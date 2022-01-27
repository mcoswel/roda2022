package com.somboi.rodaimpian.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.gdx.utils.Json;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
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
import com.somboi.rodaimpian.ui.LeaderboardInterface;
import com.somboi.rodaimpian.ui.ScoreAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderBoard extends AppCompatActivity implements LeaderboardInterface {

    private PlayerOnline thisPlayer;
    private List<PlayerOnline> playerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String playerJson = extras.getString("playeronline");
            Json json = new Json();
            thisPlayer = json.fromJson(PlayerOnline.class, playerJson);
        }

        // saveRestore = new SaveRestore(this);

       // thisPlayer = saveRestore.getPlayer();
        //listScore = findViewById(R.id.leaderListView);
        //String month= new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime());
        // String year= new SimpleDateFormat("YYYY").format(Calendar.getInstance().getTime());
        // Log.d("MonthYear",month+year);


        final SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder(getString(R.string.mopubbanner));

        configBuilder.withLogLevel(MoPubLog.LogLevel.INFO);
        MoPub.initializeSdk(this, configBuilder.build(), initSdkListener());

        MoPubView moPubView = (MoPubView) findViewById(R.id.adView);
        moPubView.setAdUnitId(getString(R.string.mopubbanner));
        moPubView.loadAd();


        ReviewManager manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        ((Task) request).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = (ReviewInfo) task.getResult();
                Task<Void> flow = manager.launchReviewFlow(this, reviewInfo);
                flow.addOnCompleteListener(task2 -> {
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                });
            } else {
                // There was some problem, continue regardless of the result.
            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Offline").child("jan2022");
        //DatabaseReference monthlyData = FirebaseDatabase.getInstance().getReference().child("Offline").child(month+year);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playerList = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    PlayerOnline p = ds.getValue(PlayerOnline.class);
                    playerList.add(p);
                }
                Collections.sort(playerList);
                for (int i = 0; i < playerList.size(); i++) {
                    playerList.get(i).rank = (i + 1);
                }
                if (playerList.size()>100){
                    for (int i = 98; i<playerList.size(); i++){
                        databaseReference.child(playerList.get(i).id).removeValue();
                        playerList.remove(i);
                    }
                }


                findViewById(R.id.progressLeaderbord).setVisibility(View.INVISIBLE);
                //ScoreAdapter scoreAdapter = new ScoreAdapter(LeaderBoard.this, newList, thisPlayer);
                //listScore.setAdapter(scoreAdapter);

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycle);
                LinearLayoutManager linearLayoutManager =
                        new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                linearLayoutManager.canScrollHorizontally();
                recyclerView.setLayoutManager(linearLayoutManager);
                ScoreAdapter scoreAdapter = new ScoreAdapter(LeaderBoard.this, playerList, thisPlayer, LeaderBoard.this);
                recyclerView.setAdapter(scoreAdapter);
                return;
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

    @Override
    public void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}

