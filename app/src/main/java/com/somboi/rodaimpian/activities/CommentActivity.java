package com.somboi.rodaimpian.activities;


import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applovin.sdk.AppLovinSdk;
import com.badlogic.gdx.utils.Json;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.ui.Comment;
import com.somboi.rodaimpian.ui.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    PlayerOnline thisPlayer;
    private AdView mAdView;
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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.ad_chat);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAdView!=null) {
            mAdView.destroy();
        }
    }
}
