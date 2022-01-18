package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.somboi.rodaimpian.gdxnew.onlineclasses.RoomSession;

public class RoomTable extends Table {
    private final Skin skin;

    public RoomTable(Skin skin, RoomSession roomSession) {
        this.skin = skin;
    }
}
