package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Logger;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.games.MenuState;
import com.somboi.rodaimpian.gdxnew.screens.GameScreen;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;
import com.somboi.rodaimpian.gdxnew.screens.OnlineScreen;
import com.somboi.rodaimpian.saves.PlayerSaves;

import java.util.ArrayList;

public class MenuFactory {
    private final AssetManager assetManager;
    private final Skin skin;
    private final Stage stage;
    private final TextureAtlas atlas;
    private Vector2 buttonposition;
    private ProfileTable playerProfile;
    private ProfileTable playerProfileTwo;
    private ProfileTable playerProfileThree;
    private final Table mainButtons;
    private final MainScreen mainScreen;
    private final RodaImpianNew rodaImpianNew;
    private MenuButton singlePlayer;
    private MenuButton startMulti;
    private MenuButton multiPlayer;
    private MenuButton leaderBoard;
    private MenuButton login;
    private MenuButton online;
    private MenuButton offline;
    private MenuButton twoPlayer;
    private MenuButton threePlayer;
    private MenuState menuState;
    private PlayerNew playerNewTwo;
    private PlayerNew playerNewThree;
    public MenuFactory(RodaImpianNew rodaImpianNew, Stage stage) {
        this.assetManager = rodaImpianNew.getAssetManager();
        this.skin = assetManager.get(AssetDesc.NEWSKIN);
        this.stage = stage;
        this.atlas = assetManager.get(AssetDesc.ATLAS);
        this.rodaImpianNew = rodaImpianNew;
        this.mainScreen = rodaImpianNew.getMainScreen();
        mainButtons = new Table();
        mainButtons.defaults().pad(10f);
        checkPlayerTwoThree();
        showMainMenu();
    }

    private void checkPlayerTwoThree() {
        PlayerSaves playerSaves = new PlayerSaves();

        playerNewTwo = playerSaves.loadPlayerNewTwo();
        if (playerNewTwo == null) {
            playerNewTwo = createNewPlayer(2);
        }
        playerNewTwo.setScore(0);
        playerNewTwo.setFullScore(0);
        playerNewTwo.setAi(false);
        playerSaves.savePlayerNewTwo(playerNewTwo);

        playerNewThree = playerSaves.loadPlayerNewThree();
        if (playerNewThree == null) {
            playerNewThree = createNewPlayer(3);
        }
        playerNewThree.setScore(0);
        playerNewThree.setFullScore(0);
        playerNewTwo.setAi(false);
        playerSaves.savePlayerNewThree(playerNewThree);


        playerProfileTwo = new ProfileTable(atlas.findRegion("defaultavatar"), skin, playerNewTwo, 2,
                rodaImpianNew.isGoldTheme(), rodaImpianNew.getMainScreen());
        playerProfileTwo.pack();
        playerProfileTwo.setPosition(450f - playerProfileTwo.getWidth() / 2f, 825f);

        playerProfileThree = new ProfileTable(atlas.findRegion("defaultavatar"), skin, playerNewThree, 3,
                rodaImpianNew.isGoldTheme(), rodaImpianNew.getMainScreen());
        playerProfileThree.pack();
        playerProfileThree.setPosition(450f - playerProfileThree.getWidth() / 2f, 825f-20f-playerProfileTwo.getHeight());
    }

    private PlayerNew createNewPlayer(int n) {
        PlayerNew playerNew = new PlayerNew();
        playerNew.setName(StringRes.PLAYER_NAME + n);
        playerNew.setUid("0"+n);
        playerNew.setPlayerBonus(new ArrayList<>());
        playerNew.setPlayerGifts(new ArrayList<>());
        return playerNew;
    }

    public void showMainMenu() {
        stage.clear();
        menuState = MenuState.MAIN;
        createButtons();
        createBackground();
        createPlayerProfile();
        createMainMenu();
    }

