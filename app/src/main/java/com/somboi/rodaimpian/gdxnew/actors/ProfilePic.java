package com.somboi.rodaimpian.gdxnew.actors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.gdx.utils.RoundMap;

public class ProfilePic extends Image {
    public ProfilePic(TextureRegion region,String picUri) {
        super(region);
        setSize(250,250);
        if (picUri!=null){
            Pixmap.downloadFromUrl(picUri, new Pixmap.DownloadPixmapResponseListener() {
                @Override
                public void downloadComplete(Pixmap pixmap) {
                    Texture round = new Texture(RoundMap.execute(new Texture(pixmap)));
                    ProfilePic.this.setDrawable(new SpriteDrawable(new Sprite(round)));
                }
                @Override
                public void downloadFailed(Throwable t) {

                }
            });
        }
    }
}
