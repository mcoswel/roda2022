package com.somboi.rodaimpian.activities;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.badlogic.gdx.utils.Json;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.ui.ChatAdapter;
import com.somboi.rodaimpian.ui.Chats;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private PlayerOnline player = new PlayerOnline();
    private final DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chatsjan2022");
    private final DatabaseReference bilOnline = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("onlinecount");
    private Button send;
    private EditText editText;
    private final List<Chats> chatsList = new ArrayList<>();
    private int onlineCount;
    private RecyclerView recyclerView;
    private ChatListener chatListener = new ChatListener();
    private ValueEventListener bilOnlineListener;
    private AdView mAdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatactivity);
        FirebaseApp.initializeApp(this);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            Json json = new Json();
            player = json.fromJson(PlayerOnline.class, extra.getString("player"));
        }
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        recyclerView = findViewById(R.id.recycleView);
        send = findViewById(R.id.kirimBtn);
        editText = findViewById(R.id.chatInput);

        mAdView = findViewById(R.id.ad_online_chat);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
/*

        player.picUri = "https://firebasestorage.googleapis.com/v0/b/roda-impian-acc41.appspot.com/o/avatar_lelex.png?alt=media&token=83d0bf7f-454b-49b7-a102-f030ab2a13df";
        player.name = "Lelex Games";
        player.id = "8026";
*/





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
        finish();
        if(mAdView!=null) {
            mAdView.destroy();
        }

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    private void removeChats() {
        chatDatabase.removeEventListener(chatListener);
        bilOnline.removeEventListener(bilOnlineListener);
        bilOnline.child(player.id).removeValue();
    }



}
