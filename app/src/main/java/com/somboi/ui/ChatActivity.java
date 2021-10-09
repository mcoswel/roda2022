package com.somboi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.jsonbeans.Json;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.somboi.gdx.entities.Player;
import com.somboi.rodaimpian.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private final Player player = new Player();
    private final DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chats");
    private final DatabaseReference bilOnline = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("onlinecount");
    private final DatabaseReference roomDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("rooms2022");
    private Button send, ciptaBilik, lihatBilik;
    private EditText editText;
    private final List<Chats> chatsList = new ArrayList<>();
    private int onlineCount;
    private RecyclerView recyclerView;
    private ChatListener chatListener = new ChatListener();
    private ValueEventListener bilOnlineListener;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity);
        FirebaseApp.initializeApp(this);
        intent = new Intent(ChatActivity.this, RoomActivity.class);
        recyclerView = findViewById(R.id.recycleView);
        send = findViewById(R.id.kirimBtn);
        editText = findViewById(R.id.chatInput);
        ciptaBilik = findViewById(R.id.ciptaBtn);
        lihatBilik = findViewById(R.id.lihatBtn);
      player.name = "Test User "+ MathUtils.random(0,100);
     player.id = "A"+MathUtils.random(0,100);
    //    player.name = "Test User 2";
   //  player.id = "A2";

        Json json = new Json();
        String playerJson = json.toJson(player, Player.class);
        intent.putExtra("player", playerJson);
        Rooms rooms = new Rooms();

        lihatBilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        ciptaBilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rooms.setHostPlayer(player);
                roomDatabase.child(player.id).setValue(rooms);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (content.length() > 0) {
                    String pushKey = chatDatabase.push().getKey();
                    Chats chats = new Chats();
                    chats.setPushKey(pushKey);
                    chats.setContent(content);
                    chats.setPlayer(player);
                    chatDatabase.child(pushKey).setValue(chats);
                    editText.setText("");
                    editText.clearFocus();
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                }
            }
        });

        chatDatabase.addValueEventListener(chatListener);

        bilOnlineListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bilOnline.child(player.id).setValue(true);
                onlineCount = (int) snapshot.getChildrenCount();
                TextView t = findViewById(R.id.bilOnline);
                t.setText("Online: " + onlineCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        bilOnline.addValueEventListener(bilOnlineListener);
        bilOnline.child(player.id).onDisconnect().removeValue();

    }

    private class ChatListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            chatsList.clear();
            for (DataSnapshot ds : snapshot.getChildren()) {
                Chats chats = ds.getValue(Chats.class);
                chatsList.add(chats);
            }


            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            linearLayoutManager.canScrollHorizontally();
            recyclerView.setLayoutManager(linearLayoutManager);
            ChatAdapter adapter = new ChatAdapter(ChatActivity.this, player, chatsList);
            recyclerView.setAdapter(adapter);
            recyclerView.scrollToPosition(chatsList.size() - 1);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeChats();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeChats();
    }

    private void removeChats() {
        chatDatabase.removeEventListener(chatListener);
        bilOnline.removeEventListener(bilOnlineListener);
        bilOnline.child(player.id).removeValue();
    }

}
