package com.somboi.rodaimpian.gdx.online;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdxnew.actors.Confetti;
import com.somboi.rodaimpian.gdx.actor.EnvelopeSubject;
import com.somboi.rodaimpian.gdx.actor.Envelopes;
import com.somboi.rodaimpian.gdxnew.actors.Sparkling;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.entities.Bonus;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.actor.ChatBtn;
import com.somboi.rodaimpian.gdx.actor.ErrorDialog;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.actor.StatusLabel;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.online.actor.KickBtn;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.entities.PlaeyrStateOld;
import com.somboi.rodaimpian.gdx.online.newentities.RemovePlayer;
import com.somboi.rodaimpian.gdx.online.newentities.RemoveSession;
import com.somboi.rodaimpian.gdx.online.newentities.RodaSession;

import java.util.List;

public class OnlinePlay extends ModeBase {
    private Group statusGroup = new Group();
    private StatusLabel statusLabel;
    private Table statusMenu = new Table();
    private final RodaClient newClient;
    private final RodaSession rodaSession;
    private boolean isHost;
    private boolean stopped;
    private ChatBtn chatBtn;

    public OnlinePlay(RodaImpian rodaImpian, RodaClient newClient, Stage stage, RodaSession rodaSession) {
        super(rodaImpian, stage);
        this.newClient = newClient;
        this.rodaSession = rodaSession;

        statusLabel = new StatusLabel(StringRes.WAITINGENTRY, skin);
        if (rodaSession.playerList.size() > 1) {
            statusLabel.setText(StringRes.WAITINGHOST);
        }

        LargeButton cancel = new LargeButton(StringRes.EXIT, skin);
        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newClient.disconnect();
                rodaImpian.gotoMenu();
            }
        });
        LargeButton mula = new LargeButton(StringRes.START, skin);
        mula.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerGuis.size <= 1) {
                    ErrorDialog errorDialog = new ErrorDialog(StringRes.NOTENOUGHPLAYER, skin);
                    errorDialog.show(stage);
                } else {
                    RemoveSession removeSession = new RemoveSession();
                    removeSession.sessionid = rodaSession.id;
                    sendObject(removeSession);
                    sendObject(PlaeyrStateOld.START);
                }
            }
        });

        statusMenu.add(cancel);
        if (rodaSession.playerList.size() > 1 && rodaSession.id.equals(rodaImpian.getPlayer().id)) {
            statusMenu.add(mula);
        }
        statusMenu.pack();
        statusMenu.setPosition(450f - statusMenu.getWidth() / 2f, 700f);
        statusGroup.addActor(statusLabel);
        statusGroup.addActor(statusMenu);
        stage.addActor(statusGroup);
        setUpPlayer(rodaSession.playerList);


    }

    public void setUpPlayer(List<Player> playerList) {
        int index = 0;
        for (Player player : playerList) {
            player.guiIndex = index;
            PlayerGui playerGui = new PlayerGui(player, new PlayerImage(player.picUri, textureAtlas.findRegion("default_avatar")));
            playerGui.setOnlinePosition(index);
            playerImageGroup.addActor(playerGui.getImage());
            playerGui.setPlayerBoard(skin, playerBoardGroup);
            playerGuis.add(playerGui);
            if (player.id.equals(rodaImpian.getPlayer().id)) {
                rodaImpian.setPlayer(player);
                thisPlayer = player;
                chatBtn = new ChatBtn(StringRes.CHAT, skin, player.guiIndex);
                chatBtn.pack();
                chatBtn.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        rodaImpian.chat(player.guiIndex, OnlinePlay.this);
                    }
                });
                playerImageGroup.addActor(chatBtn);
            }
            if (rodaImpian.getPlayer().id.equals(rodaSession.id)) {
                isHost = true;
                if (!player.id.equals(rodaImpian.getPlayer().id)) {
                    KickBtn kickBtn = new KickBtn(StringRes.KICK, skin, player.guiIndex);
                    kickBtn.pack();
                    kickBtn.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            RemovePlayer removePlayer = new RemovePlayer();
                            removePlayer.playerid = player.id;
                            sendObject(removePlayer);
                        }
                    });
                    statusGroup.addActor(kickBtn);
                }

            }
            index++;

        }


        if (!rodaSession.id.equals(rodaImpian.getPlayer().id)) {
            sendObject(PlaeyrStateOld.START);
        }
      /*  player.guiIndex = playerSize;
        PlayerGui playerGui = new PlayerGui(player, new PlayerImage(player.picUri, textureAtlas.findRegion("default_avatar")));
        playerGui.setOnlinePosition(playerSize);
        playerImageGroup.addActor(playerGui.getImage());
        playerGui.setPlayerBoard(skin, playerBoardGroup);
        ChatBtn chat = new ChatBtn(StringRes.CHAT, skin, rodaImpian.getPlayer().guiIndex);
        chat.pack();
        chat.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.chat(rodaImpian.getPlayer().guiIndex, NewOnline.this);
            }
        });*/
    }

    @Override
    public void newRound() {
        logger.debug("new round");
        gameRound++;
        tilesGroup.addActor(new Confetti(assetManager.get(AssetDesc.CONFETTI), activePlayer.guiIndex));
        for (PlayerGui p : playerGuis) {
            p.getPlayer().currentScore = 0;
            p.getPlayer().freeTurn = false;
            p.removeFreeTurn();
        }

        if (!rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    rodaImpian.showAds(gameRound);
                }
            }, 2.5f);
        }


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                tilesGroup.clear();
                PlayerGui winnerGui = playerGuis.get(activePlayer.guiIndex);
                winnerGui.getImage().addAction(Actions.moveTo(winnerGui.getPlayerPos().x, winnerGui.getPlayerPos().y, 2f));
                if (gameRound == 3) {
                    checkWinner();
                } else {
                    stage.addActor(hourGlass);
                    sendObject(PlaeyrStateOld.START);
                    //  startRound();
                }
            }
        }, 5f);
    }


    @Override
    public void sendObject(Object o) {
        newClient.sendObject(o);
    }

    @Override
    public void showMenu() {
        if (thisPlayer.turn && rodaImpian.getPlayer().turn) {
            menuButtons.showMenu(matchRound.stillHaveConsonants(), matchRound.stillHaveVocals());
        }
    }

    @Override
    public void changeTurn() {
        if (activePlayer.freeTurn && !activePlayer.disconnect) {
            playerGuis.get(activePlayer.guiIndex).removeFreeTurn();
            activePlayer.freeTurn = false;
            sendObject(PlaeyrStateOld.SHOWMENU);
        } else {
            sendObject(PlaeyrStateOld.CHANGETURN);
        }
    }

    @Override
    public void setActivePlayer(int i) {

        Player player = playerGuis.get(i).getPlayer();
        player.turn = true;
        activePlayer = player;
        if (!thisPlayer.id.equals(activePlayer.id)) {
            thisPlayer.turn = false;
            rodaImpian.getPlayer().turn = false;
        }else{
            thisPlayer.turn = true;
            rodaImpian.getPlayer().turn = true;
        }
        stage.addActor(hourGlass);
      //  hourGlass.changePos(playerGuis.get(activePlayer.guiIndex));

        logger.debug("active player " + activePlayer.name);
    }


    @Override
    public void startPlays() {
        setRound();
        matchRound.setQuestion();
        statusGroup.clear();
        statusGroup.remove();
        statusLabel.setText(StringRes.ONLINE);
        tilesGroup.addActor(statusLabel);
        sendObject(PlaeyrStateOld.SETACTIVEPLAYER);
        gameSound.playCorrect();
        infoLabel.setText(StringRes.ROUND + (1 + gameRound));
        if (gameRound != 3) {
            infoLabel.show(tilesGroup);
        }
        stage.addActor(vanna);
        //  sendObject(PlayerState.SHOWMENU);
    }

    public void checkChar(Character c) {
        matchRound.checkAnswer(c);
    }

    public void sendChat(ChatOnlineOld chatOnlineOld) {
        newClient.sendObject(chatOnlineOld);
    }

    public void checkDisconnected(String id) {

        for (PlayerGui playerGui : playerGuis) {
            if (playerGui.getPlayer().id.equals(id)) {
                playerGui.getPlayer().disconnect = true;
                playerGui.getImage().setColor(Color.BLUE);
                if (playerGui.getPlayer().turn) {
                    changeTurn();
                }
                statusLabel.setText(playerGui.getPlayer().name + StringRes.DISCONNECTED);
            }
        }
    }

    @Override
    public void setGiftOnline(int giftIndex) {
        stage.addActor(giftsBonusGroup);
        gifts.setGiftsOnline(giftIndex);
    }

    @Override
    public void showVocals() {
        activePlayer.currentScore -= 250;
        if (thisPlayer.id.equals(activePlayer.id)) {
            vocalKeyboard.show();
        }
    }

    public void setWheelParamResults(WheelParam wheelParam) {
        this.wheelParam.results = wheelParam.results;
        this.wheelParam.resultValue = wheelParam.resultValue;
    }

    @Override
    public void update(float delta) {
        if (!newClient.isConnected() && !stopped) {
            statusLabel.setText(StringRes.FAILSERVER);
            ErrorDialog errorDialog = new ErrorDialog(StringRes.FAILSERVER, skin) {
                @Override
                protected void result(Object object) {
                    rodaImpian.gotoMenu();
                    stopped = true;
                }
            };
            errorDialog.show(stage);
            stopped = true;
        }

        if (keyboardOn){
            Gdx.input.setInputProcessor(completeKeyMultiplex);
        }else if (rodaImpian.getPlayer().turn){
            Gdx.input.setInputProcessor(stage);
        }
    }

    public void setBonusOnline(int index) {
        Bonus bonus = new Bonus(textureAtlas, index);
        setBonus(bonus);
    }

    @Override
    public void bonusRound() {
        bonusRound = true;
        Array<Integer> integers = new Array<>(new Integer[]{0, 1, 2});
        integers.shuffle();
        rodaImpian.loadAds();
        for (int i = 0; i < 3; i++) {
            final Envelopes envelopes = new Envelopes(textureAtlas, integers.get(i));
            envelopes.setPosition(150 + (300 * i) - envelopes.getWidth() / 2f, 674f);
            int finalI = i;
            envelopes.addListener(new DragListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (rodaImpian.getPlayer().turn) {
                        if (!envelopeClicked) {
                            EnvelopeIndex envelopeIndex = new EnvelopeIndex();
                            envelopeIndex.index = finalI;
                            sendObject(envelopeIndex);
                        }
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            tilesGroup.addActor(envelopes);
            envelopesOnline.add(envelopes);
        }
        playerBoardGroup.addActor(bonus.getBonusImage());
        tilesGroup.addActor(new Sparkling(assetManager.get(AssetDesc.SPARKLE)));
        infoLabel.setText(StringRes.CHOOSEENVELOPE);
        infoLabel.show(tilesGroup);
    }

    @Override
    public void showGifts() {
        super.showGifts();
        chatBtn.remove();
        stage.addActor(chatBtn);
    Timer.schedule(new Timer.Task() {
        @Override
        public void run() {
            gifts.removeBox();
        }
    },4f);
    }

    public void openEnvelopes(EnvelopeIndex envelopeIndex) {
        Envelopes envelopes = envelopesOnline.get(envelopeIndex.index);
        envelopes.opened();
        float xPosition = envelopes.getX() + envelopes.getWidth() / 2f;
        tilesGroup.addActor(new EnvelopeSubject(skin, rodaImpian.getQuestionsReady().getSubjectRoundFour(), xPosition));
        envelopeClicked = true;
        gameRound = 3;
        setRound();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                tilesGroup.clear();
                matchRound.setQuestion();
                rodaImpian.showAds(3);
                if (rodaImpian.getPlayer().turn) {
                    chooseBonusConsonant();
                } else {
                    infoLabel.setText(StringRes.CHOOSE5CONS);
                    infoLabel.show(tilesGroup);
                }
            }
        }, 3f);
    }

    public void checkBonusString(String holder) {
        matchRound.checkBonusStringOnline(holder);
    }

    @Override
    public void completeBonus() {
        if (activePlayer.id.equals(rodaImpian.getPlayer().id)) {
            completePuzzle();
            menuButtons.showCompleteMenu();
            stage.addActor(timerLimit);
            timerLimit.start();
        } else {
            rodaImpian.showAds(1);
        }
    }



}
