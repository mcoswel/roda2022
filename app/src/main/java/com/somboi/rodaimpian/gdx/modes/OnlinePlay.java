package com.somboi.rodaimpian.gdx.modes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.EnvelopeSubject;
import com.somboi.rodaimpian.gdx.actor.Envelopes;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.Bonus;
import com.somboi.rodaimpian.gdx.entities.Moves;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.online.entities.ChatOnline;
import com.somboi.rodaimpian.gdx.online.entities.GameState;
import com.somboi.rodaimpian.gdx.online.entities.PlayerState;
import com.somboi.rodaimpian.gdx.online.RodaClient;
import com.somboi.rodaimpian.gdx.online.newentities.SessionRoom;

public class OnlinePlay extends ModeBase {
    private GameState gameState;
    private  RodaClient rodaClient;
    private StatusLabel debugStatus;
    private SessionRoom sessionRoom;

    public OnlinePlay(RodaImpian rodaImpian, Stage stage) {
        super(rodaImpian, stage);
        debugStatus = new StatusLabel(StringRes.CONNECTING, skin);
        stage.addActor(debugStatus);
        stage.addActor(playerImageGroup);
        //logger.debug("session name " +sessionRoom.getRoomName()+" player size "+sessionRoom.getPlayerList().size());
        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void setPlayers() {
        //setRound();
        stage.addActor(vanna);
        sessionRoom = rodaImpian.getSessionRoom();
        int index = 0;
        for (Player player : sessionRoom.getPlayerList()){
            player.guiIndex = index;
            if (player.id.equals(thisPlayer.id)){
                rodaImpian.setPlayer(player);
            }
            index++;
            playerGuis.add(new PlayerGui(player, new PlayerImage(player.picUri, textureAtlas.findRegion("default_avatar"))));
        }
      /*  Player hostPlayer = sessionRoom.getHostPlayer();
        PlayerGui playerGui0 = new PlayerGui(hostPlayer, new PlayerImage(hostPlayer.picUri, textureAtlas.findRegion("default_avatar")));
        playerGuis.add(playerGui0);

        Player playerOne = sessionRoom.getPlayerOne();

        if (rodaImpian.getPlayer().id.equals(playerOne.id)) {
            rodaImpian.setPlayer(playerOne);
            thisPlayer = playerOne;
        }

        PlayerGui playerGui1 = new PlayerGui(playerOne, new PlayerImage(playerOne.picUri, textureAtlas.findRegion("default_avatar")));
        playerGuis.add(playerGui1);

        if (sessionRoom.getPlayerTwo() != null) {
            Player playerTwo = sessionRoom.getPlayerTwo();
            if (rodaImpian.getPlayer().id.equals(playerTwo.id)) {
                rodaImpian.setPlayer(playerTwo);
                thisPlayer = playerTwo;
            }
            PlayerGui playerGui2 = new PlayerGui(playerTwo, new PlayerImage(playerTwo.picUri, textureAtlas.findRegion("default_avatar")));
            playerGuis.add(playerGui2);
        }*/


        for (PlayerGui playerGui : playerGuis) {
            playerGui.setOnlinePosition(playerGui.getPlayer().guiIndex);
            playerImageGroup.addActor(playerGui.getImage());
        }

      /*  ChatBtn chat = new ChatBtn(StringRes.CHAT, skin, rodaImpian.getPlayer().guiIndex);
        chat.pack();
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.chat(rodaImpian.getPlayer().guiIndex, OnlinePlay.this);
            }
        });*/
       // playerImageGroup.addActor(chat);
        Gdx.input.setInputProcessor(stage);
        setActivePlayer(0);
        logger.debug("Active Player " + activePlayer.name);
        setRound();
        showPlayerBoard();
        stage.addActor(playerImageGroup);
        stage.addActor(giftsBonusGroup);
        stage.addActor(hourGlass);
        matchRound.setQuestion();
        rodaClient.setPlayerState(PlayerState.READY);
        thisPlayer = rodaImpian.getPlayer();
        //   sendObject(GameState.READY);
    }


    @Override
    public void startPlays() {

          super.startPlays();
        if (thisPlayer.turn) {
            showMenu();
        } else {
            hideMenu();
        }
        rodaClient.setPlayerState(PlayerState.SHOWMENU);
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
    public void sendObject(final Object o) {
        new Thread() {
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
        // gameState = GameState.SPIN;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void checkAnswer(Character c) {
        matchRound.checkAnswer(c);
        timerLimit.reset();
    }

    public void checkBonusStringOnline(String holder){
        matchRound.checkBonusStringOnline(holder);
    }

    public void openEnvelopeByIndex(int index){
        Envelopes envelopes =envelopesOnline.get(index);
        envelopes.opened();
        float xPosition = envelopes.getX() + envelopes.getWidth() / 2f;
        tilesGroup.addActor(new EnvelopeSubject(skin, rodaImpian.getQuestionsReady().getSubjectRoundFour(), xPosition));
        envelopeClicked = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                tilesGroup.clear();
                matchRound.setQuestion();
                rodaImpian.showAds(3);
                chooseBonusConsonant();
            }
        }, 3f);

    }



    public void showInfo(String text) {
        matchRound.showInfo(text);
    }

    public void setRodaClient(RodaClient rodaClient) {
        this.rodaClient = rodaClient;
    }

    public void setOnlineBonus(int bonusIndex) {
        Bonus bonus = new Bonus(textureAtlas,bonusIndex);
        setBonus(bonus);
        bonusRound();
    }
}
