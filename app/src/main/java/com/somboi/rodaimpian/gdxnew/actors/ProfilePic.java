package com.somboi.rodaimpian.gdxnew.actors;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.somboi.rodaimpian.gdx.assets.StringRes;
import com.somboi.rodaimpian.gdx.utils.RoundMap;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;

public class ProfilePic extends Image implements Cloneable {
    private TextureRegion region;
    private boolean loadOfflinePic;
    private final PlayerNew playerNew;
    private FileHandle path = null;

    public ProfilePic(TextureRegion region, String picUri, PlayerNew playerNew, int playerNo) {
        super(region);
        this.region = region;
        this.playerNew = playerNew;
        if (playerNo == 1) {
            path = Gdx.files.local(StringRes.PLY1IMAGEPATH);
        } else if (playerNo == 2) {
            path = Gdx.files.local(StringRes.PLY2IMAGEPATH);
        } else if (playerNo == 3) {
            path = Gdx.files.local(StringRes.PLY3IMAGEPATH);
        }
        setSize(250, 250);
        if (picUri != null) {
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

    public TextureRegion getRegion() {
        return region;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!loadOfflinePic) {
            if (!playerNew.isAi() && !playerNew.isLogged()) {
                if (path != null) {
                    if (path.exists()) {
                        Texture round = new Texture(RoundMap.execute(new Texture(path)));
                        setDrawable(new SpriteDrawable(new Sprite(round)));
                    }
                }
            }
        }
    }

    public void stopLoadingPhoto() {
        loadOfflinePic = true;
        if (path != null) {
            if (path.exists()) {
                Texture round = new Texture(RoundMap.execute(new Texture(path)));
                setDrawable(new SpriteDrawable(new Sprite(round)));
            }
        }
    }

    public void reloadUrl(String picUri) {
        if (picUri != null) {
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

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
