package com.somboi.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.actor.LargeButton;
import com.somboi.gdx.actor.NameField;
import com.somboi.gdx.actor.PlayerImage;
import com.somboi.gdx.assets.AssetDesc;
import com.somboi.gdx.assets.StringRes;
import com.somboi.gdx.modes.GameModes;
import com.somboi.gdx.saves.PlayerSaves;
import com.somboi.gdx.utils.RoundMap;

public class MenuCreator {
    private final RodaImpian rodaImpian;
    private final Skin skin;
    private final Table menuTable = new Table();
    private PlayerImage playerImage;
    private final PlayerSaves playerSaves = new PlayerSaves();
    private final Table playerTable = new Table();
    private final SequenceAction blink = new SequenceAction(Actions.fadeOut(0.2f), Actions.fadeIn(0.2f));
    private final Stage stage;
    private Label loadingLabel;
    private final FileHandle p1ImgPath = Gdx.files.local(StringRes.PLY1IMAGEPATH);
    private NameField inputName;

    public MenuCreator(RodaImpian rodaImpian, Stage stage) {
        this.rodaImpian = rodaImpian;
        this.stage = stage;
        this.skin = rodaImpian.getAssetManager().get(AssetDesc.SKIN);
        playerImage = new PlayerImage(rodaImpian.getAssetManager().get(AssetDesc.TEXTUREATLAS).findRegion("default_avatar"));

        LargeButton startBtn = new LargeButton(StringRes.START, skin);
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = rodaImpian.getPlayer();
                player.name = removeSymbols(player.name);
                savePlayer(player);
                rodaImpian.setGameModes(GameModes.SINGLE);
                rodaImpian.setPlayerImage(playerImage);
                rodaImpian.gotoMatch();
            }
        });

        if (rodaImpian.getPlayer().logged) {
            loadOnlinePic();
        }

        LargeButton leaderboard = new LargeButton(StringRes.LEADERBOARD, skin);
        LargeButton logoutFB = new LargeButton(StringRes.LOGOUT, skin);


        Table leftTable = new Table();

        inputName = new NameField(rodaImpian.getPlayer().name, skin);
        TextButton choosePhoto = new TextButton(StringRes.CHOOSEPHOTO, skin);
        choosePhoto.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.choosePhoto(0, MenuCreator.this);
            }
        });

        leftTable.add(inputName).padBottom(10f).row();
        leftTable.add(choosePhoto);
        playerTable.add(playerImage).left().padRight(40f);
        playerTable.add(leftTable);
        playerTable.pack();
        playerTable.setPosition(450f - playerTable.getWidth() / 2f, 900f);

        menuTable.padTop(50f);
        menuTable.defaults().pad(10f);
        menuTable.setFillParent(true);
        menuTable.center();
        menuTable.add(startBtn).row();
        menuTable.add(leaderboard).row();
    }

    public void show() {
        stage.addActor(menuTable);
        stage.addActor(playerTable);


    }

    public void savePlayer(Player player) {
        rodaImpian.setPlayer(player);
        playerSaves.save(player);
    }

    public void loadOnlinePic() {
        createLoading();
        Pixmap.downloadFromUrl(rodaImpian.getPlayer().picUri, new Pixmap.DownloadPixmapResponseListener() {
            @Override
            public void downloadComplete(Pixmap pixmap) {
                loadingLabel.setText(StringRes.COMPLETE);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        loadingLabel.remove();
                    }
                }, 2f);
                Texture t = new Texture(pixmap);
                Texture t2 = new Texture(RoundMap.execute(t));
                Sprite sprite = new Sprite(t2);
                playerImage.setDrawable(new SpriteDrawable(sprite));
            }

            @Override
            public void downloadFailed(Throwable t) {
                loadingLabel.setText(StringRes.FAILED);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        loadingLabel.remove();
                    }
                }, 2f);
            }
        });
    }

    public void createLoading() {
        loadingLabel = new Label(StringRes.LOADING, skin, "title");
        loadingLabel.setPosition(120f, 920f);
        loadingLabel.addAction(Actions.forever(blink));
        stage.addActor(loadingLabel);

    }

    public void loadLocalPic() {
        if (!rodaImpian.getPlayer().logged && p1ImgPath.exists()) {
            Texture texture = new Texture(RoundMap.execute(new Texture(p1ImgPath)));
            playerImage.setDrawable(new SpriteDrawable(new Sprite(texture)));
        }
    }

    public void loadFBData() {

        inputName.setText(rodaImpian.getPlayer().name);
    }

    private String removeSymbols(String name) {
        name = name.replaceAll("[^a-zA-Z0-9]", "");
        return name;
    }
}
