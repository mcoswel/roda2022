package com.somboi.rodaimpian.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.actor.ChatBubble;
import com.somboi.rodaimpian.gdx.actor.Confetti;
import com.somboi.rodaimpian.gdx.actor.ConsonantKeyboard;
import com.somboi.rodaimpian.gdx.actor.CorrectScore;
import com.somboi.rodaimpian.gdx.actor.EndGameDialog;
import com.somboi.rodaimpian.gdx.actor.EnvelopeSubject;
import com.somboi.rodaimpian.gdx.actor.Envelopes;
import com.somboi.rodaimpian.gdx.actor.FirstTurnWheel;
import com.somboi.rodaimpian.gdx.actor.FlyingMoney;
import com.somboi.rodaimpian.gdx.actor.HourGlass;
import com.somboi.rodaimpian.gdx.actor.MenuButtons;
import com.somboi.rodaimpian.gdx.actor.Sparkling;
import com.somboi.rodaimpian.gdx.actor.TimerLimit;
import com.somboi.rodaimpian.gdx.actor.Trophy;
import com.somboi.rodaimpian.gdx.actor.Vanna;
import com.somboi.rodaimpian.gdx.actor.VocalKeyboard;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdx.entities.Bonus;
import com.somboi.rodaimpian.gdx.entities.Gifts;
import com.somboi.rodaimpian.gdx.entities.MatchRound;
import com.somboi.rodaimpian.gdx.entities.Moves;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.entities.Tiles;
import com.somboi.rodaimpian.gdx.entities.WheelParam;
import com.somboi.rodaimpian.gdx.listener.InputCompleteKey;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.entities.ChatOnline;
import com.somboi.rodaimpian.gdx.online.entities.EnvelopeIndex;
import com.somboi.rodaimpian.gdx.online.entities.GameState;
import com.somboi.rodaimpian.gdx.online.entities.PlayerState;
import com.somboi.rodaimpian.gdx.online.newentities.SetActivePlayer;
import com.somboi.rodaimpian.gdx.screen.WheelScreen;
import com.somboi.rodaimpian.gdx.utils.CopyPlayer;
import com.somboi.rodaimpian.saves.PlayerSaves;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModeBase {
    protected final AssetManager assetManager;
    protected final RodaImpian rodaImpian;
    protected final Skin skin;
    protected final Stage stage;
    protected final Array<PlayerGui> playerGuis = new Array<>();
    protected Player thisPlayer;
    protected final TextureAtlas textureAtlas;
    protected Player activePlayer;
    protected final GameSound gameSound;
    protected MatchRound matchRound;
    protected final Vanna vanna;
    protected final TimerLimit timerLimit;
    private final Queue<ChatBubble> chatBubbles = new Queue<>();
    protected int gameRound = 0;
    private ConsonantKeyboard consonantKeyboard;
    protected VocalKeyboard vocalKeyboard;
    private final Group menuGroup = new Group();
    protected MenuButtons menuButtons;
    public WheelParam wheelParam = new WheelParam();
    protected final Group playerImageGroup = new Group();
    protected final Group playerBoardGroup = new Group();
    protected final Group completeGroup = new Group();
    protected final Group tilesGroup = new Group();
    protected final Group giftsBonusGroup = new Group();
    protected final List<Tiles> completeList = new ArrayList<>();
    protected final Gifts gifts;
    protected final HourGlass hourGlass;
    protected boolean envelopeClicked;
    protected final Logger logger = new Logger(this.getClass().getName(), 3);
    protected final CorrectScore infoLabel;
    protected Bonus bonus;
    protected boolean winBonus;
    protected boolean bonusRound;
    protected boolean gameEnds;
    protected float adsLoadCounter = 60f;
    private final float ADSCOUNTER = 60f;
    protected final Array<Envelopes> envelopesOnline = new Array<>();

    public ModeBase(RodaImpian rodaImpian, Stage stage) {
        this.rodaImpian = rodaImpian;
        this.thisPlayer = rodaImpian.getPlayer();
        this.assetManager = rodaImpian.getAssetManager();
        this.gameSound = new GameSound(assetManager);
        this.skin = assetManager.get(AssetDesc.SKIN);
        this.textureAtlas = assetManager.get(AssetDesc.TEXTUREATLAS);
        this.stage = stage;
        this.infoLabel = new CorrectScore("", skin);
        this.timerLimit = new TimerLimit(skin, gameSound);
        vanna = new Vanna(rodaImpian.getAssetManager().get(AssetDesc.TEXTUREATLAS));
        gifts = new Gifts(textureAtlas, assetManager.get(AssetDesc.SPARKLE), giftsBonusGroup);
        hourGlass = new HourGlass(assetManager.get(AssetDesc.HOURGLASS));
        wheelParam.results = "";

        stage.addActor(tilesGroup);
        stage.addActor(menuGroup);
        stage.addActor(playerBoardGroup);
        stage.addActor(playerImageGroup);


        if (!rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            setPlayers();
            playerGuis.shuffle();
            setRound();
            firstTurn();
            stage.addActor(vanna);
        }
        winBonus = false;
        bonusRound = false;
        rodaImpian.loadAds();

        if (!rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            rodaImpian.setWheelScreen(new WheelScreen(rodaImpian, this));
        }
    }


    public void setRound() {

        menuButtons = new MenuButtons(menuGroup, rodaImpian, this);
        matchRound = new MatchRound(rodaImpian, gameSound, textureAtlas, tilesGroup, skin, gameRound, this);
        consonantKeyboard = new ConsonantKeyboard(skin, matchRound, stage);
        vocalKeyboard = new VocalKeyboard(skin, matchRound, stage);
    }

    public void firstTurn() {
        FirstTurnWheel firstTurnWheel = new FirstTurnWheel(textureAtlas, this);
        stage.addActor(firstTurnWheel);
        stage.addActor(firstTurnWheel.getPointer());
        int index = 0;
        for (PlayerGui playerGui : playerGuis) {
            playerGui.setFirstTurn(index);
            playerGui.getPlayer().guiIndex = index;
            index++;
            playerImageGroup.addActor(playerGui.getImage());
        }
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                firstTurnWheel.spin();
                gameSound.playCheer();
            }
        }, 2f);
        playerImageGroup.remove();
        stage.addActor(playerImageGroup);
        stage.addActor(giftsBonusGroup);
        stage.addActor(hourGlass);

    }

    public void setGiftsStage() {
        stage.addActor(giftsBonusGroup);
        gifts.setGifts();
    }

    public void showGifts() {
        gifts.showGifts(playerGuis.get(activePlayer.guiIndex));
    }


    public void cancelGifts() {
        gifts.cancel();
    }

    public void setPlayers() {
    }

    public void animatePosition() {
        for (PlayerGui playerGui : playerGuis) {
            playerGui.animate();
        }
    }

    public Array<PlayerGui> getPlayerGuis() {
        return playerGuis;
    }

    public void startRound() {
        matchRound.setQuestion();
        gameSound.playCorrect();

        infoLabel.setText(StringRes.ROUND + (1 + gameRound));
        if (gameRound != 3) {
            infoLabel.show(tilesGroup);
        }
        startPlays();
    }

    public void setActivePlayer(int i) {
        Player player = playerGuis.get(i).getPlayer();
        player.turn = true;
        activePlayer = player;


    }


    public void showMenu() {
        Gdx.input.setInputProcessor(stage);
        timerLimit.start();
        menuButtons.showMenu(matchRound.stillHaveConsonants(), matchRound.stillHaveVocals());
    }

    public void hideMenu() {
        Gdx.input.setInputProcessor(null);
        menuButtons.hideMenu();
    }

    public void showVocals() {
        vocalKeyboard.show();
        gameSound.stopClockSound();
    }

    public void showConsonants() {
        vanna.hostSide();
        if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            if (rodaImpian.getPlayer().turn) {
                consonantKeyboard.show();
            }
        } else {
            consonantKeyboard.show();
        }

    }

    public void queueChat(String text) {
        ChatBubble chatBubble = new ChatBubble(text, skin, activePlayer.guiIndex);
        chatBubbles.addLast(chatBubble);
        showChat();
    }

    public void queueChatOnline(ChatOnline chatOnline) {
        ChatBubble chatBubble = new ChatBubble(chatOnline.content, skin, chatOnline.guiIndex);
        chatBubbles.addLast(chatBubble);
        showChat();
    }

    private void showChat() {
        stage.addActor(chatBubbles.first());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (!chatBubbles.isEmpty()) {
                    chatBubbles.first().remove();
                    chatBubbles.removeFirst();
                }
                if (!chatBubbles.isEmpty()) {
                    showChat();
                }
            }
        }, 2f);
    }

    public void showPlayerBoard() {
        stage.addActor(timerLimit);
        for (PlayerGui p : playerGuis) {
            p.setPlayerBoard(skin, playerBoardGroup);
        }
    }

    public void changeTurn() {

        menuButtons.hideMenu();
        vocalKeyboard.hide();
        consonantKeyboard.hide();
        Gdx.input.setOnscreenKeyboardVisible(false);
        timerLimit.reset();
        if (activePlayer.freeTurn) {
            removeFreeTurn();
            startPlays();
            activePlayer.freeTurn = false;
        } else {
            activePlayer.turn = false;
            hideMenu();
            int next = (activePlayer.guiIndex + 1) % playerGuis.size;
            if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
                if (activePlayer.id.equals(rodaImpian.getPlayer().id)) {
                    rodaImpian.getPlayer().turn = false;
                    SetActivePlayer setActivePlayer = new SetActivePlayer();
                    setActivePlayer.index = next;
                    sendObject(setActivePlayer);
                }
            } else {
                setActivePlayer(next);
                startPlays();
            }
        }
    }


    public void reveaAll() {
        matchRound.revealAll();
    }

    public void startPlays() {
        hourGlass.changePos(playerGuis.get(activePlayer.guiIndex));
        timerLimit.reset();

        //  bonusRound();
    }

    public void cpuChooseConsonants() {
        char c = findRandom();
        if (matchRound.completePercentage() > 0.4) {
            float decide = randomize();
            if (decide < matchRound.completePercentage() && matchRound.stillHaveConsonants()) {
                char correct = matchRound.findCorrectConsonants();
                if (correct != '*') {
                    c = correct;
                }
            }
        }


        final Character cFinal = c;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                queueChat(StringRes.CPUCHOOSECONSONANTS.random() + "\'" + cFinal + "\'");
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        matchRound.checkAnswer(cFinal);
                    }
                }, 2f);
            }
        }, 2f);
    }

    public void showFreeTurn() {
        playerGuis.get(activePlayer.guiIndex).showFreeTurn(stage, skin);
    }

    public void removeFreeTurn() {
        playerGuis.get(activePlayer.guiIndex).removeFreeTurn();
    }

    public void aiRun() {
        timerLimit.start();
        final Moves moves = decideAiMoves();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                cpuRun(moves);
            }
        }, 2f);
    }

    private Moves decideAiMoves() {
        Array<Moves> availableMoves = availableMoves();
        availableMoves.shuffle();
        Moves moves = availableMoves.random();
        float decide = randomize();
        if (decide < matchRound.completePercentage()) {
            if (matchRound.questionStillHaveConsonants()) {
                return Moves.SPINWHEEL;
            }
            if (availableMoves.contains(Moves.COMPLETE, false)) {
                return Moves.COMPLETE;
            }
            if (matchRound.questionStillHaveVocals()) {
                return Moves.BUYVOCAL;
            }
        }
        return moves;
    }

    public float randomize() {
        return new Float(MathUtils.random(0f, 1f));
    }

    public Character findRandom() {
        GameConfig.GROUP_CONS_ONE.shuffle();
        GameConfig.GROUP_CONS_TWO.shuffle();
        GameConfig.GROUP_CONS_THREE.shuffle();
        Character c;
        if ((c = checkGroupOne()) != null) {
            return c;
        }
        if ((c = checkGroupTwo()) != null) {
            return c;
        }
        return (checkGroupThree());
    }

    private Character checkGroupOne() {
        for (Character c : GameConfig.GROUP_CONS_ONE) {
            if (matchRound.getConsonants().contains(String.valueOf(c))) {
                return c;
            }
        }
        return null;
    }

    private Character checkGroupTwo() {
        for (Character c : GameConfig.GROUP_CONS_TWO) {
            if (matchRound.getConsonants().contains(String.valueOf(c))) {
                return c;
            }
        }
        return null;
    }

    private Character checkGroupThree() {
        for (Character c : GameConfig.GROUP_CONS_THREE) {
            if (matchRound.getConsonants().contains(String.valueOf(c))) {
                return c;
            }
        }
        return null;
    }

    private Array<Moves> availableMoves() {
        Array<Moves> available = new Array<>();
        if (matchRound.stillHaveConsonants()) {
            available.add(Moves.SPINWHEEL);
        }
        if (matchRound.stillHaveVocals() && activePlayer.currentScore >= 250) {
            available.add(Moves.BUYVOCAL);
        }
        if (matchRound.completePercentage() > 0.75f) {
            available.add(Moves.COMPLETE);
        }
        return available;
    }

    public void cpuRun(final Moves moves) {
        if (moves.equals(Moves.SPINWHEEL)) {
            cpuSpinWheel();
        } else if (moves.equals(Moves.BUYVOCAL)) {
            cpuBuyVocals();
        } else if (moves.equals(Moves.COMPLETE)) {
            cpuTryComplete();
        }
    }

    public void cpuBuyVocals() {
        queueChat(StringRes.CPUBUYVOCALS.random());
        activePlayer.currentScore -= 250;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                char c = matchRound.randomVocals();
                float decide = randomize();
                if (decide < matchRound.completePercentage() && matchRound.questionStillHaveVocals()) {
                    char correct = matchRound.findCorrectVocals();
                    if (correct != '*') {
                        c = correct;
                    }
                }
                final char cFinal = c;
                queueChat(StringRes.CPUCHOOSEVOCALS.random() + c);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        matchRound.checkAnswer(cFinal);
                    }
                }, 2f);

            }
        }, 2f);
    }

    private void cpuComplete() {
        queueChat(StringRes.ANSWERIS + matchRound.correctAnswers());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                matchRound.revealAll();
            }
        }, 2f);
    }

    public void cpuTryComplete() {
        queueChat(StringRes.CPUCOMPLETE);
        float decide = randomize();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (decide < matchRound.completePercentage()) {
                    cpuComplete();
                } else {
                    queueChat(StringRes.ANSWERIS + " ... ... ");
                    gameSound.playWrong();
                    vanna.hostWrong();
                    changeTurn();
                }
            }
        }, 2f);

    }

    public void cpuSpinWheel() {
        queueChat(StringRes.CPUPUTAR.random());
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                rodaImpian.spinWheel();
            }
        }, 2f);
    }

    public void completePuzzle() {
        completeGroup.clear();
        completeGroup.remove();
        completeList.clear();

        completeList.addAll(matchRound.completePuzzle());
        for (Tiles t : completeList) {
            completeGroup.addActor(t);
            t.setColor(GameConfig.VISIBLE);
        }

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(new InputCompleteKey(completeList, this));
        Gdx.input.setInputProcessor(inputMultiplexer);
        Gdx.input.setOnscreenKeyboardVisible(true);
        stage.addActor(completeGroup);
    }


    public void endGame() {
        gameEnds = true;
        timerLimit.stop();
        if (rodaImpian.getGameModes().equals(GameModes.SINGLE)) {
            if (activePlayer.id != null) {
                if (activePlayer.id.equals(rodaImpian.getPlayer().id)) {
                    PlayerOnline playerOnline = CopyPlayer.getPlayerOnline(rodaImpian.getPlayerOnline(), activePlayer);
                    playerOnline.timesplayed += 1;
                    rodaImpian.setPlayerOnline(playerOnline);
                    PlayerSaves playerSaves = new PlayerSaves();
                    playerSaves.save(activePlayer);
                    if (rodaImpian.getPlayer().logged) {
                        if (winBonus) {
                            playerOnline.bonusList.add(bonus.getBonusIndex());
                        }
                        if (activePlayer.fullScore > playerOnline.bestScore) {
                            playerOnline.bestScore = activePlayer.fullScore;
                        }
                        playerOnline.logged = true;
                        playerSaves.savePlayerOnline(playerOnline);
                        rodaImpian.uploadScore(playerOnline);
                    }
                }
            }
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                EndGameDialog endGameDialog = new EndGameDialog(skin, playerGuis.get(activePlayer.guiIndex), textureAtlas, rodaImpian);
                endGameDialog.show(stage);
                Gdx.input.setInputProcessor(stage);
                rodaImpian.showAds(2);
            }
        }, 2f);

    }

    public void checkCompleteAnswer() {
        boolean correct = true;
        Gdx.input.setInputProcessor(null);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setOnscreenKeyboardVisible(false);
        menuButtons.hideMenu();
        for (Tiles t : completeList) {
            t.setColor(GameConfig.VISIBLE);
            if (!t.checkLetter()) {
                correct = false;
            }
        }
        completeList.clear();
        completeGroup.clear();
        completeGroup.remove();
        if (correct) {

            if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
                sendObject(PlayerState.COMPLETETRUE);
            } else {
                matchRound.revealAll();
            }
        } else {
            if (gameRound == 3) {
                endGame();
            } else {
                if(rodaImpian.getGameModes().equals(GameModes.ONLINE)){
                    sendObject(PlayerState.COMPLETEWRONG);
                    menuButtons.hideMenu();
                }else {
                    changeTurn();
                }
            }
            gameSound.playAww();
            gameSound.playWrong();
        }

    }

    public void newRound() {
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

        gameRound++;

        setRound();
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
                    startRound();
                }
            }
        }, 5f);
    }

    public void checkWinner() {
        List<Integer> scores = new ArrayList<>();
        for (PlayerGui playerGui : playerGuis) {
            scores.add(playerGui.getPlayer().fullScore);
        }
        int max = Collections.max(scores);
        int min = Collections.min(scores);
        for (PlayerGui playerGui : playerGuis) {
            if (playerGui.getPlayer().fullScore == max) {
                setActivePlayer(playerGui.getPlayer().guiIndex);
                tilesGroup.addActor(new Trophy(textureAtlas, 0, playerGui.getPlayerPos()));
            } else if (playerGui.getPlayer().fullScore == min) {
                tilesGroup.addActor(new Trophy(textureAtlas, 2, playerGui.getPlayerPos()));
            } else {
                tilesGroup.addActor(new Trophy(textureAtlas, 1, playerGui.getPlayerPos()));
            }
        }

        if (!activePlayer.isAi) {

            infoLabel.setText(StringRes.BONUSROUND);
            infoLabel.show(tilesGroup);
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    tilesGroup.clear();
                    rodaImpian.spinWheel();
                    for (PlayerGui playerGui : playerGuis) {
                        if (playerGui.getPlayer() != activePlayer) {
                            playerGui.getImage().remove();
                        }
                    }
                }
            }, 4f);
        } else {

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    tilesGroup.clear();
                    infoLabel.setText(StringRes.PEMENANGIALAH + activePlayer.name);
                    infoLabel.show(tilesGroup);
                    for (PlayerGui playerGui : playerGuis) {
                        if (playerGui.getPlayer() != activePlayer) {
                            playerGui.getImage().remove();
                        }
                    }
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            endGame();
                        }
                    }, 2f);
                }
            }, 2f);
        }
    }

    public void bonusRound() {

        bonusRound = true;
        timerLimit.reset();
        timerLimit.stop();
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
                        if (!envelopeClicked) {
                            clickEnveloped(envelopes);
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


    private void clickEnveloped(Envelopes envelopes) {
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

    public void chooseBonusConsonant() {
        infoLabel.setText(StringRes.CHOOSE5CONS);
        infoLabel.show(tilesGroup);
        consonantKeyboard.setBonusRound(true);
        vocalKeyboard.setBonusRound(true);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                showConsonants();
            }
        }, 3f);

    }

    public void removeHourGlass() {
        hourGlass.remove();
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public WheelParam getWheelParam() {
        return wheelParam;
    }

    public Gifts getGifts() {
        return gifts;
    }

    public int getGiftIndex() {
        return gifts.getGiftIndex();
    }

    public void setGiftOnline(int giftIndex) {

    }

    public void completeBonus() {
        completePuzzle();
        menuButtons.showCompleteMenu();
        stage.addActor(timerLimit);
        timerLimit.start();
    }

    public int getGameRound() {
        return gameRound;
    }

    public void setBonus(Bonus bonus) {
        this.bonus = bonus;
    }

    public Vanna getVanna() {
        return vanna;
    }

    public void flyingMoney() {
        stage.addActor(new FlyingMoney(textureAtlas.findRegion("3_badgebankrupt"), playerGuis.get(activePlayer.guiIndex).getPlayerPos()));
    }

    public void update(float delta) {

        if (!rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            adsLoadCounter -= delta;
            if (adsLoadCounter <= 0) {
                rodaImpian.loadAds();
                adsLoadCounter = ADSCOUNTER;
            }
        }

        if (rodaImpian.getGameModes().equals(GameModes.ONLINE)) {
            if (rodaImpian.getPlayer().turn) {
                if (timerLimit.isChangeTurn()) {
                    if (!bonusRound) {
                        changeTurn();
                        timerLimit.reset();
                    } else {
                        timerLimit.reset();
                        timerLimit.stop();
                        endGame();
                    }
                }
            }
        } else {
            if (timerLimit.isChangeTurn()) {
                if (!bonusRound) {
                    changeTurn();
                    timerLimit.reset();
                } else {
                    timerLimit.reset();
                    timerLimit.stop();
                    endGame();
                }
            }
        }
    }

    public void sendObject(final Object o) {
    }

    public void spinWheel() {
        rodaImpian.spinWheel();
        gameSound.stopClockSound();
    }


    public void setWheelParam(WheelParam wheelParam) {
        this.wheelParam = wheelParam;
    }

    public Bonus getBonus() {
        return bonus;
    }

    public TimerLimit getTimerLimit() {
        return timerLimit;
    }

    public boolean isBonusRound() {
        return bonusRound;
    }

    public void setWinBonus(boolean winBonus) {
        this.winBonus = winBonus;
    }
}

