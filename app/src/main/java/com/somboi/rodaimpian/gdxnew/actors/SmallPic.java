package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.gdxnew.utils.RoundMap;

public class SmallPic extends Image {
    public SmallPic(TextureRegion region, String picUri) {
        super(region);
        setSize(150f, 150f);
        if (picUri!=null){
            Pixmap.downloadFromUrl(picUri, new Pixmap.DownloadPixmapResponseListener() {
                @Override
                public void downloadComplete(Pixmap pixmap) {
                    Texture round = new Texture(RoundMap.execute(new Texture(pixmap)));
                    setDrawable(new SpriteDrawable(new Sprite(round)));
                }

                @Override
                public void downloadFailed(Throwable t) {

                }
            });
        }
    }
}
