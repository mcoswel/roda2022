package com.somboi.rodaimpian.android;

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

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.badlogic.gdx.utils.Json;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.privacy.ConsentDialogListener;
import com.mopub.common.privacy.PersonalInfoManager;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.RodaImpian;
import com.somboi.rodaimpian.TargetGlide;
import com.somboi.rodaimpian.gdx.entities.MainMenuCreator;
import com.somboi.rodaimpian.gdx.entities.Player;
import com.somboi.rodaimpian.gdx.modes.GameModes;
import com.somboi.rodaimpian.gdx.modes.OnlinePlay;
import com.somboi.rodaimpian.gdx.online.ChatOnline;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

import barsoosayque.libgdxoboe.OboeAudio;

public class AndroidLauncher extends AndroidApplication implements AndroidInterface {
    private static final int REQUEST_GALLERY_IMAGE = 3;
    private String filename;
    private MainMenuCreator mainMenuCreator;
    private Player player;
    private PlayerOnline playerOnline = new PlayerOnline();
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private RodaImpian rodaImpian;
    private ChatOnline chatOnline;
    private InterstitialAd googleInter;
    private FirebaseAnalytics mFirebaseAnalytics;
    private com.facebook.ads.InterstitialAd facebookInter;
    private MoPubInterstitial moPubInterstitial;

    @Override
    public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
        return new OboeAudio(context.getAssets());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        rodaImpian = new RodaImpian(this);
        rodaImpian.setGameModes(GameModes.SINGLE);


