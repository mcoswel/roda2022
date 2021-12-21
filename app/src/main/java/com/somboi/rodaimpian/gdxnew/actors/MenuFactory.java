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
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;

public class MenuFactory {
    private final AssetManager assetManager;
    private final Skin skin;
    private final Stage stage;
    private final TextureAtlas atlas;
    private Vector2 buttonposition;
    private ProfileTable playerProfile;
    private Table mainButtons;

    public MenuFactory(AssetManager assetManager, Skin skin, Stage stage) {
        this.assetManager = assetManager;
        this.skin = skin;
        this.stage = stage;
        this.atlas = assetManager.get(AssetDesc.ATLAS);
    }

    public void createBackground(boolean goldTheme) {
        if (goldTheme) {
            stage.addActor(new Image(assetManager.get(AssetDesc.BLURGOLD)));
        } else {
            stage.addActor(new Image(assetManager.get(AssetDesc.BLURRED)));
        }
        Image titleLogo = new Image(atlas.findRegion("titlelogo"));
        titleLogo.setPosition(39f, 1291f);
        stage.addActor(titleLogo);
    }

    public void createMenuButtons(MainScreen mainScreen, boolean isLogged) {
        mainButtons = new Table();
        mainButtons.defaults().pad(10f);
        MenuButton singlePlayer = new MenuButton(StringRes.SINGLEPLAYER, skin);
        singlePlayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mainScreen.startGame();
            }
        });
        MenuButton multiPlayer = new MenuButton(StringRes.MULTIPLAYER, skin);
        MenuButton leaderBoard = new MenuButton(StringRes.LEADERBOARD, skin);
        MenuButton log = new MenuButton(StringRes.LOGOUT, skin);
        if (!isLogged) {
            log.setText(StringRes.LOGINFB);
            log.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    mainScreen.loginFacebook();
                }
            });
        }
        mainButtons.add(singlePlayer).row();
        mainButtons.add(multiPlayer).row();
        mainButtons.add(leaderBoard).row();
        mainButtons.add(log).row();
        mainButtons.pack();
        mainButtons.setPosition(450f - mainButtons.getWidth() / 2f, buttonposition.y - mainButtons.getWidth() + 10f);
        stage.addActor(mainButtons);
    }

    public void createPlayerProfile(PlayerNew playerNew, boolean isGold, MainScreen mainScreen) {
        if (playerProfile != null) {
            playerProfile.remove();
        }
        playerProfile = new ProfileTable(atlas.findRegion("defaultavatar"), skin, playerNew, isGold, mainScreen);
        playerProfile.setPosition(450f - playerProfile.getWidth() / 2f, 825f);
        buttonposition = new Vector2(playerProfile.getX(), playerProfile.getY());
        stage.addActor(playerProfile);
    }


}
