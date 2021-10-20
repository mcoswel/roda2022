package com.somboi.rodaimpian.gdx.actor;

import androidx.annotation.NonNull;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.gdx.utils.RoundMap;

public class PlayerImage extends Image implements Cloneable {
    private Sprite animate = null;
    private TextureRegion thisRegion;

    public PlayerImage(String picUri, TextureRegion defaultAvatar) {
        super(defaultAvatar);
        setUp();
        Pixmap.downloadFromUrl(picUri, new Pixmap.DownloadPixmapResponseListener() {
            @Override
            public void downloadComplete(Pixmap pixmap) {

                Texture tx = new Texture(RoundMap.execute(new Texture(pixmap)));
                PlayerImage.this.setDrawable(new SpriteDrawable(new Sprite(tx)));
                PlayerImage.this.setSize(250f, 250f);
            }

            @Override
            public void downloadFailed(Throwable t) {

            }
        });
    }

    public void animate() {
        this.setDrawable(new SpriteDrawable(animate));
    }

    public PlayerImage(TextureRegion region) {
        super(region);
        thisRegion = region;
        setUp();
    }

    public PlayerImage(Texture texture) {
        super(texture);
        setUp();
    }

    private void setUp() {
        this.setSize(250f, 250f);
    }

    public void setAnimate(TextureRegion region) {
        animate = new Sprite(region);
    }


    public TextureRegion getThisRegion() {
        return thisRegion;
    }

    public Sprite getAnimate() {
        return animate;
    }

    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
