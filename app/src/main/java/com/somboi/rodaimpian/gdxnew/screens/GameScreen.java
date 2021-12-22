package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.games.SinglePlayer;
import com.somboi.rodaimpian.gdxnew.interfaces.GameInterface;

public class GameScreen extends BaseScreenNew implements GameInterface {
    private final GameModes modes;
    public GameScreen(RodaImpianNew rodaImpianNew, GameModes modes) {
        super(rodaImpianNew);
        this.modes = modes;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        actorFactory.createGameBg(rodaImpianNew.isGoldTheme());
        actorFactory.createGameTables();
        if (modes.equals(GameModes.SINGLE)){
            SinglePlayer singlePlayer = new SinglePlayer(stage, rodaImpianNew);
        }
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void showPlayerMenu() {

    }

    @Override
    public void hidePlayerMenu() {

    }
}
