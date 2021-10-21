package com.somboi.rodaimpian.gdx.screen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.base.BaseScreen;

public class OnlineScreen extends BaseScreen {
    private final Group roomGroup = new Group();
    public OnlineScreen(RodaImpian rodaImpian) {
        super(rodaImpian);
        stage.addActor(roomGroup);

    }
}
