package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.NameField;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.screen.MatchScreen;
import com.somboi.rodaimpian.gdx.screen.MenuScreen;
import com.somboi.rodaimpian.gdx.utils.RoundMap;
import com.somboi.rodaimpian.saves.PlayerSaves;

public class LocalPlayMenu {
    private final RodaImpian rodaImpian;
    private final Group stage;
    private final Skin skin;
    private final TextureAtlas textureAtlas;
    private final MenuScreen menuScreen;
    private final FileHandle p2ImgPath = Gdx.files.local(StringRes.PLY2IMAGEPATH);
    private final FileHandle p3ImgPath = Gdx.files.local(StringRes.PLY3IMAGEPATH);

    private  PlayerImage playerTwoImage;
    private  PlayerImage playerThreeImage;

    private final NameField playerTwoName;
    private final NameField playerThreeName;
    private final LargeButton back;
    private Table playerTwoTable;
    private Table playerThreeTable;

    private boolean loadedPlayerTwo;
    private boolean loadedPlayerThree;

    public LocalPlayMenu(RodaImpian rodaImpian, Group stage, Skin skin, TextureAtlas textureAtlas, MenuScreen menuScreen) {
        this.rodaImpian = rodaImpian;
        this.stage = stage;
        this.skin = skin;
        this.textureAtlas = textureAtlas;
        this.menuScreen = menuScreen;
        playerTwoName = new NameField(StringRes.PLAYERTWO, skin);
        playerThreeName = new NameField(StringRes.PLAYERTHREE, skin);
        if (p2ImgPath.exists()){
            playerTwoImage = new PlayerImage(new Texture(RoundMap.execute(new Texture(p2ImgPath))));
            loadedPlayerTwo = true;
        }else{
            playerTwoImage = new PlayerImage(textureAtlas.findRegion("default_avatar"));
        }

        if (p3ImgPath.exists()){
            playerThreeImage = new PlayerImage(new Texture(RoundMap.execute(new Texture(p3ImgPath))));
            loadedPlayerThree = true;
        }else{
            playerThreeImage = new PlayerImage(textureAtlas.findRegion("default_avatar"));
        }


        back = new LargeButton(StringRes.BACK, skin);

    }

    public void showFirstMenu() {
        stage.clear();
        Table firstTable = new Table();
        firstTable.defaults().pad(15f);
        LargeButton singlePlay = new LargeButton(StringRes.ONEPLAYER, skin);
        singlePlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                rodaImpian.setMatchScreen(new MatchScreen(rodaImpian));
                rodaImpian.gotoMatch();
            }
        });
        LargeButton doublePlay = new LargeButton(StringRes.TWOPLAYER, skin);
        doublePlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showSecondMenu();
            }
        });
        LargeButton triplePlay = new LargeButton(StringRes.THREEPLAYER, skin);
        triplePlay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showThirdMenu();
            }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuScreen.show();
            }
        });
        firstTable.add(singlePlay).row();
        firstTable.add(doublePlay).row();
        firstTable.add(triplePlay).row();
        firstTable.add(back).row();
        firstTable.pack();
        firstTable.setPosition(450f - firstTable.getWidth() / 2f, 900f - firstTable.getHeight() / 2f - 200f);
        stage.addActor(firstTable);
        createPlayerTwoTable();
        createPlayerThreeTable();
    }

    public void createPlayerTwoTable() {
        Table subTable = new Table();
        TextButton choosePhoto = new TextButton(StringRes.CHOOSEPHOTO, skin);
        choosePhoto.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.choosePhoto(1);
            }
        });

        subTable.add(playerTwoName).row();
        subTable.add(choosePhoto).row();
        playerTwoTable = new Table();
        playerTwoTable.add(playerTwoImage).pad(10f);
        playerTwoTable.add(subTable).padTop(10f);
    }

    public void createPlayerThreeTable() {
        Table subTable = new Table();
        TextButton choosePhoto = new TextButton(StringRes.CHOOSEPHOTO, skin);
        choosePhoto.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.choosePhoto(2);
            }
        });

        subTable.add(playerThreeName).row();
        subTable.add(choosePhoto).row();
        playerThreeTable = new Table();
        playerThreeTable.add(playerThreeImage).pad(10f);
        playerThreeTable.add(subTable).padTop(10f);
    }


    public void showSecondMenu() {
        stage.clear();
        Table secondTable = new Table();

        LargeButton mula = new LargeButton(StringRes.START, skin);
        mula.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = new Player();
                player.id = "02";
                player.isAi = false;
                player.name = playerTwoName.getText().replaceAll("[^a-zA-Z0-9]", "");
                rodaImpian.setPlayerTwo(player);
                rodaImpian.setPlayerTwoImage(playerTwoImage);
                rodaImpian.setGameModes(GameModes.LOCALMULTI);
                rodaImpian.setMatchScreen(new MatchScreen(rodaImpian));
                rodaImpian.gotoMatch();

            }
        });

        LargeButton backOneStep = new LargeButton(StringRes.BACK, skin);
        backOneStep.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showFirstMenu();
            }
        });
        secondTable.add(playerTwoTable).row();
        secondTable.add(mula).padTop(20f).row();
        secondTable.add(backOneStep).padTop(20f).row();
        secondTable.pack();
        secondTable.setPosition(450f - secondTable.getWidth() / 2f, 900f - secondTable.getHeight() / 2f - 200f);
        stage.addActor(secondTable);

    }

    public void showThirdMenu() {
        stage.clear();
        Table thirdTable = new Table();

        LargeButton mula = new LargeButton(StringRes.START, skin);
        mula.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = new Player();
                player.id = "02";
                player.isAi = false;
                player.name = playerTwoName.getText().replaceAll("[^a-zA-Z0-9]", "");
                rodaImpian.setPlayerTwo(player);
                rodaImpian.setPlayerTwoImage(playerTwoImage);

                Player playerThree = new Player();
                playerThree.id = "03";
                playerThree.isAi = false;
                playerThree.name = playerThreeName.getText().replaceAll("[^a-zA-Z0-9]", "");
                rodaImpian.setPlayerThree(playerThree);
                rodaImpian.setPlayerThreeImage(playerThreeImage);

                rodaImpian.setGameModes(GameModes.LOCALMULTI);
                rodaImpian.setMatchScreen(new MatchScreen(rodaImpian));
                rodaImpian.gotoMatch();

            }
        });

        LargeButton backOneStep = new LargeButton(StringRes.BACK, skin);
        backOneStep.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showFirstMenu();
            }
        });
        thirdTable.add(playerTwoTable).row();
        thirdTable.add(playerThreeTable).row();

        thirdTable.add(mula).padTop(20f).row();
        thirdTable.add(backOneStep).padTop(20f).row();
        thirdTable.pack();
        thirdTable.setPosition(450f - thirdTable.getWidth() / 2f, 900f - thirdTable.getHeight() / 2f - 200f);
        stage.addActor(thirdTable);

    }


    public void loadLocalPic() {

        if (p2ImgPath.exists() && !loadedPlayerTwo) {
            Texture texture = new Texture(RoundMap.execute(new Texture(p2ImgPath)));
            playerTwoImage.setDrawable(new SpriteDrawable(new Sprite(texture)));
            loadedPlayerTwo = true;
        }

        if (p3ImgPath.exists() && !loadedPlayerThree) {
            Texture texture = new Texture(RoundMap.execute(new Texture(p3ImgPath)));
            playerThreeImage.setDrawable(new SpriteDrawable(new Sprite(texture)));
            loadedPlayerThree = true;
        }
    }

    public void update() {

    }
}

