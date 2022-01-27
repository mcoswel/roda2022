package com.somboi.rodaimpian.gdx.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.somboi.rodaimpian.gdx.actor.BlinkActions;
import com.somboi.rodaimpian.gdx.actor.BoardLabel;
import com.somboi.rodaimpian.gdx.actor.PlayerImage;
import com.somboi.rodaimpian.gdx.actor.ScoreLabel;
import com.somboi.rodaimpian.gdxnew.assets.StringRes;

public class PlayerGui {
    private final Player player;
    private  PlayerImage image;
    private int positionIndex;
    private ScoreLabel scoreLabel;
    private BoardLabel nameLabel;
    private Label freeTurnLabel;
    private BoardLabel fullScoreLabel;
    private Vector2 playerPos;
    private float xPosition, yPosition;

    public PlayerGui(Player player, PlayerImage image) {
        this.player = player;
        this.image = image;
        image.setSize(250f, 250f);
    }

    public void setFirstTurn(int index) {
        positionIndex = index;
        image.setPosition(450f - 250f - 50f, 800f);
        if (positionIndex == 2) {
            image.setPosition(450f + 50f, 800f);
        } else if (positionIndex == 0) {
            image.setPosition(450f - (250f / 2), 800f - 100f - 250f);
        }
        image.setOrigin(450f - image.getX(), 800f - image.getY());

    }

    public void setPlayerBoard(Skin skin, Group playerBoardGroup) {
        String name = player.name;
        if (name.length() > 9) {
            name = name.substring(0, 9);
        }
        Color bluHi = new Color(114f / 255f, 184f / 255f, 249f / 255f, 1f);
        nameLabel = new BoardLabel(name, skin);
        nameLabel.pack();
        scoreLabel = new ScoreLabel("$" + player.boardScore, skin, player);
        scoreLabel.pack();
        fullScoreLabel = new BoardLabel("$" + player.fullScore, skin);
        fullScoreLabel.setColor(Color.GREEN);
        fullScoreLabel.pack();

        xPosition = 150f;
        nameLabel.setColor(bluHi);
        if (positionIndex == 1) {
            xPosition = 150f + 300f;
        } else if (positionIndex == 2) {
            xPosition = 150f + 600f;
        }
        yPosition = 267f;
        nameLabel.setPosition(xPosition - nameLabel.getWidth() / 2f, yPosition);
        scoreLabel.setPosition(xPosition - scoreLabel.getWidth() / 2f, yPosition - scoreLabel.getHeight() - 25f);
        fullScoreLabel.setPosition(xPosition - fullScoreLabel.getWidth() / 2f, 40f);


        playerBoardGroup.addActor(nameLabel);
        playerBoardGroup.addActor(scoreLabel);
        playerBoardGroup.addActor(fullScoreLabel);

    }

    public void showFreeTurn(Stage stage, Skin skin) {
        freeTurnLabel = new Label(StringRes.FREETURN, skin, "free");
        freeTurnLabel.setAlignment(Align.center);
        freeTurnLabel.pack();
        freeTurnLabel.setPosition(xPosition - freeTurnLabel.getWidth() / 2f, yPosition - freeTurnLabel.getHeight() * 2 - 25f);
        freeTurnLabel.addAction(BlinkActions.blinkAction());
        stage.addActor(freeTurnLabel);
    }

    public void removeFreeTurn() {
        if (freeTurnLabel != null) {
            freeTurnLabel.addAction(Actions.moveBy(0, 400f, 3f));
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    freeTurnLabel.remove();
                }
            }, 2f);
        }
    }

    public void animate() {

        playerPos = new Vector2(25f, 376f);
        if (positionIndex == 1) {
            playerPos = new Vector2(325f, 376f);
        } else if (positionIndex == 2) {
            playerPos = new Vector2(625f, 376f);
        }
        image.addAction(new ParallelAction(Actions.rotateTo(0, 2f), Actions.moveTo(playerPos.x, playerPos.y, 2f)));
    }

    public void setOnlinePosition(int i){
        playerPos = new Vector2(25f, 376f);
        positionIndex = i;
        if (positionIndex == 1) {
            playerPos = new Vector2(325f, 376f);
        } else if (positionIndex == 2) {
            playerPos = new Vector2(625f, 376f);
        }
        image.setPosition(playerPos.x, playerPos.y);
    }

    public PlayerImage getImage() {
        return image;
    }

    public Player getPlayer() {
        return player;
    }

    public void updateFullScore() {
        fullScoreLabel.setText("$" + player.fullScore);
        fullScoreLabel.pack();
        fullScoreLabel.setPosition(xPosition - fullScoreLabel.getWidth() / 2f, 40f);
    }

    public Vector2 getPlayerPos() {
        return playerPos;
    }



}
