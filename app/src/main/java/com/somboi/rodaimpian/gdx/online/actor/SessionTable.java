package com.somboi.rodaimpian.gdx.online.actor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.MenuBtn;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.online.newentities.CreateSessions;
import com.somboi.rodaimpian.gdx.online.newentities.RegisterPlayer;

public class SessionTable extends Table {
    private final CreateSessions createSessions;
    public SessionTable(CreateSessions createSessions, RodaClient newClient, Skin skin, TextureRegion defaultAvatar, Player thisplayer) {
        this.createSessions = createSessions;
        PlayerImage playerImage = new PlayerImage(createSessions.picUri,defaultAvatar);
        Label name = new Label(createSessions.name, skin);
        MenuBtn join = new MenuBtn(StringRes.JOIN, skin);
        join.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                RegisterPlayer registerPlayer = new RegisterPlayer()    ;
                registerPlayer.player = thisplayer  ;
                registerPlayer.roomID = createSessions.sessionID;
                newClient.sendObject(registerPlayer);
            }
        });
        Table table = new Table();
        table.add(playerImage).width(150f).height(150f);
        table.add(join).padLeft(100f);
        this.defaults().pad(10f);
        this.add(name).colspan(3).row();
        this.add(table);
        this.pack();
        this.add(new Actor());
        this.setBackground(skin.getDrawable("title-box"));

    }
}
