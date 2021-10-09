package com.somboi.gdx.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDesc {
    public static final AssetDescriptor<TextureAtlas> TEXTUREATLAS = new AssetDescriptor<TextureAtlas>(AssetPath.TEXTUREATLAS, TextureAtlas.class);
    public static final AssetDescriptor<Texture> CONFETTI = new AssetDescriptor<Texture>(AssetPath.CONFETTI, Texture.class);
    public static final AssetDescriptor<Texture> WINANIMATION = new AssetDescriptor<Texture>(AssetPath.WINANIMATION, Texture.class);
    public static final AssetDescriptor<Texture> SPARKLE = new AssetDescriptor<Texture>(AssetPath.SPARKLE, Texture.class);
    public static final AssetDescriptor<Texture> BLURBG = new AssetDescriptor<Texture>(AssetPath.BLURBG, Texture.class);
    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>(AssetPath.SKIN, Skin.class);
    public static final AssetDescriptor<Sound> CHEERSOUND = new AssetDescriptor<Sound>(AssetPath.CHEERSOUND, Sound.class);
    public static final AssetDescriptor<Sound> AWWSOUND = new AssetDescriptor<Sound>(AssetPath.AWWSOUND, Sound.class);
    public static final AssetDescriptor<Sound> ROTATESOUND = new AssetDescriptor<Sound>(AssetPath.ROTATESOUND, Sound.class);
    public static final AssetDescriptor<Sound> CORRECTSOUND = new AssetDescriptor<Sound>(AssetPath.CORRECTSOUND, Sound.class);
    public static final AssetDescriptor<Sound> WRONGSOUND = new AssetDescriptor<Sound>(AssetPath.WRONGSOUND, Sound.class);
    public static final AssetDescriptor<Sound> CLOCKSOUND = new AssetDescriptor<Sound>(AssetPath.CLOCKSOUND, Sound.class);
    public static final AssetDescriptor<Sound> WINSOUND = new AssetDescriptor<Sound>(AssetPath.WINSOUND, Sound.class);
    public static final AssetDescriptor<Sound> SLAPSOUND = new AssetDescriptor<Sound>(AssetPath.SLAPSOUND, Sound.class);
    public static final AssetDescriptor<Texture> BG = new AssetDescriptor<Texture>(AssetPath.BG, Texture.class);
    public static final AssetDescriptor<Texture> HOURGLASS = new AssetDescriptor<Texture>(AssetPath.HOURGLASS, Texture.class);

}

