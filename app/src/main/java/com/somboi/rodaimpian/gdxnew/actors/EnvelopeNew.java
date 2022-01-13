package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;

public class EnvelopeNew {
    private final TextureAtlas atlas;
    private final Vector2 position;
    private final Vector2 size = new Vector2(346f, 353f);
    private final ClosedEnvelope closedEnvelope;
    private final OpenEnvelope openEnvelope;
    private final BottomEnvelop bottomEnvelop;
    private final PaperEnvelope paperEnvelope;
    private final int index;
    private final Array<Color> colors = new Array<>(new Color[]{ Color.BLUE, Color.BROWN, Color.FIREBRICK, Color.GOLDENROD,
            Color.GRAY, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.PINK, Color.PURPLE, Color.RED, Color.ORANGE
    });

    public EnvelopeNew(TextureAtlas atlas, int index, Vector2 position) {
        this.atlas = atlas;
        this.index = index;
        this.position = position;
        colors.shuffle();
        closedEnvelope = new ClosedEnvelope();
        bottomEnvelop = new BottomEnvelop();
        paperEnvelope = new PaperEnvelope();
        openEnvelope = new OpenEnvelope();
    }

    public void prepareEnvelope(Stage stage, DragListener dragListener) {
        closedEnvelope.addListener(dragListener);
        stage.addActor(closedEnvelope);
    }

    public void opened(Stage stage) {
        closedEnvelope.remove();
        stage.addActor(bottomEnvelop);
        stage.addActor(paperEnvelope);
        stage.addActor(openEnvelope);
    }

    public void clearAll() {
        bottomEnvelop.remove();
        closedEnvelope.remove();
        paperEnvelope.remove();
        openEnvelope.remove();
    }


    private class ClosedEnvelope extends Image {
        public ClosedEnvelope() {
            super(atlas.findRegion("closedenvelope"));
            setPosition(position.x, position.y);
            setSize(size.x, size.y);
            setColor(colors.get(index));
        }
    }

    private class OpenEnvelope extends Image {
        public OpenEnvelope() {
            super(atlas.findRegion("openenveloped"));
            setPosition(position.x, position.y);
            setSize(size.x, size.y);
            setColor(colors.get(index));
        }
    }

    private class BottomEnvelop extends Image {
        public BottomEnvelop() {
            super(atlas.findRegion("bottomopenenveloped"));
            setPosition(position.x, position.y);
            setSize(size.x, size.y);
            setColor(colors.get(index));
        }
    }

    private class PaperEnvelope extends Image {
        public PaperEnvelope() {
            super(atlas.findRegion("midone"));
            if (index == 0) {
                setDrawable(new SpriteDrawable(atlas.createSprite("midzero")));
            }
            setPosition(position.x, position.y);
            setSize(size.x, size.y);
        }
    }

}
