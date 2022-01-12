package com.somboi.rodaimpian.gdxnew.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdx.actor.BonusGiftImg;
import com.somboi.rodaimpian.gdx.actor.LargeButton;
import com.somboi.rodaimpian.gdx.assets.AssetDesc;
import com.somboi.rodaimpian.gdx.assets.GameSound;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.config.GameConfig;
import com.somboi.rodaimpian.gdxnew.actors.BeiroTiles;
import com.somboi.rodaimpian.gdxnew.actors.Bonuses;
import com.somboi.rodaimpian.gdxnew.actors.Bulb;
import com.somboi.rodaimpian.gdxnew.actors.Fireworks;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.actors.Vehicle;
import com.somboi.rodaimpian.gdxnew.actors.WinnerDialog;

public class BombeiroScreen extends BaseScreenNew {
    private final Vehicle police;
    private final Vehicle ambulance;
    private final Vehicle firetruck;
    private boolean finish;
    private String vehiclesChosen;
    private Table numbersTable = new Table();
    private Bonuses bonusGiftImg;
    private boolean moving;
    private boolean winBonus;
    private final int bonusIndex;
    private final int bonusValue;
    private int adsCount;
    private final GameSound gameSound;
    private final PlayerGuis playerGuis;

    public BombeiroScreen(RodaImpianNew rodaImpian, int bonusIndex, int bonusValue, PlayerGuis playerGuis) {
        super(rodaImpian);
        this.bonusIndex = bonusIndex;
        this.bonusValue = bonusValue;
        this.gameSound = new GameSound(assetManager);
        this.playerGuis = playerGuis;
        Gdx.input.setInputProcessor(stage);

        createBackground();
        police = new Vehicle(atlas.findRegion("police"), "police");
        ambulance = new Vehicle(atlas.findRegion("ambulance"), "ambulance");
        firetruck = new Vehicle(atlas.findRegion("firetruck"), "firetruck");
        createButton();
        bonusGiftImg = new Bonuses(atlas, bonusIndex);
        stage.addActor(bonusGiftImg.getBonusImage());
        bonusGiftImg.setPosition(450f - bonusGiftImg.getWidth() / 2f, 1326f);
        logger.debug("bombeiro screen");

        // rodaImpian.loadAds();
    }

