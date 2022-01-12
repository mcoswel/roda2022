package com.somboi.rodaimpian.gdx.modes;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdxnew.actors.LoveRajan;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.entities.RandomCpu;

public class SinglePlayer extends ModeBase {
    private boolean flipCpu;
    private float rajanTimer = 15f;
    private LoveRajan loveRajan = null;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    private  RandomCpu randomCpu;
    public SinglePlayer(RodaImpian rodaImpian, Stage stage) {
        super(rodaImpian, stage);

    }

    @Override
    public void setPlayers() {
        randomCpu = new RandomCpu(textureAtlas, gameSound);

        PlayerGui playerGuiCpu1 = new PlayerGui(randomCpu.getPlayer(4), randomCpu.getImage(4));
        PlayerGui playerGuiCpu2 = new PlayerGui(randomCpu.getPlayer(1), randomCpu.getImage(1));
        PlayerGui playerGuiHuman = new PlayerGui(thisPlayer, rodaImpian.getPlayerImage());
        playerGuis.add(playerGuiCpu1);
        playerGuis.add(playerGuiCpu2);
        playerGuis.add(playerGuiHuman);

    }


    @Override
    public void startPlays() {
        super.startPlays();
        if (!flipCpu) {
            flipCpu = true;
            flipCpu();
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (activePlayer.isAi) {
                    aiRun();
                } else {
                    if (activePlayer.turn) {
                        showMenu();
                    }
                }
            }
        }, 2f);

    }

    private void flipCpu() {
        Vector2 humanPos = new Vector2();
        for (PlayerGui p : playerGuis) {
            if (!p.getPlayer().isAi) {
                humanPos = p.getPlayerPos();
            }
        }
        for (PlayerGui p : playerGuis) {
            if (p.getPlayer().isAi) {
                if (p.getPlayerPos().x < humanPos.x) {
                    TextureRegion region = p.getImage().getThisRegion();
                    region.flip(true, false);
                    TextureRegion regionAnimate = p.getImage().getAnimate();
                    regionAnimate.flip(true, false);
                    p.getImage().setAnimate(regionAnimate);
                    p.getImage().setDrawable(new SpriteDrawable(new Sprite(region)));
                }

                if (p.getPlayer().name.equals("Rajan")) {
                    loveRajan = new LoveRajan(textureAtlas.findRegion("love"), p.getPlayerPos(), humanPos.x);
                    logger.debug("Human pos x " + humanPos.x + " rajan pos " + p.getPlayerPos().x);
                }
            }

        }
    }




    @Override
    public void update(float delta) {
        super.update(delta);
        if (loveRajan != null && rajanTimer > 0 && !randomCpu.isRajanSlap() && !bonusRound && !gameEnds) {
            rajanTimer -= delta;
            if (rajanTimer <= 0) {
                rajanTimer = 15f;
                loveRajan.animate(stage);
            }
        }

    }

}