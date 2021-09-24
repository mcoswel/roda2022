package com.somboi.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.somboi.gdx.entities.Player;
import com.somboi.rodaimpian.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Chats> chatsList;
    private final Player thisPlayer;
    private final DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chats");

    public ChatAdapter(Context context, Player player, List<Chats> chatsList) {
        this.context = context;
        this.thisPlayer = player;
        this.chatsList = chatsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatadapter, parent, false);
        ChatAdapter.MyViewHolder vh = new ChatAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chats chats = chatsList.get(position);
        Player player = chats.getPlayer();
        TextView chatContent = holder.itemView.findViewById(R.id.chat_content);

        if (thisPlayer.id.equals(player.id)) {
            Button delete = holder.itemView.findViewById(R.id.padamChatBtn);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatDatabase.child(chats.getPushKey()).removeValue();
                }
            });

        } else {
            Button reply = holder.itemView.findViewById(R.id.replyChatBtn);
            reply.setVisibility(View.VISIBLE);
            reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    final EditText edittext = new EditText(context);
                    alert.setTitle(R.string.reply);
                    alert.setView(edittext);
                    alert.setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            String content = edittext.getText().toString();
                            if (content.length() > 0) {
                                String pushKey = chatDatabase.push().getKey();
                                Chats thisChats = new Chats();
                                thisChats.setContent(content);
                                thisChats.setPlayer(thisPlayer);
                                thisChats.setReplierName(chats.getPlayer().name);
                                thisChats.setReplyToId(chats.getPlayer().id);
                                chatDatabase.child(pushKey).setValue(thisChats);
                            } else {
                                dialog.dismiss();
                            }
                        }
                    }).show();
                }
            });
        }

        TextView name = holder.itemView.findViewById(R.id.chatname);

        if (chats.getReplyToId() == thisPlayer.id) {
            chatContent.setTextColor(Color.BLUE);
            name.setText(chats.getPlayer().name + " " + R.string.reply + " " + thisPlayer.name);
        } else {
            chatContent.setText(chats.getContent());
            name.setText(chats.getPlayer().name);
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
