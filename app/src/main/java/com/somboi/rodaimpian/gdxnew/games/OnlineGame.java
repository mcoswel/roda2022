package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.ChatBubble;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.SmallButton;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.KickPlayer;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;

public class OnlineGame extends BaseGame {
    private RoomSession roomSession;
    private final OnInterface onInterface;
    private final Group onlineMenuGroup = new Group();

    public OnlineGame(Stage stage, RodaImpianNew rodaImpianNew, OnInterface onInterface) {
        super(stage, rodaImpianNew);
        this.onInterface = onInterface;
        vannaHost.remove();
    }

    @Override
    public void addPlayers() {
        playerGuis.clear();
        tilesGroup.clear();
        tilesGroup.remove();
        onlineMenuGroup.clear();
        onlineMenuGroup.remove();
        stage.addActor(tilesGroup);
        stage.addActor(onlineMenuGroup);
        for (PlayerNew playerNew : roomSession.getPlayerList()) {
            playerGuis.add(setHumanGui(playerNew, 1));
        }
        for (PlayerGuis p : playerGuis) {
            p.getPlayerNew().setScore(0);
            p.getScoreLabel().setText("$" + p.getPlayerNew().getScore());
            p.getPlayerNew().setFullScore(0);
            p.updateFullScore(p.getPlayerNew().getFullScore());
            p.getPlayerNew().setAnimateScore(0);
        }
        for (int i = 0; i < playerGuis.size; i++) {
            PlayerGuis pgui = playerGuis.get(i);
            pgui.setPlayerIndex(i);
            pgui.setChatBubble(new ChatBubble(i, stage, skin));
            pgui.setPicFixPos();
            stage.addActor(pgui.getProfilePic());
            if (i > 0 && onInterface.isHost())  {
                SmallButton kickBtn = new SmallButton(StringRes.KICK, skin) {
                    @Override
                    public float getPrefWidth() {
                        return 260f;
                    }
                };
                kickBtn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        KickPlayer kickPlayer = new KickPlayer();
                        kickPlayer.setRoomId(rodaImpianNew.getPlayer().getUid());
                        kickPlayer.setKickedPlayerID(pgui.getPlayerNew().getUid());
                        onInterface.sendObjects(kickPlayer);
                    }
                });
                kickBtn.setPosition(pgui.getPosition().x, pgui.getPosition().y + 255f);
                onlineMenuGroup.addActor(kickBtn);
            }
            tilesGroup.addActor(pgui.getScoreLabel());
            tilesGroup.addActor(pgui.getFulLScoreLabel());
            //tilesGroup.addActor(playerGuis.get(i).getFreeTurn());
            tilesGroup.addActor(pgui.getNameLabel());
        }


    }

    public void playerDisconnected(String uid) {
        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().getUid().equals(uid)) {
                p.getProfilePic().setColor(Color.BLUE);
            }
        }
    }

    public void setRoomSession(RoomSession roomSession) {
        this.roomSession = roomSession;
    }
}
