package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
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
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.actor.FBPrompt;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.actor.NameField;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.screen.LoadingScreen;
import com.somboi.rodaimpian.gdx.screen.MenuScreen;
import com.somboi.rodaimpian.gdx.screen.OnlineMatchScreen;
import com.somboi.rodaimpian.gdx.utils.RoundMap;
import com.somboi.rodaimpian.saves.PlayerSaves;

import java.util.ArrayList;

public class MainMenuCreator {
    private final RodaImpian rodaImpian;
    private final Skin skin;
    private final Table menuTable = new Table();
    private PlayerImage playerImage;
    private final PlayerSaves playerSaves = new PlayerSaves();
    private final Table playerTable = new Table();
    private final SequenceAction blink = new SequenceAction(Actions.fadeOut(0.2f), Actions.fadeIn(0.2f));
    private final Group menuGroup;
    private Label loadingLabel;
    private final FileHandle p1ImgPath = Gdx.files.local(StringRes.PLY1IMAGEPATH);
    private NameField inputName;
    private final Stage stage;
    private final MenuScreen menuScreen;
    private boolean promptFb;

    public MainMenuCreator(RodaImpian rodaImpian, Group menuGroup, Stage stage, MenuScreen menuScreen) {
        this.rodaImpian = rodaImpian;
        this.menuGroup = menuGroup;
        this.stage = stage;
        this.menuScreen = menuScreen;
        this.skin = rodaImpian.getAssetManager().get(AssetDesc.SKIN);
        playerImage = new PlayerImage(rodaImpian.getPlayer().picUri,rodaImpian.getAssetManager().get(AssetDesc.TEXTUREATLAS).findRegion("default_avatar"));

        loadingLabel = new Label(StringRes.LOADING, skin, "title");
        loadingLabel.setPosition(120f, 920f);
        loadingLabel.addAction(Actions.forever(blink));
        LargeButton offline = new LargeButton(StringRes.Offline, skin);
        offline.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Player player = rodaImpian.getPlayer();
                player.name = removeSymbols(player.name);
                savePlayer(player);
                player.fullScore = 0;
                player.currentScore = 0;
                player.freeTurn = false;
                player.gifts.clear();
                player.bonusIndex = 0;
                rodaImpian.setGameModes(GameModes.SINGLE);
                rodaImpian.setPlayerImage(playerImage);
                menuScreen.showLocal();
                changeName();
            }
        });



        LargeButton leaderboard = new LargeButton(StringRes.LEADERBOARD, skin);
        leaderboard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.gotoLeaderBoard();
            }
        });
        LargeButton onlineBtn = new LargeButton(StringRes.ONLINE, skin);
        onlineBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (rodaImpian.getPlayer().logged) {
                    //rodaImpian.setScreen(new RoomScreen(rodaImpian));
                    changeName();
                    rodaImpian.setScreen(new OnlineMatchScreen(rodaImpian));
                } else {
                    FBPrompt fbPrompt = new FBPrompt(skin){
                        @Override
                        protected void result(Object object) {
                            if (object.equals(true)){
                                rodaImpian.loginFB();
                            }
                        }
                    };
                    fbPrompt.show(stage);
                }
            }
        });
        LargeButton logoutFB = new LargeButton(StringRes.LOGOUT, skin);
        logoutFB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                FileHandle fileHandle = Gdx.files.local("playersave");
                if (fileHandle.exists()){
                    fileHandle.delete();
                }

                    rodaImpian.logoutFB(rodaImpian.getPlayer().id);

            }
        });
        logoutFB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.getPlayer().logged = false;
                rodaImpian.getPlayer().picUri = null;
                savePlayer(rodaImpian.getPlayer());
                rodaImpian.setScreen(new LoadingScreen(rodaImpian));
            }
        });
        LargeButton comment = new LargeButton(StringRes.MESSAGE, skin);
        comment.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.openComment();
            }
        });


        Table leftTable = new Table();

        inputName = new NameField(rodaImpian.getPlayer().name, skin);
        TextButton choosePhoto = new TextButton(StringRes.CHOOSEPHOTO, skin);
        choosePhoto.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                rodaImpian.choosePhoto(0);
            }
        });

        leftTable.add(inputName).padBottom(10f).row();
        leftTable.add(choosePhoto);
        playerTable.add(playerImage).left().padRight(40f);
        playerTable.add(leftTable);
        playerTable.pack();
        playerTable.setPosition(450f - playerTable.getWidth() / 2f, 900f);

        menuTable.defaults().pad(10f);
        //menuTable.setFillParent(true);
        //menuTable.center();
        menuTable.add(offline).row();
        menuTable.add(onlineBtn).row();        menuTable.add(comment).row();

        menuTable.add(leaderboard).row();
        if (rodaImpian.getPlayer().logged){
            menuTable.add(logoutFB);

        }
        menuTable.pack();
        menuTable.setPosition(450f-menuTable.getWidth()/2f, playerTable.getY()-menuTable.getHeight()-20f);

        rodaImpian.getPlayer().fullScore = 0;
        rodaImpian.getPlayer().turn = false;
        rodaImpian.getPlayer().gifts = new ArrayList<>();
        rodaImpian.getPlayer().bankrupt = 0;
        rodaImpian.getPlayer().currentScore = 0;


    }

    private void changeName(){
        if (inputName.getText().length()>0){
            String name = removeSymbols(inputName.getText());
            rodaImpian.getPlayer().name = name;
        }
    }

    public void show() {

        menuGroup.addActor(menuTable);
        menuGroup.addActor(playerTable);
        FBPrompt fbPrompt = new FBPrompt(skin) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    rodaImpian.loginFB();
                }
            }
        };
        if (!rodaImpian.getPlayer().logged && rodaImpian.getPlayerOnline().timesplayed==0 && !promptFb) {
            fbPrompt.show(stage);
            promptFb = true;
        }

        if (rodaImpian.getPlayerOnline().bestScore>0){
            Table table = new Table();
            Label score = new Label(StringRes.BESTSCORE,skin, "whitearial");
            score.setColor(Color.GOLDENROD);
            Label bestscore = new Label("$"+rodaImpian.getPlayerOnline().bestScore,skin,"whitearial");
            bestscore.setColor(Color.GREEN);
            table.add(score).row();
            table.add(bestscore);
            table.pack();
            table.setPosition(450f-table.getWidth()/2f, menuTable.getY()-table.getHeight());
            menuGroup.addActor(table);
        }
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

        stage.addActor(loadingLabel);
    }

    public void loadLocalPic() {
        if (!rodaImpian.getPlayer().logged && p1ImgPath.exists()) {
            Texture texture = new Texture(RoundMap.execute(new Texture(p1ImgPath)));
            playerImage.setDrawable(new SpriteDrawable(new Sprite(texture)));
        }
    }

    public void savePlayerOnline(PlayerOnline playerOnline) {
        playerSaves.savePlayerOnline(playerOnline);
    }

    private String removeSymbols(String name) {
        name = name.replaceAll("[^a-zA-Z0-9]", "");
        return name;
    }
}
