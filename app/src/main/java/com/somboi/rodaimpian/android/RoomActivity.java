package com.somboi.rodaimpian.android;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.gdx.utils.Json;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.android.ui.RoomAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RoomActivity extends AppCompatActivity implements RoomInterface {
    private final DatabaseReference roomDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("rooms2022");
    private final List<Rooms> roomsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PlayerOnline player = new PlayerOnline();
    private final RoomListener roomListener = new RoomListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String playerJson = extras.getString("player");
            Json json = new Json();
            player = json.fromJson(PlayerOnline.class, playerJson);
        }

        setContentView(R.layout.roomactivity);
        recyclerView = findViewById(R.id.recycleViewRoomAct);
        roomDatabase.addValueEventListener(roomListener);
        roomDatabase.child(player.id).onDisconnect().removeValue();

    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyData();
    }

    public void destroyData() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                roomDatabase.child(player.id).removeValue();
                roomDatabase.removeEventListener(roomListener);
            }
        },5000);


    }

    private class RoomListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            roomsList.clear();
            for (DataSnapshot ds : snapshot.getChildren()) {
                roomsList.add(ds.getValue(Rooms.class));
            }

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.canScrollHorizontally();
            recyclerView.setLayoutManager(linearLayoutManager);
            RoomAdapter adapter = new RoomAdapter(RoomActivity.this, player, roomsList, RoomActivity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(roomsList.size() - 1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    @Override
    public void startRodaOnline(Rooms rooms) {
        Intent intent = new Intent(RoomActivity.this, AndroidLauncher.class);
        intent.putExtra("online", true);
        Json json = new Json();
        String roomsJson = json.toJson(rooms, Rooms.class);
        intent.putExtra("rooms", roomsJson);
        startActivity(intent);
    }


}
