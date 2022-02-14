package com.somboi.rodaimpian;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.somboi.rodaimpian.activities.ChatActivity;
import com.somboi.rodaimpian.activities.CommentActivity;
import com.somboi.rodaimpian.activities.LeaderBoard;
import com.somboi.rodaimpian.activities.PlayerOnline;
import com.somboi.rodaimpian.activities.RodaImpianNew;
import com.somboi.rodaimpian.gdxnew.entitiesnew.PlayerNew;
import com.somboi.rodaimpian.gdxnew.interfaces.OnInterface;
import com.somboi.rodaimpian.gdxnew.onlineclasses.ChatOnline;
import com.somboi.rodaimpian.gdxnew.utils.CopyPlayer;
import com.somboi.rodaimpian.gdxnew.utils.ShortenName;
import com.somboi.rodaimpian.ui.Chats;
import com.somboi.rodaimpian.ui.TargetGlide;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import barsoosayque.libgdxoboe.OboeAudio;

public class AndroidLauncherNew extends AndroidApplication implements AndroInterface, OnUserEarnedRewardListener {
    private RodaImpianNew rodaImpianNew;
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private static final int REQUEST_GALLERY_IMAGE = 3;
    private static final int RC_SIGN_IN = 5;
    private String filename;
    private String fcm_token;
    private MaxInterstitialAd maxInter;
    private MaxRewardedAd maxRewardedAd;
    private InterstitialAd googleInter;
    private com.facebook.ads.InterstitialAd facebookInter;
    private RewardedInterstitialAd googleRewarded;
    private RewardedVideoAd facebookRewarded;
    private FirebaseAnalytics mFirebaseAnalytics;

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


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration) {
                // AppLovin SDK is initialized, start loading ads
            }
        });

        maxInter = new MaxInterstitialAd(getString(R.string.maxinter), this);
        maxRewardedAd = MaxRewardedAd.getInstance(getString(R.string.maxrewarded), this);

        AudienceNetworkAds.initialize(this);
        facebookInter = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_inter_ads));
        facebookRewarded = new RewardedVideoAd(this, getString(R.string.fb_rewarded_ads));

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    private void getFaceBookDetail() {
        PlayerNew playerNew = new PlayerNew();
        playerNew.setName(ShortenName.execute(Profile.getCurrentProfile().getName()));
        playerNew.setPicUri(Profile.getCurrentProfile().getProfilePictureUri(300, 300).toString());
        playerNew.setUid(Profile.getCurrentProfile().getId());
        playerNew.setLogged(true);
        if (fcm_token != null) {
            playerNew.setFcmToken(fcm_token);
        }
        rodaImpianNew.setPlayer(playerNew);
        rodaImpianNew.getMainScreen().reloadOnlineProfile();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

    }

    @Override
    public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
        return new OboeAudio(context.getAssets());
    }

    @Override
    public void loadRewardedAds() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (googleRewarded == null) {
                    RewardedInterstitialAd.load(AndroidLauncherNew.this, getString(R.string.google_rewarded_id),
                            new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                                @Override
                                public void onAdLoaded(RewardedInterstitialAd ad) {
                                    googleRewarded = ad;
                                }

                                @Override
                                public void onAdFailedToLoad(LoadAdError loadAdError) {
                                    googleRewarded = null;
                                }
                            });
                }

                if (!facebookRewarded.isAdLoaded()) {
                    RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {

                        @Override
                        public void onError(Ad ad, AdError adError) {


                        }

                        @Override
                        public void onAdLoaded(Ad ad) {

                        }

                        @Override
                        public void onAdClicked(Ad ad) {

                        }

                        @Override
                        public void onLoggingImpression(Ad ad) {

                        }

                        @Override
                        public void onRewardedVideoCompleted() {
                            rodaImpianNew.setRewardedLoaded(true);
                        }

                        @Override
                        public void onRewardedVideoClosed() {
                            // The Rewarded Video ad was closed - this can occur during the video
                            // by closing the app, or closing the end card.
                        }
                    };
                    facebookRewarded.loadAd(
                            facebookRewarded.buildLoadAdConfig()
                                    .withAdListener(rewardedVideoAdListener)
                                    .build());
                }

                maxRewardedAd.setListener(new MaxRewardedAdListener() {
                    @Override
                    public void onRewardedVideoStarted(MaxAd ad) {

                    }

                    @Override
                    public void onRewardedVideoCompleted(MaxAd ad) {
                        rodaImpianNew.setRewardedLoaded(true);
                    }

                    @Override
                    public void onUserRewarded(MaxAd ad, MaxReward reward) {
                        rodaImpianNew.setRewardedLoaded(true);
                    }

                    @Override
                    public void onAdLoaded(MaxAd ad) {

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                maxRewardedAd.loadAd();
                            }
                        }, 60f);
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                    }
                });
                maxRewardedAd.loadAd();
            }
        });
    }

    @Override
    public void loadAllAds() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (googleInter == null) {
                    loadGoogleAds();
                }
                if (!maxInter.isReady()) {
                    loadMaxInter();
                }
                if (!facebookInter.isAdLoaded()) {
                    loadFacebookAds();
                }
            }
        });
    }

    @Override
    public void showRewardedAds() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (googleRewarded != null) {
                    googleRewarded.show(AndroidLauncherNew.this, AndroidLauncherNew.this);
                } else if (facebookRewarded.isAdLoaded()) {
                    facebookRewarded.show();
                } else if (maxRewardedAd.isReady()) {
                    maxRewardedAd.showAd();
                } else {
                    rodaImpianNew.setRewardedLoaded(true);
                }
            }
        });
    }

    @Override
    public void showAds(int gameRound) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (gameRound % 2 == 0) {
                    if (googleInter != null) {
                        googleInter.show(AndroidLauncherNew.this);
                    } else if (facebookInter.isAdLoaded()) {
                        facebookInter.show();
                    } else if (maxInter.isReady()) {
                        maxInter.showAd();
                    }
                } else {
                    if (maxInter.isReady()) {
                        maxInter.showAd();
                    } else if (facebookInter.isAdLoaded()) {
                        facebookInter.show();
                    } else if (googleInter != null) {
                        googleInter.show(AndroidLauncherNew.this);
                    }
                }
            }
        });


    }

    @Override
    public void sahibba() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.somboi.tekakata")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.somboi.tekakata")));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            .setCropShape(CropImageView.CropShape.OVAL)
                            .setFixAspectRatio(true)
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this);
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    //cropImageView.setImageUriAsync(resultUri);
                    glideIt(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }

                break;
        }
        if (callbackManager.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            getGmailInfo(account);
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

        }

    }

    private void getGmailInfo(GoogleSignInAccount acct) {
        if (acct != null) {
            rodaImpianNew.getPlayer().setName(ShortenName.execute(acct.getDisplayName()));
            rodaImpianNew.getPlayer().setUid(acct.getId());
            if (acct.getPhotoUrl() != null) {
                rodaImpianNew.getPlayer().setPicUri(acct.getPhotoUrl().toString());
            }
            rodaImpianNew.getPlayer().setLogged(true);
            if (fcm_token != null) {
                rodaImpianNew.getPlayer().setFcmToken(fcm_token);
            }
            rodaImpianNew.getMainScreen().reloadOnlineProfile();

        }
    }

    private void glideIt(Uri resultUri) {
        Glide.with(this).asBitmap().load(resultUri)
                .apply(new RequestOptions().override(350, 350))
                .into(new TargetGlide() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Bitmap bm = Bitmap.createScaledBitmap(resource, 350, 350, false);
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        byte[] picData = out.toByteArray();
                        if (rodaImpianNew.getPlayer().isLogged()
                                &&
                                (!(filename.equals(getString(R.string.p2imagepath)) || filename.equals(getString(R.string.p3imagepath))))) {
                            uploadPic(bm);
                        } else {
                            Log.d("glide", "glide");

                            new SavePhotoTask().execute(picData);
                        }
                    }
                });
    }

    private void uploadPic(Bitmap bm) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference mountainsRef =
                storageRef.child("PlayerPic2022").child(rodaImpianNew.getPlayer().getUid() + ".jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 75, out);
        byte[] data = out.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnSuccessListener(
                taskSnapshot -> mountainsRef.getDownloadUrl().addOnCompleteListener(task -> {
                    String image = task.getResult().toString();
                    rodaImpianNew.getPlayer().setPicUri((Uri.parse(image).toString()));
                    rodaImpianNew.savePlayer();
                    rodaImpianNew.reloadMenu();
                }));
    }

    @Override
    public void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Collections.singletonList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            getFaceBookDetail();
                            mProfileTracker.stopTracking();

                        }
                    };
                } else {
                    getFaceBookDetail();
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
    public void loginGmail() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void choosePhoto(int playerInt) {
        if (playerInt == 1) {
            filename = getString(R.string.p1imagepath);
        } else if (playerInt == 2) {
            filename = getString(R.string.p2imagepath);
        } else if (playerInt == 3) {
            filename = getString(R.string.p3imagepath);
        }
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            pickPhoto.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(pickPhoto, AndroidLauncherNew.REQUEST_GALLERY_IMAGE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        fcm_token = task.getResult();
                        rodaImpianNew.setFcmToken(fcm_token);
                    }
                });
    }

    @Override
    public void chatOnline(final OnInterface onInterface, final int guiIndex) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alert = new AlertDialog.Builder(AndroidLauncherNew.this);
                final EditText editChat = new EditText(AndroidLauncherNew.this);
                alert.setTitle(R.string.chat);
                alert.setView(editChat);
                alert.setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String content = editChat.getText().toString();
                        if (content.length() > 0) {
                            ChatOnline chatOnline = new ChatOnline();
                            chatOnline.setText(content);
                            chatOnline.setGuiIndex(guiIndex);
                            Gdx.app.postRunnable(new Runnable() {
                                @Override
                                public void run() {
                                    onInterface.sendObjects(chatOnline);
                                }
                            });
                            //     return new ChatBubble(content,skin,guiIndex);
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });

    }

    @Override
    public void leaderboardActivity() {
        Intent intent = new Intent(this, LeaderBoard.class);
        intent.putExtra("playeronline", playerOnlineJson());
        startActivity(intent);
    }

    @Override
    public void uploadToLeaderBoard() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Offline").child("jan2022");
        PlayerOnline playerOnline = CopyPlayer.getPlayerOnline(rodaImpianNew.getPlayer());
        databaseReference.child(playerOnline.id).setValue(playerOnline);
    }

    @Override
    public void chatActivity() {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("player", playerOnlineJson());
        startActivity(intent);
    }

    @Override
    public void commentActivity() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("player", playerOnlineJson());
        startActivity(intent);
    }

    @Override
    public void logout() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Offline").child("jan2022");
        if (rodaImpianNew.getPlayer().getUid() != null) {
            databaseReference.child(rodaImpianNew.getPlayer().getUid()).removeValue();
        }
        DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chatsjan2022");
        chatDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Chats chats = ds.getValue(Chats.class);
                    if (rodaImpianNew.getPlayer().getUid() != null) {
                        if (chats.getPlayer().id.equals(rodaImpianNew.getPlayer().getUid())) {
                            chatDatabase.child(chats.getPushKey()).removeValue();
                        }
                    }
                }
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private String playerOnlineJson() {
        Json json = new Json();
        PlayerOnline playerOnline = CopyPlayer.getPlayerOnline(rodaImpianNew.getPlayer());
        String playerOnlineJson = json.toJson(playerOnline);
        return playerOnlineJson;
    }

    @Override
    public void getTopPlayers() {
        List<PlayerOnline> topPlayers = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Offline").child("jan2022");
        //DatabaseReference monthlyData = FirebaseDatabase.getInstance().getReference().child("Offline").child(month+year);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PlayerOnline> playerList = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    PlayerOnline p = ds.getValue(PlayerOnline.class);
                    playerList.add(p);
                }
                Collections.sort(playerList);
                for (int i = 0; i < 3; i++) {
                    if (i < playerList.size()) {
                        topPlayers.add(playerList.get(i));
                    }
                }
                rodaImpianNew.updateTopPlayer(topPlayers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadGoogleAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(AndroidLauncherNew.this, getString(R.string.google_inter_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                googleInter = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                googleInter = null;
            }
        });
    }

    private void loadFacebookAds() {
        facebookInter.loadAd();
    }

    private void loadMaxInter() {
        maxInter.loadAd();
    }


    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        rodaImpianNew.setRewardedLoaded(true);
    }

    private class SavePhotoTask extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... jpeg) {

            File photo = new File(getFilesDir() + "/" + filename);
            if (photo.exists()) {
                photo.delete();
            }
            try {
                FileOutputStream fos = new FileOutputStream(photo.getPath());
                fos.write(jpeg[0]);
                fos.close();
            } catch (java.io.IOException e) {
            }


            return (null);
        }

        @Override
        protected void onPostExecute(String s) {
            //  menuCreator.loadLocalPic();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rodaImpianNew.dispose();
        facebookInter.destroy();
        facebookRewarded.destroy();
        maxInter.destroy();
        maxRewardedAd.destroy();
    }
}
