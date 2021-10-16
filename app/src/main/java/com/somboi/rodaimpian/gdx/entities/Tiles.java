package com.somboi.rodaimpian.gdx.entities;

import androidx.annotation.NonNull;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Tiles extends Image implements Cloneable {
    private final TextureAtlas textureAtlas;
    private char letter;
    private char completeLetter;
    private final Color normal = new Color(1f, 1f, 1f, 1f);
    private final SequenceAction introActions = new SequenceAction(
            Actions.color(Color.BLUE, 0.2f),
            Actions.color(normal, 0.4f)
    );
    private final SequenceAction revealActions = new SequenceAction(
            new ParallelAction(Actions.moveBy(30f, 0, 1f),
                    Actions.sizeTo(0, 84f, 1f)),
            new ParallelAction(Actions.sizeTo(60f, 84f, 1f),
                    Actions.moveBy(-30f, 0, 1f))
    );

    private boolean revealed;
    private final Array<String> textureName = new Array<>();

    //  private final Logger logger = new Logger(this.getClass().getName(), 3);
    public Tiles(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
        this.setSize(60f, 84f);
        setImage(textureAtlas.findRegion("blank"));
        this.setColor(Color.BLUE);
        for (TextureAtlas.AtlasRegion region : textureAtlas.getRegions()) {
            textureName.add(region.name);
            //  logger.debug(region.name);
        }
    }


    private void setImage(TextureRegion region) {
        this.setDrawable(new SpriteDrawable(new Sprite(region)));
    }

    public void typeLetter(String letter) {
        setCompleteLetter(letter.toUpperCase().charAt(0));
        for (String s : textureName) {
            //   logger.debug("texture name "+s);
            if (s.equals(letter)) {
                setImage(textureAtlas.findRegion(s));
                break;
            }
        }
    }

    public void deleteLetter() {
        setImage(textureAtlas.findRegion("blank"));
        setCompleteLetter('*');
    }

    public void reveal(char c) {
        this.addAction(revealActions);
        revealed = true;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setImage(textureAtlas.findRegion(String.valueOf(c).toLowerCase()));
            }
        }, 1f);
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
        if (letter == '\'') {
            setImage(textureAtlas.findRegion("oppost"));
            revealed = true;
        }
        if (letter == '-') {
            setImage(textureAtlas.findRegion("minus"));
            revealed = true;
        }
        if (letter == ' ') {
            setImage(textureAtlas.findRegion("space"));
            revealed = true;
        }
    }

    public void introAnimation() {
        this.addAction(Actions.repeat(3, introActions));
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setCompleteLetter(char completeLetter) {
        this.completeLetter = completeLetter;
    }

    public boolean checkLetter() {
        return (completeLetter == letter);
    }

    public char getCompleteLetter() {
        return completeLetter;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
