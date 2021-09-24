package com.somboi.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class RoomActivity extends AppCompatActivity {

    private final Player player = new Player();
    private DatabaseReference chatDatabase;
    private DatabaseReference bilOnline;
    private Button send, ciptaBilik, lihatBilik;
    private EditText editText;
    private final List<Chats> chatsList = new ArrayList<>();
    private int onlineCount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roomactivity);
        FirebaseApp.initializeApp(this);
        chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chats");
        bilOnline = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("onlinecount");
        send = findViewById(R.id.kirimBtn);
        editText = findViewById(R.id.chatInput);

        player.name = "lelex";
        player.id = "12345";

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                if (content.length() > 0) {
                    String pushKey = chatDatabase.push().getKey();
                    Chats chats = new Chats()  ;
                    chats.setPushKey(pushKey);
                    chats.setContent(content);
                    chats.setPlayer(player);
                    chatDatabase.child(pushKey).setValue(chats);
                }
            }
        });

        chatDatabase.addValueEventListener(new ChatListener());


        bilOnline.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onlineCount = (int)snapshot.getChildrenCount()+1;
                bilOnline.child(player.id).setValue(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private class ChatListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            chatsList.clear();
            for (DataSnapshot ds : snapshot.getChildren()) {
                Chats chats = ds.getValue(Chats.class);
                chatsList.add(chats);
                Log.d(this.getClass().getName(), ds.getKey());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }
}