        View gameView = initializeForView(rodaImpian, config);
        setContentView(gameView);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AudienceNetworkAds.initialize(this);
        facebookInter = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_inter_ads));


        final SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(getString(R.string.mopub_inter));
        configBuilder.withLogLevel(MoPubLog.LogLevel.INFO);
        MoPub.initializeSdk(this, configBuilder.build(), initSdkListener());
        PersonalInfoManager mPersonalInfoManager = MoPub.getPersonalInformationManager();
        mPersonalInfoManager.shouldShowConsentDialog();
        moPubInterstitial = new MoPubInterstitial(this, getString(R.string.mopub_inter));
        mPersonalInfoManager.loadConsentDialog(
                new ConsentDialogListener() {
                    @Override
                    public void onConsentDialogLoaded() {
                        if (mPersonalInfoManager != null) {
                            // If you choose to show the consent dialog in the future, check if it is ready using isConsentDialogReady() before showing it
                            mPersonalInfoManager.showConsentDialog();
                        }
                    }

                    @Override
                    public void onConsentDialogLoadFailed(@NonNull MoPubErrorCode moPubErrorCode) {
                    }
                }
        );

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case REQUEST_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();

                    CropImage.activity(imageUri)
                            .setMinCropResultSize(250, 250)
                            .setMaxCropResultSize(250, 250)
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
                        if (player.logged && (!(filename.equals(getString(R.string.p2imagepath)) || filename.equals(getString(R.string.p3imagepath))))) {
                            uploadPic(bm);
                        } else {
                            new SavePhotoTask().execute(picData);
                        }
                    }
                });
    }

    private void uploadPic(Bitmap bm) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference mountainsRef = storageRef.child("PlayerPic2022").child(player.id + ".jpg");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 75, out);
        byte[] data = out.toByteArray();
        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnProgressListener(snapshot -> {
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();

        });

        uploadTask.addOnSuccessListener(
                taskSnapshot -> mountainsRef.getDownloadUrl().addOnCompleteListener(task -> {
                    String image = task.getResult().toString();
                    player.picUri = (Uri.parse(image).toString());
                    mainMenuCreator.savePlayer(player);
                    mainMenuCreator.loadOnlinePic();
                }));

    }

    @Override
    public void choosePhoto(int playerInt) {

        if (playerInt == 0) {
            filename = getString(R.string.p1imagepath);
        } else if (playerInt == 1) {
            filename = getString(R.string.p2imagepath);
        } else if (playerInt == 2) {
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
                            startActivityForResult(pickPhoto, AndroidLauncher.REQUEST_GALLERY_IMAGE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setPlayerOnline(PlayerOnline playerOnline) {
        this.playerOnline = playerOnline;
    }

    @Override
    public void setMenuCreator(MainMenuCreator mainMenuCreator) {
        this.mainMenuCreator = mainMenuCreator;
    }

    @Override
    public void chat(int guiIndex, OnlinePlay onlinePlay) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder alert = new AlertDialog.Builder(AndroidLauncher.this);
                final EditText editChat = new EditText(AndroidLauncher.this);
                alert.setTitle(R.string.chat);
                alert.setView(editChat);
                alert.setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String content = editChat.getText().toString();
                        if (content.length() > 0) {
                            chatOnline = new ChatOnline();
                            chatOnline.content = content;
                            chatOnline.guiIndex = guiIndex;
                            onlinePlay.sendChat(chatOnline);
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
    public void loginFB() {

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
                    Profile profile = Profile.getCurrentProfile();
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
    public void leaderBoard() {
        Intent intent = new Intent(this, LeaderBoard.class);
        Json json = new Json();
        String playerOnlineJson = json.toJson(playerOnline, PlayerOnline.class);
        intent.putExtra("playeronline", playerOnlineJson);
        startActivity(intent);
    }

    private void getFaceBookDetail() {
        player.name = Profile.getCurrentProfile().getName();

        if (player.name.length() > 10) {
            player.name = player.name.substring(0, 10);
        }
        playerOnline.name = player.name;
        player.picUri = (Profile.getCurrentProfile().getProfilePictureUri(250, 250).toString());
        playerOnline.picUri = player.picUri;

        player.id = (Profile.getCurrentProfile().getId());
        playerOnline.id = player.id;
        playerOnline.logged = true;
        player.logged = true;
        mainMenuCreator.savePlayer(player);
        mainMenuCreator.savePlayerOnline(playerOnline);
        rodaImpian.create();
    }


    class SavePhotoTask extends AsyncTask<byte[], String, String> {
        @Override
        protected String doInBackground(byte[]... jpeg) {
            Log.d("Photo", "executed");
            File photo = new File(getFilesDir() + "/" + filename);
            if (photo.exists()) {
                photo.delete();
            }

            try {
                FileOutputStream fos = new FileOutputStream(photo.getPath());
                fos.write(jpeg[0]);
                fos.close();
            } catch (java.io.IOException e) {
                Log.e("PictureDemo", "Exception in photoCallback", e);
            }

            return (null);
        }

        @Override
        protected void onPostExecute(String s) {
            //  menuCreator.loadLocalPic();
        }
    }

    @Override
    public void onlineChat() {
        Intent intent = new Intent(this, ChatActivity.class);
        Json json = new Json();
        String playerOnlineJson = json.toJson(rodaImpian.getPlayerOnline());
        intent.putExtra("player", playerOnlineJson);
        startActivity(intent);
    }

    @Override
    public void comments() {
        Intent intent = new Intent(this, CommentActivity.class);
        Json json = new Json();
        String playerOnlineJson = json.toJson(rodaImpian.getPlayerOnline());
        intent.putExtra("player", playerOnlineJson);
        startActivity(intent);
    }

    @Override
    public void uploadScore(PlayerOnline playerOnline) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Offline").child("2022");
        databaseReference.child(playerOnline.id).setValue(playerOnline);
    }

    @Override
    public void loadAllAds() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadGoogleAds();
                loadMopubAds();
                loadFacebookAds();
            }
        });
    }

    @Override
    public void showAds(final int gameRound) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
              chooseAds(gameRound);
            }
        });

    }

    private void chooseAds(int gameRound) {
        if (gameRound == 0) {
            if (googleInter != null) {
                googleInter.show(AndroidLauncher.this);
            } else if (facebookInter.isAdLoaded()) {
                facebookInter.show();
            } else if (moPubInterstitial.isReady()) {
                moPubInterstitial.show();
            }
        } else if (gameRound == 1) {
            if (facebookInter.isAdLoaded()) {
                facebookInter.show();
            } else if (moPubInterstitial.isReady()) {
                moPubInterstitial.show();
            } else if (googleInter != null) {
                googleInter.show(AndroidLauncher.this);
            }
        } else if (gameRound == 2) {
            if (moPubInterstitial.isReady()) {
                moPubInterstitial.show();
            } else if (googleInter != null) {
                googleInter.show(AndroidLauncher.this);
            } else if (facebookInter.isAdLoaded()) {
                facebookInter.show();
            }
        } else {
            if (googleInter != null) {
                googleInter.show(AndroidLauncher.this);
            } else if (moPubInterstitial.isReady()) {
                moPubInterstitial.show();
            } else if (facebookInter.isAdLoaded()) {
                facebookInter.show();
            }
        }
    }

    private void loadGoogleAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(AndroidLauncher.this, getString(R.string.google_inter_id), adRequest, new InterstitialAdLoadCallback() {
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

    private void loadMopubAds() {
        moPubInterstitial.loadAd();
    }


    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
            }
        };
    }

}
