package com.somboi.rodaimpian;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.somboi.gdx.RodaImpian;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        View gameView = initializeForView(new RodaImpian(this), config);
        setContentView(gameView);
    }
}
