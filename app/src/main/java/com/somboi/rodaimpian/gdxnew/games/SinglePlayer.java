package com.somboi.rodaimpian.gdxnew.games;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.actors.LoveRajan;
import com.somboi.rodaimpian.gdxnew.actors.PlayerGuis;
import com.somboi.rodaimpian.gdxnew.screens.BombeiroScreen;

public class SinglePlayer extends BaseGame {
    private boolean rajanBool;
    private float rajanTimer = 40f;
    private Vector2 humanPos;
    private Vector2 rajanPos;
    private LoveRajan loveRajan;
    private boolean checkRajan;

    public SinglePlayer(Stage stage, RodaImpianNew rodaImpianNew) {
        super(stage, rodaImpianNew);
        addPlayers();
        start();
    }

    @Override
    public void startTurn() {
        //rodaImpianNew.setScreen(new BombeiroScreen(rodaImpianNew, 1,1,testGui));
        super.startTurn();
        if (!checkRajan) {
            checkRajan();
        }
    }

    private void checkRajan() {
        for (PlayerGuis p : playerGuis) {
            if (!p.getPlayerNew().isAi()) {
                humanPos = p.getPosition();
            }
        }
        flipCpu();
        checkRajan = true;
    }

    private void flipCpu() {

        for (PlayerGuis p : playerGuis) {
            if (p.getPlayerNew().isAi()) {
                if (p.getPlayerNew().getName().equals("Rajan")) {
                    rajanPos = p.getPosition();
                    rajanBool = true;
                    loveRajan = new LoveRajan(atlas.findRegion("loverajan"), rajanPos, humanPos.x);
                }
                if (p.getPosition().x < humanPos.x) {
                    TextureRegion region = p.getProfilePic().getRegion();
                    region.flip(true, false);
                    TextureRegion regionAnimate = atlas.findRegion(p.getPlayerNew().getName().toLowerCase() + "two");
                    regionAnimate.flip(true, false);
                    p.getProfilePic().setDrawable(new SpriteDrawable(new Sprite(region)));
                    p.getProfilePic().addListener(new DragListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            if (rajanBool) {
                                rajanBool = false;
                                gameSound.playSlapSound();
                            }
                            p.getProfilePic().setDrawable(new SpriteDrawable(new Sprite(regionAnimate)));
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    p.getProfilePic().setDrawable(new SpriteDrawable(new Sprite(region)));
                                }
                            }, 60f);
                            return super.touchDown(event, x, y, pointer, button);
                        }
                    });
                } else {
                    p.getProfilePic().addListener(new DragListener() {
                        @Override
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            if (rajanBool) {
                                rajanBool = false;
                                gameSound.playSlapSound();
                            }
                            p.getProfilePic().setDrawable(new SpriteDrawable(
                                    atlas.createSprite(p.getPlayerNew().getName().toLowerCase() + "two")
                            ));
                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    p.getProfilePic().setDrawable(new SpriteDrawable(
                                            atlas.createSprite(p.getPlayerNew().getName().toLowerCase())
                                    ));
                                }
                            }, 60f);
                            return super.touchDown(event, x, y, pointer, button);
                        }
                    });
                }
            }

        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (rajanBool) {
            if (rajanTimer >= 0) {
                rajanTimer -= delta;
                if (rajanTimer <= 0 && rajanPos != null && humanPos != null && loveRajan != null) {
                    loveRajan.animate(stage);
                    rajanTimer = 45f;
                }
            }
        }
    }
}