    private void createButtons() {
        singlePlayer = new MenuButton(StringRes.SINGLEPLAYER, skin);
        singlePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainScreen.singlePlay();
            }
        });

        startMulti = new MenuButton(StringRes.START, skin);
        startMulti.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainScreen.singlePlay();
            }
        });

        multiPlayer = new MenuButton(StringRes.MULTIPLAYER, skin);
        multiPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createMultiMenuFirst();
            }
        });


        leaderBoard = new MenuButton(StringRes.LEADERBOARD, skin);

        login = new MenuButton(StringRes.LOGOUT, skin);
        if (!rodaImpianNew.getPlayer().isLogged()) {
            login.setText(StringRes.LOGINFB);
            login.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mainScreen.loginFacebook();
                }
            });
        }

        online = new MenuButton(StringRes.ONLINE, skin);
        online.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (!rodaImpianNew.getPlayer().isLogged()) {
                    LoginDiag loginDiag = new LoginDiag(skin, rodaImpianNew);
                    loginDiag.show(stage);
                }else{
                    rodaImpianNew.setGameModes(GameModes.ONLINE);
                    rodaImpianNew.setScreen(new OnlineScreen(rodaImpianNew));
                }
            }
        });
        offline = new MenuButton(StringRes.Offline, skin);
        offline.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createMultiSecond();
            }
        });

        twoPlayer = new MenuButton(StringRes.TWOPLAYER, skin);
        twoPlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createTwoPlayerOffline();
            }
        });

        threePlayer = new MenuButton(StringRes.THREEPLAYER, skin);
        threePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createThreePlayerOffline();
            }
        });
    }
    private void updateMultiButton(){
        startMulti.pack();
        startMulti.setPosition(450f- startMulti.getWidth()/2f, 200f);
    }

    private void createBackground() {
        if (rodaImpianNew.isGoldTheme()) {
            stage.addActor(new Image(assetManager.get(AssetDesc.BLURGOLD)));
        } else {
            stage.addActor(new Image(assetManager.get(AssetDesc.BLURRED)));
        }
        Image titleLogo = new Image(atlas.findRegion("titlelogo"));
        titleLogo.setPosition(39f, 1291f);
        stage.addActor(titleLogo);
    }

    public void createMainMenu() {
        menuState = MenuState.MAIN;
        mainButtons.clear();
        mainButtons.add(singlePlayer).row();
        mainButtons.add(multiPlayer).row();
        mainButtons.add(leaderBoard).row();
        if (!rodaImpianNew.getPlayer().isLogged()) {
            mainButtons.add(login).row();
        }
        updateMainButtonPos();
        stage.addActor(mainButtons);
    }

    private void updateMainButtonPos() {
        mainButtons.pack();
        mainButtons.setPosition(450f - mainButtons.getWidth() / 2f, buttonposition.y - mainButtons.getWidth() + 30f);
    }

    public void createMultiMenuFirst() {
        menuState = MenuState.MULTI;
        mainButtons.clear();
        mainButtons.add(offline).row();
        mainButtons.add(online).row();
        updateMainButtonPos();
    }

    public void createMultiSecond() {
        playerProfileTwo.remove();
        playerProfileThree.remove();
        startMulti.remove();
        stage.addActor(playerProfile);
        menuState = MenuState.MULTISECOND;
        mainButtons.clear();
        mainButtons.add(twoPlayer).row();
        mainButtons.add(threePlayer).row();
        updateMainButtonPos();
    }

    private void createTwoPlayerOffline() {
        menuState = MenuState.TWOPLAYER;
        mainButtons.clear();
        playerProfile.remove();
        stage.addActor(playerProfileTwo);
        startMulti = new MenuButton(StringRes.START, skin);
        startMulti.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.setPlayerTwo(playerNewTwo);
                mainScreen.singlePlay();
            }
        });
        updateMultiButton();
        stage.addActor(startMulti);

    }

    private void createThreePlayerOffline() {
        menuState = MenuState.THREEPLAYER;
        playerProfile.remove();
        mainButtons.clear();
        stage.addActor(playerProfileTwo);
        stage.addActor(playerProfileThree);
        startMulti = new MenuButton(StringRes.START, skin);
        startMulti.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpianNew.setPlayerTwo(playerNewTwo);
                rodaImpianNew.setPlayerThree(playerNewThree);
                mainScreen.singlePlay();
            }
        });
        updateMultiButton();
        stage.addActor(startMulti);

    }


    private void createPlayerProfile() {
        if (playerProfile != null) {
            playerProfile.remove();
        }
        playerProfile = new ProfileTable(atlas.findRegion("defaultavatar"), skin, rodaImpianNew.getPlayer(), 1,
                rodaImpianNew.isGoldTheme(), mainScreen);

        playerProfile.setPosition(450f - playerProfile.getWidth() / 2f, 825f);

        buttonposition = new Vector2(playerProfile.getX(), playerProfile.getY());
        stage.addActor(playerProfile);
    }

    public void reloadProfilePic(String picUri) {
        playerProfile.reloadPicUrl(picUri);
    }

    public MenuState getMenuState() {
        return menuState;
    }
}
