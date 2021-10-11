package com.somboi.gdx.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.ChatBubble;
import com.somboi.gdx.actor.Confetti;
import com.somboi.gdx.actor.ConsonantKeyboard;
import com.somboi.gdx.actor.CorrectScore;
import com.somboi.gdx.actor.EndGameDialog;
import com.somboi.gdx.actor.EnvelopeSubject;
import com.somboi.gdx.actor.Envelopes;
import com.somboi.gdx.actor.FirstTurnWheel;
import com.somboi.gdx.actor.FlyingMoney;
import com.somboi.gdx.actor.HourGlass;
import com.somboi.gdx.actor.Sparkling;
import com.somboi.gdx.actor.Trophy;
import com.somboi.gdx.actor.Vanna;
import com.somboi.gdx.actor.VocalKeyboard;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.GameSound;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.config.GameConfig;
import com.somboi.gdx.entities.Bonus;
import com.somboi.gdx.entities.Gifts;
import com.somboi.gdx.entities.MatchRound;
import com.somboi.gdx.entities.MenuButtons;
import com.somboi.gdx.entities.Player;
import com.somboi.gdx.entities.PlayerGui;
import com.somboi.gdx.entities.Tiles;
import com.somboi.gdx.entities.WheelParam;
import com.somboi.gdx.listener.InputCompleteKey;
import com.somboi.gdx.screen.WheelScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModeBase {
    protected final AssetManager assetManager;
    protected final RodaImpian rodaImpian;
    protected final Skin skin;
    protected final Stage stage;
    protected final Array<PlayerGui> playerGuis = new Array<>();
    protected final Player thisPlayer;
    protected final TextureAtlas textureAtlas;
    protected Player activePlayer;
    protected final GameSound gameSound;
    protected MatchRound matchRound;
    protected final Vanna vanna;

    private final Queue<ChatBubble> chatBubbles = new Queue<>();
    private int gameRound = 0;
    private ConsonantKeyboard consonantKeyboard;
    private VocalKeyboard vocalKeyboard;
    private final Group menuGroup = new Group();
    private MenuButtons menuButtons;
    private final WheelParam wheelParam = new WheelParam();
    private final Group playerImageGroup = new Group();
    private final Group playerBoardGroup = new Group();
    private final Group completeGroup = new Group();
    private final Group tilesGroup = new Group();
    private final Group giftsBonusGroup = new Group();
    private final List<Tiles> completeList = new ArrayList<>();
    private final Gifts gifts;
    private final HourGlass hourGlass;
    private boolean envelopeClicked;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private final CorrectScore infoLabel;
    private Bonus bonus;
    public ModeBase(RodaImpian rodaImpian, Stage stage) {
        this.rodaImpian = rodaImpian;
        this.thisPlayer = rodaImpian.getPlayer();
        this.assetManager = rodaImpian.getAssetManager();
        this.gameSound = new GameSound(assetManager);
        this.skin = assetManager.get(AssetDesc.SKIN);
        this.textureAtlas = assetManager.get(AssetDesc.TEXTUREATLAS);
        this.stage = stage;
        this.infoLabel = new CorrectScore("", skin);
        vanna = new Vanna(rodaImpian.getAssetManager().get(AssetDesc.TEXTUREATLAS));
        gifts = new Gifts(textureAtlas, assetManager.get(AssetDesc.SPARKLE), giftsBonusGroup);
        hourGlass = new HourGlass(assetManager.get(AssetDesc.HOURGLASS));
        wheelParam.results = "";
        stage.addActor(tilesGroup);
        stage.addActor(menuGroup);
        stage.addActor(playerBoardGroup);
        stage.addActor(playerImageGroup);
        stage.addActor(vanna);
        setPlayers();
        playerGuis.shuffle();
        rodaImpian.setWheelScreen(new WheelScreen(rodaImpian, this));
        setRound();
        firstTurn();
    }

    private void setRound() {
        menuButtons = new MenuButtons(menuGroup, rodaImpian, this);
        matchRound = new MatchRound(rodaImpian, gameSound, textureAtlas, tilesGroup, skin, gameRound, this);
        consonantKeyboard = new ConsonantKeyboard(skin, matchRound, stage);
        vocalKeyboard = new VocalKeyboard(skin, matchRound, stage);
    }

    private void firstTurn() {
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
        menuButtons.showMenu(matchRound.stillHaveConsonants(), matchRound.stillHaveVocals());
    }

    public void hideMenu() {
        Gdx.input.setInputProcessor(null);
        menuButtons.hideMenu();
    }

    public void showVocals() {
        vocalKeyboard.show();
    }

    public void showConsonants() {
        vanna.hostSide();
        consonantKeyboard.show();
    }

    public void queueChat(String text) {
        ChatBubble chatBubble = new ChatBubble(text, skin, activePlayer.guiIndex);
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
        for (PlayerGui p : playerGuis) {
            p.setPlayerBoard(skin, playerBoardGroup);
        }
    }

    public void changeTurn() {
       // vanna.hostRelax();
        if (activePlayer.freeTurn) {
            removeFreeTurn();
            startPlays();
            activePlayer.freeTurn = false;
        } else {
            hideMenu();
            int next = (activePlayer.guiIndex + 1) % 3;
            setActivePlayer(next);
            startPlays();
        }
    }

    public void startPlays() {
        hourGlass.changePos(playerGuis.get(activePlayer.guiIndex));

        //  bonusRound();
    }

    public void cpuChooseConsonants() {
    }

    public void showFreeTurn() {
        playerGuis.get(activePlayer.guiIndex).showFreeTurn(stage, skin);
    }

    public void removeFreeTurn() {
        playerGuis.get(activePlayer.guiIndex).removeFreeTurn();
    }

    public void aiRun() {
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


    public void endGame(){
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                EndGameDialog endGameDialog = new EndGameDialog(skin, playerGuis.get(activePlayer.guiIndex),textureAtlas,rodaImpian);
                endGameDialog.show(stage);
            }
        },2f);

    }

    public void checkCompleteAnswer() {
        boolean correct = true;
        Gdx.input.setInputProcessor(null);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setOnscreenKeyboardVisible(false);
        menuButtons.hideMenu();
        for (Tiles t : completeList) {
            t.setColor(GameConfig.VISIBLE);
            logger.debug("tile letter "+t.getLetter()+", check letter "+t.getCompleteLetter());
            if (!t.checkLetter()) {
                correct = false;
            }
        }
        completeList.clear();
        completeGroup.clear();
        completeGroup.remove();
        if (correct) {
            matchRound.revealAll();
        } else {
            if (gameRound==3) {
                endGame();
            }else{
                changeTurn();
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

    private void checkWinner() {
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
                    for (PlayerGui playerGui: playerGuis){
                        if (playerGui.getPlayer()!=activePlayer){
                            playerGui.getImage().remove();
                        }
                    }
                }
            }, 4f);
        }else{

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    tilesGroup.clear();
                    infoLabel.setText(StringRes.PEMENANGIALAH+activePlayer.name);
                    infoLabel.show(tilesGroup);
                    for (PlayerGui playerGui: playerGuis){
                        if (playerGui.getPlayer()!=activePlayer){
                            playerGui.getImage().remove();
                        }
                    }
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            endGame();
                        }
                    },2f);
                }
            }, 2f);
        }
    }

    public void bonusRound() {
        Array<Integer> integers = new Array<>(new Integer[]{0, 1, 2});
        integers.shuffle();

        for (int i = 0; i < 3; i++) {
            final Envelopes envelopes = new Envelopes(textureAtlas, integers.get(i));
            envelopes.setPosition(150 + (300 * i) - envelopes.getWidth() / 2f, 674f);
            envelopes.addListener(new DragListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (!envelopeClicked) {
                        envelopes.opened();
                        float xPosition = envelopes.getX() + envelopes.getWidth() / 2f;
                        tilesGroup.addActor(new EnvelopeSubject(skin, rodaImpian.getQuestionsReady().getSubjectRoundFour(), xPosition));
                        envelopeClicked = true;
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                tilesGroup.clear();
                                matchRound.setQuestion();
                                chooseBonusConsonant();
                            }
                        }, 3f);
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            tilesGroup.addActor(envelopes);
            playerBoardGroup.addActor(bonus.getBonusImage());
            tilesGroup.addActor(new Sparkling(assetManager.get(AssetDesc.SPARKLE)));
            infoLabel.setText(StringRes.CHOOSEENVELOPE);
            infoLabel.show(tilesGroup);
        }
    }

    public void chooseBonusConsonant() {
        infoLabel.setText(StringRes.CHOOSE5CONS);
        infoLabel.show(tilesGroup);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                showConsonants();
            }
        },3f);

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
    public void completeBonus(){
        completePuzzle();
        menuButtons.showCompleteMenu();
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

    public void flyingMoney(){
        stage.addActor(new FlyingMoney(textureAtlas.findRegion("3_badgebankrupt"), playerGuis.get(activePlayer.guiIndex).getPlayerPos()));
    }

    public void update(float delta){};
}

