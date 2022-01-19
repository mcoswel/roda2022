package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.games.BaseGame;
import com.somboi.rodaimpian.gdxnew.games.SinglePlayer;
import com.somboi.rodaimpian.gdxnew.interfaces.GameInterface;

public class GameScreen extends BaseScreenNew implements GameInterface {
    private final GameModes modes;
    private BaseGame baseGame;

    public GameScreen(RodaImpianNew rodaImpianNew, GameModes modes) {
        super(rodaImpianNew);
        this.modes = modes;
        rodaImpianNew.setGameScreen(this);
        if (modes.equals(GameModes.SINGLE)) {
            actorFactory.createGameBg(rodaImpianNew.isGoldTheme());
            actorFactory.createGameTables();
            baseGame = new SinglePlayer(stage, rodaImpianNew);
        }

    }

    public void spinResult() {
        if (baseGame != null) {
            baseGame.showConsonants();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void update(float delta) {
        if (baseGame != null) {
            baseGame.update(delta);
        }
    }

    @Override
    public void showPlayerMenu() {

    }

    @Override
    public void hidePlayerMenu() {

    }
}
