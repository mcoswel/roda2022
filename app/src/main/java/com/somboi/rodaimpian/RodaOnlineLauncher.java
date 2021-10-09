package com.somboi.rodaimpian;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.utils.Logger;
import com.somboi.gdx.RodaOnline;

import barsoosayque.libgdxoboe.OboeAudio;

public class RodaOnlineLauncher extends AndroidApplication implements RodaOnlineInterface {

    @Override
    public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
        return new OboeAudio(context.getAssets());
    }

    private String roomID;
    private final Logger logger = new Logger(this.getClass().getName(), 3);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomID = extras.getString("roomID");
        }
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        View gameView = initializeForView(new RodaOnline(this), config);
    }

    @Override
    public String getRoomID() {
        return roomID;
    }
}
