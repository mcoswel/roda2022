package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.ProfilePic;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.actors.WheelTurns;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class BaseGame {
    protected final Stage stage;
    protected final RodaImpianNew rodaImpianNew;
    protected final Array<PlayerNew> players = new Array<>();
    protected final Array<PlayerGuis> playerGuis = new Array<>();
    protected final VannaHost vannaHost;
    protected final TextureAtlas atlas;
    protected final Skin skin;
    protected final CpuFactory cpuFactory;

    public BaseGame(Stage stage, RodaImpianNew rodaImpianNew) {
        this.stage = stage;
        this.rodaImpianNew = rodaImpianNew;
        this.atlas = rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS);
        this.skin = rodaImpianNew.getAssetManager().get(AssetDesc.NEWSKIN);
        this.cpuFactory = new CpuFactory(atlas);
        this.vannaHost = new VannaHost(rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS));
        stage.addActor(vannaHost);
    }

    public void addPlayers() {
        PlayerGuis playerOneGuis = setHumanGui(rodaImpianNew.getPlayer());
        PlayerGuis playerTwoGuis;
        PlayerGuis playerThreeGuis;

        if (rodaImpianNew.getPlayerTwo() != null) {
            players.add(rodaImpianNew.getPlayerTwo());
            playerTwoGuis = setHumanGui(rodaImpianNew.getPlayerTwo());
        } else {
            playerTwoGuis = cpuFactory.createCpu(skin);
        }

        if (rodaImpianNew.getPlayerThree() != null) {
            playerThreeGuis = setHumanGui(rodaImpianNew.getPlayerThree());
        } else {
            playerThreeGuis = cpuFactory.createCpu(skin);
        }

        playerGuis.add(playerOneGuis);
        playerGuis.add(playerTwoGuis);
        playerGuis.add(playerThreeGuis);
        playerGuis.shuffle();
        wheelTurn();
    }

    public void wheelTurn() {
        final WheelTurns wheelTurns = new WheelTurns(atlas.findRegion("wheelturn"),
                atlas.findRegion("needle"), playerGuis, this);

        stage.addActor(wheelTurns);

        for (int i = 0; i < playerGuis.size; i++) {
            playerGuis.get(i).setPlayerIndex(i);
            stage.addActor(playerGuis.get(i).getProfilePic());
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                wheelTurns.spin();
            }
        }, 2f);
    }

    public void animatePicture() {
        for (int i = 0; i < playerGuis.size; i++) {
            playerGuis.get(i).animateShowBoard();
            // stage.addActor(playerGuis.get(i).getProfilePic());
            stage.addActor(playerGuis.get(i).getScoreLabel());
            stage.addActor(playerGuis.get(i).getFulLScoreLabel());
            stage.addActor(playerGuis.get(i).getFreeTurn());
            stage.addActor(playerGuis.get(i).getNameLabel());
        }
    }

    public PlayerGuis setHumanGui(PlayerNew playerNew) {
        PlayerGuis playerGuis = new PlayerGuis();
        playerGuis.setPlayerNew(playerNew);
        playerGuis.setNameLabel(new Label(playerNew.getName().toUpperCase(), skin, "name"));
        playerGuis.setProfilePic(new ProfilePic(atlas.findRegion("defaultavatar"), playerNew.getPicUri()));
        playerGuis.setScoreLabel(new Label("$" + playerNew.getScore(), skin, "score"));
        playerGuis.setFulLScoreLabel(new Label("$" + playerNew.getFullScore(), skin, "arial36"));
        playerGuis.setFreeTurn(new Label(StringRes.FREETURN, skin, "free"));
        return playerGuis;
    }


}
