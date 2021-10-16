package com.somboi.rodaimpian.gdx.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class RoundMap {


    public static Pixmap execute(Texture tx) {
        if (!tx.getTextureData().isPrepared()) {
            tx.getTextureData().prepare();
        }
        Pixmap pixmap = tx.getTextureData().consumePixmap();
        int width = pixmap.getWidth();
        int height = pixmap.getHeight();
        Pixmap round = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        double radius = width / 2.0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //check if pixel is outside circle. Set pixel to transparant;
                double dist_x = (radius - x);
                double dist_y = radius - y;
                double dist = Math.sqrt((dist_x * dist_x) + (dist_y * dist_y));
                if (dist < radius) {
                    round.drawPixel(x, y, pixmap.getPixel(x, y));
                } else
                    round.drawPixel(x, y, 0);
            }
        }

        return round;
    }
}