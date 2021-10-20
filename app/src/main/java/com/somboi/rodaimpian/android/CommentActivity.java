package com.somboi.rodaimpian.android;


import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.utils.Json;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;
import com.mopub.mobileads.MoPubView;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.android.ui.Comment;
import com.somboi.rodaimpian.android.ui.CommentAdapter;
import com.somboi.rodaimpian.gdx.entities.Player;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    PlayerOnline thisPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            Json json = new Json();
            thisPlayer = json.fromJson(PlayerOnline.class, extra.getString("player"));
        }
        DatabaseReference komenData = FirebaseDatabase.getInstance().getReference().child("Offline").child("Comment2022").child(thisPlayer.id);


        final SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(getString(R.string.mopubbanner));
        configBuilder.withLogLevel(MoPubLog.LogLevel.INFO);
        MoPub.initializeSdk(this, configBuilder.build(), initSdkListener());

        MoPubView moPubView = (MoPubView) findViewById(R.id.adView2);
        moPubView.setAdUnitId(getString(R.string.mopubbanner));
        moPubView.loadAd();

        ListView komenList = (ListView)findViewById(R.id.komenList);
        komenData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Comment> comments = new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){
                    comments.add(ds.getValue(Comment.class));
                }
                ListAdapter commentAdapter = new CommentAdapter(CommentActivity.this, comments,thisPlayer);
                komenList.setAdapter(commentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private SdkInitializationListener initSdkListener() {
        return new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                // SDK initialization complete. You may now request ads.
            }
        };
    }
}
