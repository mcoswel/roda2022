package com.somboi.rodaimpian.gdx.modes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.gdx.actor.LoveRajan;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.base.ModeBase;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.entities.PlayerGui;
import com.somboi.rodaimpian.gdx.entities.RandomCpu;

public class MultiPlayLocal extends ModeBase {
    private RandomCpu randomCpu;
    private Player playerTwo;
    private Player playerThree;
    private PlayerImage playerTwoImage;
    private PlayerImage playerThreeImage;
    private boolean flipCpu;
    private float rajanTimer = 15f;
    private LoveRajan loveRajan = null;
    public MultiPlayLocal(RodaImpian rodaImpian, Stage stage) {
        super(rodaImpian, stage);


    }

    @Override
    public void setPlayers() {
        playerTwo = rodaImpian.getPlayerTwo();
        playerTwoImage = rodaImpian.getPlayerTwoImage();
        playerThree = rodaImpian.getPlayerThree();
        playerThreeImage = rodaImpian.getPlayerThreeImage();

        randomCpu = new RandomCpu(textureAtlas, gameSound);
        PlayerGui playerGuiCpu1 = new PlayerGui(playerTwo, playerTwoImage);
        PlayerGui playerGuiCpu2;
        if (playerThree!=null){
            playerGuiCpu2 = new PlayerGui(playerThree, playerThreeImage);
        }else{
            playerGuiCpu2 = new PlayerGui(randomCpu.getPlayer(1), randomCpu.getImage(1));
        }
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
