package com.somboi.rodaimpian.gdx.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.online.entities.GameStateOld;
import com.somboi.rodaimpian.gdx.online.entities.PlaeyrStateOld;
import com.somboi.rodaimpian.gdx.online.newentities.RegisterPlayer;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.online.newentities.SessionRoom;

import java.util.List;

public class SessionListTable extends Window {
    private final SessionRoom sessionRoom;
    private final List<Player> playerList;
    private final Skin skin;
    private final Logger logger = new Logger(this.getClass().getName(), 3);

    public SessionListTable(SessionRoom sessionRoom, Skin skin, TextureRegion defaultAvatar, RodaImpian rodaImpian, RodaClient rodaClient) {
        super(sessionRoom.getRoomName(), skin);
        this.sessionRoom = sessionRoom;
        this.playerList = sessionRoom.getPlayerList();
        this.skin = skin;
        this.setMovable(false);

        MenuBtn join = new MenuBtn(StringRes.JOIN, skin);
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaClient.sendObject(PlaeyrStateOld.EXITROOM);
                RegisterPlayer registerPlayer = new RegisterPlayer();
                registerPlayer.roomID = sessionRoom.getRoomID();
                registerPlayer.player = rodaImpian.getPlayer();
                rodaClient.setSessionID(sessionRoom.getRoomID());
                rodaClient.sendObject(registerPlayer);

            }
        });


        Table hostPlayer = new Table();
        hostPlayer.setWidth(300f);

        Table playerOne = new Table();
        PlayerImage imageOne = new PlayerImage(defaultAvatar);
        playerOne.add(imageOne).width(150f).height(150f).row();
        playerOne.add(new Label(StringRes.EMPTYSLOT, skin)).row();

        Table playerTwo = new Table();
        PlayerImage imageTwo = new PlayerImage(defaultAvatar);
        playerTwo.add(imageTwo).width(150f).height(150f).row();
        playerTwo.add(new Label(StringRes.EMPTYSLOT, skin)).row();

        int index = 0;
        for (Player p : playerList) {
            PlayerImage image = new PlayerImage(p.picUri, defaultAvatar);
            Label name = new Label(p.name, skin);

            if (index == 0) {
                hostPlayer.clear();
                hostPlayer.add(image).width(150f).height(150f).row();
                hostPlayer.add(name).row();
            } else if (index == 1) {
                playerOne.clear();
                playerOne.add(image).width(150f).height(150f).row();
                playerOne.add(name).row();
            } else if (index == 2) {
                playerTwo.clear();
                playerTwo.add(image).width(150f).height(150f).row();
                playerTwo.add(name).row();

            }

            index++;
        }
        Table playerListTable = new Table() {
            @Override
            public float getColumnPrefWidth(int columnIndex) {
                return 300f;
            }
        };

        playerListTable.add(hostPlayer).width(300f).height(300f);
        playerListTable.add(playerOne).width(300f).height(300f);
        playerListTable.add(playerTwo).width(300f).height(300f);

        this.add(playerListTable).row();
        MenuBtn delete = new MenuBtn(StringRes.DELETE, skin);
        delete.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaClient.sendObject(PlaeyrStateOld.EXITROOM);
            }
        });
        MenuBtn exit = new MenuBtn(StringRes.EXIT, skin);
        MenuBtn start = new MenuBtn(StringRes.START, skin);
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaClient.sendObject(GameStateOld.START);
            }
        });

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaClient.sendObject(PlaeyrStateOld.EXITROOM);
                rodaClient.setSessionID(null);
            }
        });
        Table hostTable = new Table();

        if (sessionRoom.getRoomID().equals(rodaImpian.getPlayer().id)) {
            hostTable.add(delete);
            if (sessionRoom.getPlayerList().size() > 1) {
                hostTable.add(start);
            }
            this.add(hostTable);
        } else {
            if (rodaClient.getSessionID() == null) {
                this.add(join);
            } else if (rodaClient.getSessionID().equals(sessionRoom.getRoomID())) {
                this.add(exit);
            }
        }



        if (sessionRoom.getPlayerList().size() == 3 || sessionRoom.isPlaying()) {
            join.remove();
            this.getTitleLabel().setText(StringRes.PLAYING);
        }


        this.pack();

    }
}
