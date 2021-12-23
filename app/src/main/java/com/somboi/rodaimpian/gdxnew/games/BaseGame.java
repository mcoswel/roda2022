package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.actors.CpuFactory;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.ProfilePic;
import com.somboi.rodaimpian.gdxnew.actors.TileBase;
import com.somboi.rodaimpian.gdxnew.actors.VannaHost;
import com.somboi.rodaimpian.gdxnew.actors.WheelTurns;
import com.somboi.rodaimpian.gdxnew.assets.QuestionNew;
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
    protected final Logger logger = new Logger(this.getClass().getName(), 3);
    protected int gameRound;
    protected QuestionNew currentQuestion;
    protected final Array<TileBase> tileBases = new Array<>();

    public BaseGame(Stage stage, RodaImpianNew rodaImpianNew) {
        this.stage = stage;
        this.rodaImpianNew = rodaImpianNew;
        this.atlas = rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS);
        this.skin = rodaImpianNew.getAssetManager().get(AssetDesc.NEWSKIN);
        this.cpuFactory = new CpuFactory(atlas);
        this.vannaHost = new VannaHost(rodaImpianNew.getAssetManager().get(AssetDesc.ATLAS));
        currentQuestion = rodaImpianNew.getPreparedQuestions().get(gameRound);
        setTile();
        stage.addActor(vannaHost);
    }

    public void setTile() {
        float initialY = 0;
        float initialX = 0;
        if (currentQuestion.getTotalline() <= 2) {
            initialY = 1401f;
            initialX = 37f;
        } else {
            initialX = 96f;
            initialY = 1481f;
        }
        if (currentQuestion.getLine1() != null) {
            addTileBase(currentQuestion.getLine1(), 12, initialY,initialX);
        }
        if (currentQuestion.getLine2() != null) {
            addTileBase(currentQuestion.getLine2(), 14, initialY - 78f - 2,37f);
        }
        if (currentQuestion.getLine3() != null) {
            addTileBase(currentQuestion.getLine3(), 14, initialY - 78f * 2 - 4,37f);
        }
        if (currentQuestion.getLine4() != null) {
            addTileBase(currentQuestion.getLine4(), 12, initialY - 78f * 3 - 6,96f);
        }

    }

    private void addTileBase(String questionLine, int lineMaxLength, float positionY, float positionX) {
      if (lineMaxLength>12){
          if ((lineMaxLength-questionLine.length())>=1){
              positionX+=59f;
          }
      }
        if (lineMaxLength==12 && currentQuestion.getTotalline()<=2){
            if ((lineMaxLength-questionLine.length())>=1){
                positionX+=59f;
            }
        }
        for (int i = 0; i < questionLine.length(); i++) {
            if (questionLine.charAt(i)!=' '){
                TileBase tileBase = new TileBase(atlas, String.valueOf(questionLine.charAt(i)));
                tileBase.setPosition(positionX + 57 * i + 2 * i, positionY);
                tileBases.add(tileBase);
            }

        }
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
                atlas.findRegion("pointer"), playerGuis, this);

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

    public void showQuestions() {
        for (TileBase tileBase : tileBases) {
            tileBase.addAction(new SequenceAction(
                    Actions.fadeOut(0),
                    Actions.fadeIn(1f)
            ));
            stage.addActor(tileBase);
        }
    }

    public void startRound(){
        showQuestions();
    }


}
