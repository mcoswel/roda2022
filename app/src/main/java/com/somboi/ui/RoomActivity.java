package com.somboi.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esotericsoftware.jsonbeans.Json;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.somboi.gdx.entities.Player;
import com.somboi.rodaimpian.R;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private final DatabaseReference roomDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("rooms2022");
    private final List<Rooms> roomsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Player player;
    private final RoomListener roomListener = new RoomListener();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            String playerJson = extras.getString("player");
            Json json = new Json();
            player = json.fromJson(Player.class, playerJson);
            Log.d("RoomActivity", "Player json "+player.name);
        }

        setContentView(R.layout.roomactivity);
        recyclerView = findViewById(R.id.recycleViewRoomAct);
        roomDatabase.addValueEventListener(roomListener);
        roomDatabase.child(player.id).onDisconnect().removeValue();

    }

    @Override
    protected void onPause() {
        super.onPause();
        roomDatabase.removeEventListener(roomListener);
        roomDatabase.child(player.id).removeValue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        roomDatabase.child(player.id).removeValue();
        roomDatabase.removeEventListener(roomListener);

    }

    private class RoomListener implements ValueEventListener{

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            roomsList.clear();
            for (DataSnapshot ds: snapshot.getChildren()){
                roomsList.add(ds.getValue(Rooms.class));
            }

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.canScrollHorizontally();
            recyclerView.setLayoutManager(linearLayoutManager);
            RoomAdapter adapter = new RoomAdapter(RoomActivity.this, player, roomsList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(roomsList.size() - 1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}
