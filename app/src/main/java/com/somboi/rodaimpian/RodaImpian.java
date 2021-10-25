package com.somboi.rodaimpian;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.somboi.rodaimpian.android.AndroidInterface;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.android.onlinemsg.UpdateNews;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.assets.QuestionsReady;
import com.somboi.rodaimpian.gdx.entities.MainMenuCreator;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.online.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.entities.SessionList;
import com.somboi.rodaimpian.gdx.online.newentities.SessionRoom;
import com.somboi.rodaimpian.gdx.screen.LoadingScreen;
import com.somboi.rodaimpian.gdx.screen.MatchScreen;
import com.somboi.rodaimpian.gdx.screen.MenuScreen;
import com.somboi.rodaimpian.gdx.screen.OnlineMatchScreen;
import com.somboi.rodaimpian.gdx.screen.WheelScreen;

public class RodaImpian extends Game {
    private final AssetManager assetManager = new AssetManager();
    private WheelScreen wheelScreen;
    private MenuScreen menuScreen;
    private MatchScreen matchScreen;
    private QuestionsReady questionsReady;
    private final AndroidInterface androidInterface;
    private Player player;
    private PlayerImage playerImage;
    private GameModes gameModes;
    private PlayerOnline playerOnline;
    private SessionRoom sessionRoom;
    private SessionList sessionList = new SessionList();
    private int bestScore;
    private Player PlayerTwo;
    private Player PlayerThree;
    private PlayerImage PlayerTwoImage;
    private PlayerImage PlayerThreeImage;
    private OnlineMatchScreen onlineScreen;
    private boolean rewarded;

    public RodaImpian(AndroidInterface androidInterface) {
        this.androidInterface = androidInterface;
    }

    @Override
    public void create() {
        //  PopulateQuestions populateQuestions = new PopulateQuestions();

        setScreen(new LoadingScreen(this));
        Gdx.app.setLogLevel(3);

    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void spinWheel() {
        this.setScreen(wheelScreen);
    }

    public void chat(int guiIndex, OnlinePlay onlinePlay) {
        androidInterface.chat(guiIndex, onlinePlay);
    }

    public void exitAll() {
        androidInterface.finishAct();
    }

    public void restart(){
        androidInterface.restartGame();
    }

    public void setWheelScreen(WheelScreen wheelScreen) {
        this.wheelScreen = wheelScreen;
    }

    public WheelScreen getWheelScreen() {
        return wheelScreen;
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public void gotoMenu() {
        this.setScreen(menuScreen);
    }

    public QuestionsReady getQuestionsReady() {
        return questionsReady;
    }

    public void setMatchScreen(MatchScreen matchScreen) {
        this.matchScreen = matchScreen;
    }

    public void gotoMatch() {
        if (matchScreen != null) {
            this.setScreen(matchScreen);
        }
    }


    public void setQuestionsReady(QuestionsReady questionsReady) {
        this.questionsReady = questionsReady;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        androidInterface.setPlayer(player);
    }

    public void setPlayerOnline(PlayerOnline playerOnline) {
        this.playerOnline = playerOnline;
        androidInterface.setPlayerOnline(playerOnline);
    }

    public void setMenuCreator(MainMenuCreator mainMenuCreator) {
        androidInterface.setMenuCreator(mainMenuCreator);
    }

    public void choosePhoto(int playerInt) {
        androidInterface.choosePhoto(playerInt);
    }

    public GameModes getGameModes() {
        return gameModes;
    }

    public void setGameModes(GameModes gameModes) {
        this.gameModes = gameModes;
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(PlayerImage playerImage) {

        this.playerImage = playerImage;
    }

    public void loginFB() {
        androidInterface.loginFB();
    }


    public PlayerOnline getPlayerOnline() {
        return playerOnline;
    }

    public void startOnlineChat() {
        androidInterface.onlineChat();
    }

    public SessionRoom getSessionRoom() {
        return sessionRoom;
    }

    public void setSessionRoom(SessionRoom sessionRoom) {
        this.sessionRoom = sessionRoom;
    }

    public SessionList getSessionList() {
        return sessionList;
    }


    public void uploadScore(PlayerOnline playerOnline) {
        androidInterface.uploadScore(playerOnline);
    }

    public void loadAds() {
        androidInterface.loadAllAds();
    }

    public void showAds(int gameRound) {
        androidInterface.showAds(gameRound);
    }


    public void gotoLeaderBoard() {
        androidInterface.leaderBoard();
    }

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public void openComment() {
        androidInterface.comments();
    }

    public Player getPlayerTwo() {
        return PlayerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        PlayerTwo = playerTwo;
    }

    public Player getPlayerThree() {
        return PlayerThree;
    }

    public void setPlayerThree(Player playerThree) {
        PlayerThree = playerThree;
    }

    public PlayerImage getPlayerTwoImage() {
        return PlayerTwoImage;
    }

    public void setPlayerTwoImage(PlayerImage playerTwoImage) {
        PlayerTwoImage = playerTwoImage;
    }

    public PlayerImage getPlayerThreeImage() {
        return PlayerThreeImage;
    }

    public void setPlayerThreeImage(PlayerImage playerThreeImage) {
        PlayerThreeImage = playerThreeImage;
    }

    public void stop() {
        if (matchScreen!=null){
            matchScreen.stop();
        }
        if (wheelScreen!=null){
            wheelScreen.stop();
        }
    }

    public void setOnlineScreen(OnlineMatchScreen onlineScreen) {
        this.onlineScreen = onlineScreen;
    }

    public void gotoOnlineScreen() {
        setScreen(onlineScreen);
    }

    public void openPlayStore(String uri) {
        androidInterface.openPlayStore(uri);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public void setRewarded(boolean b) {
        this.rewarded = b;
    }

    public boolean isRewarded() {
        return rewarded;
    }

    public void loadRewardedAds() {
        androidInterface.loadRewardedAds();
    }

    public void showRewardedAds() {
        androidInterface.showRewarded();
    }

    public void logoutFB(String playerID) {
        androidInterface.logoutFacebook(playerID);
    }

    public void getUpdate() {
        androidInterface.getUpdateNews();
    }

    public void showUpdateNews(UpdateNews updateNews) {
        menuScreen.showUpdate(updateNews);
    }

    public void reloadMainMenu() {
        menuScreen.show();
    }
    public void loginGoogle(){
        androidInterface.loginGmail();
    }
}
