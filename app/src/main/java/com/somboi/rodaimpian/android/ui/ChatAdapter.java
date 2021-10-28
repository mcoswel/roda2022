package com.somboi.rodaimpian.android.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.utils.SendNotif;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Chats> chatsList;
    private final PlayerOnline thisPlayer;
    private final DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chats");

    public ChatAdapter(Context context, PlayerOnline player, List<Chats> chatsList) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chats chats = chatsList.get(position);
        PlayerOnline player = chats.getPlayer();
        TextView chatContent = holder.itemView.findViewById(R.id.chat_content);
        CircleImageView playerImg = holder.itemView.findViewById(R.id.player_img);
        ConstraintLayout constraintLayout = holder.itemView.findViewById(R.id.chatlayoutforadapter);
        if (player.id.equals("8026")) {
            constraintLayout.setBackgroundColor(Color.GREEN);
        }
        if (chats.getPlayer().picUri != null) {
            Glide.with(context).load(chats.getPlayer().picUri).into(playerImg);
        }
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
                    final EditText editChat = new EditText(context);
                    alert.setTitle(R.string.reply);
                    alert.setView(editChat);
                    alert.setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            String content = editChat.getText().toString();
                            if (content.length() > 0) {
                                String pushKey = chatDatabase.push().getKey();
                                Chats thisChats = new Chats();
                                thisChats.setPushKey(pushKey);
                                thisChats.setContent(content);
                                thisChats.setPlayer(thisPlayer);
                                thisChats.setReplierName(chats.getPlayer().name);
                                thisChats.setReplyToId(chats.getPlayer().id);
                                thisChats.setSenderId(thisPlayer.id);
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
        chatContent.setText(chats.getContent());
        name.setText(chats.getPlayer().name);
        if (chats.getReplyToId() != null) {
            if (chats.getReplyToId().equals(thisPlayer.id)) {
                chatContent.setTextColor(Color.BLUE);
                name.setTextColor(Color.MAGENTA);
                name.setText(chats.getPlayer().name + " " + context.getString(R.string.replyto) + " " + thisPlayer.name);
            }
        }
        if (chats.getSenderId() != null) {
            if (chats.getSenderId().equals(thisPlayer.id)) {
                chatContent.setTextColor(Color.parseColor("#FF6347"));
                name.setTextColor(Color.MAGENTA);
                name.setText(thisPlayer.name + " " + context.getString(R.string.replyto) + " " + chats.getReplierName());
            }
        }

        Button invite = (Button) holder.itemView.findViewById(R.id.invite_but);
        if (thisPlayer.fcm_token != null) {
            if (player.fcm_token != null) {
                if (!thisPlayer.id.equals(player.id)) {
                    invite.setVisibility(View.VISIBLE);
                }
            }
        }
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final EditText editChat = new EditText(context);
                alert.setTitle(R.string.invite);
                alert.setView(editChat);
                alert.setPositiveButton(R.string.kirim, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        String content = editChat.getText().toString();
                        if (content.length() > 0) {
                            DatabaseReference dataComment = FirebaseDatabase.getInstance().getReference().child("Offline").child("Comment2022");

                            Comment comment = new Comment();
                            comment.setComment(content);

                            comment.setPicUri(thisPlayer.picUri);
                            comment.setName(thisPlayer.name);
                            comment.setPlayerWhoSendID(thisPlayer.id);
                            String pushKey = dataComment.push().getKey();
                            comment.setCommentID(pushKey);

                            if (thisPlayer.fcm_token != null) {
                                comment.setSenderFCM(thisPlayer.fcm_token);
                            }

                            if (player.fcm_token != null) {
                                SendNotif.send(comment, player.fcm_token, thisPlayer,true);
                            }

                            dataComment.child(player.id).child(comment.getCommentID()).setValue(comment);

                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return chatsList.size();
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
