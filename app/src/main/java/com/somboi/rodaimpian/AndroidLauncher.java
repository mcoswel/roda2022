package com.somboi.rodaimpian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidAudio;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.somboi.gdx.RodaImpian;
import com.somboi.gdx.entities.MenuCreator;
import com.somboi.gdx.entities.Player;
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
    private MenuCreator menuCreator;
    private Player player;
    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;

    @Override
    public AndroidAudio createAudio(Context context, AndroidApplicationConfiguration config) {
        return new OboeAudio(context.getAssets());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        View gameView = initializeForView(new RodaImpian(this), config);
        setContentView(gameView);

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
                        if (player.logged && !(filename.equals(getString(R.string.p2imagepath)) || filename.equals(getString(R.string.p3imagepath)))) {
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
                    menuCreator.savePlayer(player);
                    menuCreator.loadOnlinePic();
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
    public void setMenuCreator(MenuCreator menuCreator) {
        this.menuCreator = menuCreator;
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

    private void getFaceBookDetail() {
        player.name = Profile.getCurrentProfile().getName();
        if (player.name.length()>15){
            player.name = player.name.substring(0,15);
        }
        player.picUri = (Profile.getCurrentProfile().getProfilePictureUri(250, 250).toString());
        player.id = (Profile.getCurrentProfile().getId());
        player.logged = true;
        menuCreator.savePlayer(player);
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
}
