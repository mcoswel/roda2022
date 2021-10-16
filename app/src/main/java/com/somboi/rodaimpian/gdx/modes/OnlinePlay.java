package com.somboi.rodaimpian.gdx.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.ChatBtn;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.Moves;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.entities.RandomCpu;
import com.somboi.rodaimpian.gdx.online.ChatOnline;
import com.somboi.rodaimpian.gdx.online.GameState;
import com.somboi.rodaimpian.gdx.online.MatchRoom;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.utils.CopyPlayer;

public class OnlinePlay extends ModeBase {
    private GameState gameState = GameState.INIT;
    private RodaClient rodaClient;
    private final RandomCpu randomCpu;
    private MatchRoom matchRoom;
    private StatusLabel debugStatus;

    public OnlinePlay(RodaImpian rodaImpian, Stage stage) {
        super(rodaImpian, stage);
        randomCpu = new RandomCpu(textureAtlas, gameSound);
        debugStatus = new StatusLabel("Sedang berhubung dengan server ", skin);
        stage.addActor(debugStatus);
        stage.addActor(playerImageGroup);
    }


    @Override
    public void setPlayers() {

        Player hostPlayer = CopyPlayer.getPlayer(rodaImpian.getRooms().getHostPlayer());
        hostPlayer.guiIndex = 0;
        if (rodaImpian.getPlayer().id.equals(hostPlayer.id)) {
            rodaImpian.setPlayer(hostPlayer);
            thisPlayer = hostPlayer;
            logger.debug("im host");
        }
        PlayerGui playerGui0 = new PlayerGui(hostPlayer, new PlayerImage(hostPlayer.picUri, textureAtlas.findRegion("default_avatar")));
        playerGuis.add(playerGui0);

        Player playerOne = CopyPlayer.getPlayer(rodaImpian.getRooms().getPlayer_one());
        playerOne.guiIndex = 1;
        if (rodaImpian.getPlayer().id.equals(playerOne.id)) {
            rodaImpian.setPlayer(playerOne);
            thisPlayer = playerOne;
            logger.debug("im player one");

        }

        PlayerGui playerGui1 = new PlayerGui(playerOne, new PlayerImage(playerOne.picUri, textureAtlas.findRegion("default_avatar")));
        playerGuis.add(playerGui1);

        if (rodaImpian.getRooms().getPlayer_two() != null) {
            Player playerTwo = CopyPlayer.getPlayer(rodaImpian.getRooms().getPlayer_two());
            playerTwo.guiIndex = 2;
            if (rodaImpian.getPlayer().id.equals(playerTwo.id)) {
                rodaImpian.setPlayer(playerTwo);
                thisPlayer = playerTwo;
            }
            PlayerGui playerGui2 = new PlayerGui(playerTwo, new PlayerImage(playerTwo.picUri, textureAtlas.findRegion("default_avatar")));
            playerGuis.add(playerGui2);

        }


        for (PlayerGui playerGui : playerGuis) {
            playerGui.setOnlinePosition(playerGui.getPlayer().guiIndex);
            playerImageGroup.addActor(playerGui.getImage());
        }

        ChatBtn chat = new ChatBtn(StringRes.CHAT, skin, rodaImpian.getPlayer().guiIndex);
        chat.pack();
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.chat(rodaImpian.getPlayer().guiIndex, OnlinePlay.this);
            }
        });
        playerImageGroup.addActor(chat);
        Gdx.input.setInputProcessor(stage);
        setActivePlayer(hostPlayer.guiIndex);
        logger.debug("Active Player "+activePlayer.name);
        setRound();
        matchRound.setOnlinePlay(true);
        showPlayerBoard();
        stage.addActor(playerImageGroup);
        stage.addActor(giftsBonusGroup);
        stage.addActor(hourGlass);
        matchRound.setQuestion();
        gameState = GameState.READY;
        sendObject(GameState.READY);
     //   sendObject(GameState.READY);
    }

    @Override
    public void firstTurn() {
        int index = 0;
        for (PlayerGui playerGui : playerGuis) {
            playerGui.setFirstTurn(index);
            playerGui.getPlayer().guiIndex = index;
            index++;
            playerImageGroup.addActor(playerGui.getImage());
        }
        showPlayerBoard();
        stage.addActor(playerImageGroup);
        stage.addActor(giftsBonusGroup);
        stage.addActor(hourGlass);
    }

    @Override
    public void startPlays() {
      //  super.startPlays();
        if (thisPlayer.turn){
            showMenu();
        }else{
            hideMenu();
        }
        gameState = GameState.STARTPLAY;
    }

    @Override
    public void aiRun() {
        super.aiRun();
    }

    @Override
    public void cpuRun(Moves moves) {
        super.cpuRun(moves);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setRodaClient(RodaClient rodaClient) {
        this.rodaClient = rodaClient;
    }


    public StatusLabel getDebugStatus() {
        return debugStatus;
    }

    public void sendChat(ChatOnline chatOnline) {
        new Thread() {
            @Override
            public void run() {
                rodaClient.sendTCP(chatOnline);
            }
        }.start();
    }

    @Override
    public void hideMenu() {
        menuButtons.hideMenu();
    }

    @Override
    public void sendObject(final Object o){
        new Thread(){
            @Override
            public void run() {
                rodaClient.sendTCP(o);
            }
        }.start();
    }

    @Override
    public void spinWheel() {
        rodaImpian.spinWheel();
        gameSound.stopClockSound();
        gameState = GameState.SPIN;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void checkAnswer(Character c){
        matchRound.checkAnswer(c);
        timerLimit.reset();
    }

}
