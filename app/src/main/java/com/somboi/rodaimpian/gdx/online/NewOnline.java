package com.somboi.rodaimpian.gdx.online;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.ChatBtn;
import com.somboi.rodaimpian.gdx.actor.ChatBubble;
import com.somboi.rodaimpian.gdx.actor.ErrorDialog;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.online.entities.ChatOnline;
import com.somboi.rodaimpian.gdx.online.newentities.RemoveSession;
import com.somboi.rodaimpian.gdx.online.newentities.RodaSession;

import java.util.List;

public class NewOnline extends ModeBase {
    private Group statusGroup = new Group();
    private StatusLabel statusLabel;
    private Table statusMenu = new Table();
    private final NewClient newClient;
    private final RodaSession rodaSession;
    public NewOnline(RodaImpian rodaImpian, NewClient newClient, Stage stage, RodaSession rodaSession) {
        super(rodaImpian, stage);
        this.newClient = newClient;
        this.rodaSession = rodaSession;

        statusLabel = new StatusLabel(StringRes.WAITINGENTRY, skin);

        LargeButton cancel = new LargeButton(StringRes.EXIT, skin);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RemoveSession removeSession = new RemoveSession();
                removeSession.sessionid = rodaImpian.getPlayer().id;
                newClient.sendObject(removeSession);
            }
        });
        LargeButton mula = new LargeButton(StringRes.START, skin);
        mula.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerGuis.size<=1){
                    ErrorDialog errorDialog = new ErrorDialog(StringRes.NOTENOUGHPLAYER, skin);
                    errorDialog.show(stage);
                }else{

                }
            }
        });

        statusMenu.add(cancel);
        if (rodaSession.playerList.size()>1 && rodaSession.id.equals(rodaImpian.getPlayer().id)) {
            statusMenu.add(mula);
        }
        statusMenu.pack();
        statusMenu.setPosition(450f-statusMenu.getWidth()/2f, 700f);
        statusGroup.addActor(statusLabel);
        statusGroup.addActor(statusMenu);
        stage.addActor(statusGroup);
        setUpPlayer(rodaSession.playerList);
    }

    public void setUpPlayer(List<Player> playerList){
        int index = 0;
        for (Player player: playerList){
            player.guiIndex = index;
            PlayerGui playerGui = new PlayerGui(player, new PlayerImage(player.picUri, textureAtlas.findRegion("default_avatar")));
            playerGui.setOnlinePosition(index);
            playerImageGroup.addActor(playerGui.getImage());
            playerGui.setPlayerBoard(skin, playerBoardGroup);
            ChatBtn chat = new ChatBtn(StringRes.CHAT, skin, rodaImpian.getPlayer().guiIndex);
            chat.pack();
            chat.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    rodaImpian.chat(rodaImpian.getPlayer().guiIndex, NewOnline.this);
                }
            });
            playerImageGroup.addActor(chat);
            playerGuis.add(playerGui);
            index++;

        }
      /*  player.guiIndex = playerSize;
        PlayerGui playerGui = new PlayerGui(player, new PlayerImage(player.picUri, textureAtlas.findRegion("default_avatar")));
        playerGui.setOnlinePosition(playerSize);
        playerImageGroup.addActor(playerGui.getImage());
        playerGui.setPlayerBoard(skin, playerBoardGroup);
        ChatBtn chat = new ChatBtn(StringRes.CHAT, skin, rodaImpian.getPlayer().guiIndex);
        chat.pack();
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.chat(rodaImpian.getPlayer().guiIndex, NewOnline.this);
            }
        });*/
    }

    public void sendChat(ChatOnline chatOnline){
        newClient.sendObject(chatOnline);
    }
}
