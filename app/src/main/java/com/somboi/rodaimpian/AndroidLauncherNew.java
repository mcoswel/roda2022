package com.somboi.rodaimpian;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.somboi.rodaimpian.gdx.utils.ShortenName;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.screens.MainScreen;

import java.util.Collections;

import barsoosayque.libgdxoboe.OboeAudio;

public class AndroidLauncherNew extends AndroidApplication implements AndroInterface{
    private String fcmToken;
    private RodaImpianNew rodaImpianNew;
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        rodaImpianNew = new RodaImpianNew(this);
        View gameView = initializeForView(rodaImpianNew, config);
        setContentView(gameView);

        callbackManager = CallbackManager.Factory.create();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        fcmToken = task.getResult();
                        rodaImpianNew.setFcmToken(fcmToken);
                    }
                });

    }
    private void getFaceBookDetail(MainScreen mainScreen){
        PlayerNew playerNew = new PlayerNew();
        playerNew.setName(ShortenName.execute(Profile.getCurrentProfile().getName()));
        playerNew.setPicUri(Profile.getCurrentProfile().getProfilePictureUri(300, 300).toString());
        playerNew.setUid(Profile.getCurrentProfile().getId());
        playerNew.setLogged(true);
        rodaImpianNew.setPlayer(playerNew);
        mainScreen.loadFacebook();
    }

    @Override
    public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
        return new OboeAudio(context.getAssets());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
    }

    @Override
    public void loginFacebook(MainScreen mainScreen) {
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            getFaceBookDetail(mainScreen);
                            mProfileTracker.stopTracking();

                        }
                    };
                } else {
                    getFaceBookDetail(mainScreen);
                }
            }

            @Override
            public void onCancel() {
                // App code

            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}