    private void createNumbersTable() {
        String bulbString = "_bulb";
        Array<String> colors = new Array<>();

        for (int i = 0; i < 6; i++) {
            colors.add("white");
            colors.add("black");
            colors.add("red");
            colors.shuffle();
        }

        int index = 0;
        for (String type : colors) {
            final String finalType = type;
            Bulb bulb = new Bulb(atlas.findRegion((index + 1) + bulbString), type);
            bulb.addListener(new DragListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (!moving) {
                        bulb.setDrawable(new SpriteDrawable(new Sprite(atlas.findRegion(finalType + "_move"))));
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                moveVehicles(finalType);
                            }
                        }, 1f);
                        moving = true;
                    }
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            numbersTable.add(bulb);
            index++;
            if (index % 6 == 0) {
                numbersTable.row();
            }
        }
        numbersTable.setFillParent(true);
        numbersTable.center();
        numbersTable.pack();
        stage.addActor(numbersTable);
    }

    private void moveVehicles(String type) {
        adsCount++;
        if (adsCount == 8) {
            // rodaImpian.showAds(2);
        }
        if (type.equals("red")) {
            firetruck.move();
            gameSound.playFire();
            //  rodaImpian.showAds(0);
        } else if (type.equals("white")) {
            ambulance.move();
            gameSound.playAmbulance();
            // rodaImpian.showAds(0);
        } else if (type.equals("black")) {
            police.move();
            gameSound.playPolice();
            //   rodaImpian.showAds(0);
        }
        logger.debug("firecount " + firetruck.getCount());
        logger.debug("ambulance " + ambulance.getCount());
        logger.debug("police " + police.getCount());
        numbersTable.remove();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (firetruck.getCount() == 6) {
                    checkWinner(firetruck.getType());
                } else if (ambulance.getCount() == 6) {
                    checkWinner(ambulance.getType());
                } else if (police.getCount() == 6) {
                    checkWinner(police.getType());
                } else {
                    stage.addActor(numbersTable);
                    moving = false;
                }

            }
        }, 2.5f);
    }

    private void createButton() {
        Table table = new Table();
        LargeButton chooseFire = new LargeButton(StringRes.FIRETRUCK, skin);
        chooseFire.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vehiclesChosen = "firetruck";
                createVehicle();
                table.remove();
            }
        });
        LargeButton chooseAmbulance = new LargeButton(StringRes.AMBULANCE, skin);
        chooseAmbulance.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vehiclesChosen = "ambulance";
                createVehicle();
                table.remove();
            }
        });
        LargeButton choosePolice = new LargeButton(StringRes.POLICE, skin);
        choosePolice.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                vehiclesChosen = "police";
                createVehicle();
                table.remove();
            }
        });
        table.center();
        table.setFillParent(true);
        table.defaults().pad(20f);
        table.add(chooseFire).row();
        table.add(chooseAmbulance).row();
        table.add(choosePolice).row();
        stage.addActor(table);
    }

    public void createVehicle() {
        stage.addActor(police);
        stage.addActor(ambulance);
        stage.addActor(firetruck);
        createNumbersTable();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }



    private void checkWinner(String type) {
        if (vehiclesChosen.equals(type)) {
            winBonus = true;
            stage.addActor(new Fireworks(assetManager.get(AssetDesc.WINANIMATION)));
            gameSound.playCheer();
        } else {
            gameSound.playAww();
        }
        if (type.equals("ambulance")) {
            logger.debug("ambulance wins");
        } else if (type.equals("police")) {
            logger.debug("police wins");
        } else if (type.equals("firetruck")) {
            logger.debug("firetruck wins");
        }

        endGame();
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                WinnerDialog endGameDialog = new WinnerDialog(skin, playerGuis, atlas, rodaImpianNew);
                endGameDialog.show(stage);
            }
        }, 3f);
    }

    private void endGame() {
      /*  if (rodaImpian.getPlayer().id != null) {
            Player finalPlayer = rodaImpian.getPlayer();
            PlayerOnline playerOnline = rodaImpian.getPlayerOnline();
            playerOnline.name = finalPlayer.name;
            playerOnline.picUri = finalPlayer.picUri;
            playerOnline.timesplayed += 1;
            playerOnline.id = finalPlayer.id;
            playerOnline.bankrupt += finalPlayer.bankrupt;
            PlayerSaves playerSaves = new PlayerSaves();
            if (rodaImpian.getPlayer().logged) {
                if (winBonus) {
                    playerOnline.bonusList.add(bonusIndex);
                    rodaImpian.getPlayer().fullScore+=bonusValue;
                }
                if (finalPlayer.fullScore > playerOnline.bestScore) {
                    playerOnline.bestScore = finalPlayer.fullScore;
                }
                if (finalPlayer.gifts.size() > 0) {
                    for (Integer integer : finalPlayer.gifts) {
                        if (integer != 0 && !playerOnline.giftsList.contains(integer)) {
                            playerOnline.giftsList.add(integer);
                        }
                    }
                }
                playerOnline.logged = true;
                if (playerOnline.bestScore!=0) {
                    rodaImpian.uploadScore(playerOnline);
                }
            }
            playerSaves.savePlayerOnline(playerOnline);
            playerSaves.save(finalPlayer);

        }*/
    }

    private void createBackground() {
        if (rodaImpianNew.isGoldTheme()){
            stage.addActor(new Image(assetManager.get(AssetDesc.BLURGOLD)));
        }else{
            stage.addActor(new Image(assetManager.get(AssetDesc.BLURRED)));
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String color = "_red";
                if (j == 1) {
                    color = "_white";
                } else if (j == 2) {
                    color = "_black";
                }
                BeiroTiles beiroTiles = new BeiroTiles(atlas.findRegion((i + 1) + color));
                beiroTiles.setPosition(124f + (beiroTiles.getWidth() * j), 237f + (beiroTiles.getHeight() * i));
                stage.addActor(beiroTiles);
            }
        }
        Image flag = new Image(atlas.findRegion("flag"));
        flag.setSize(653f, 218f);
        flag.setPosition(124f, 1325f);
        stage.addActor(flag);
    }
}
