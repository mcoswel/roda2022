package com.somboi.rodaimpian.android.ui;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.gdx.entities.Player;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Activity context;
    List<Comment> thisCommentList;
    PlayerOnline thisPlayer;

    public CommentAdapter(@NonNull Activity context, List<Comment> thisCommentList, PlayerOnline thisPlayer) {
        super(context, R.layout.chat_layout, thisCommentList);
        this.context = context;
        this.thisCommentList = thisCommentList;
        this.thisPlayer = thisPlayer;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint({"InflateParams", "ViewHolder"}) View myView = inflater.inflate(R.layout.chat_layout, null, true);
        Comment comment = getItem(position);
        TextView commentTxt = myView.findViewById(R.id.comment_content);
        TextView nameTxt = myView.findViewById(R.id.commenter_name);
        CircleImageView profileImage = myView.findViewById(R.id.player1_image);
        Button reply = myView.findViewById(R.id.reply);
        Button delete = myView.findViewById(R.id.delete);
        Glide.with(context).load(Uri.parse(comment.getPicUri())).placeholder(R.drawable.default_avatar).into(profileImage);
        commentTxt.setText("\"" + comment.getComment() + "\"");
        nameTxt.setText(comment.getName());
        DatabaseReference dataComment = FirebaseDatabase.getInstance().getReference().child("Offline").child("Comment2022");


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataComment.child(thisPlayer.id).child(comment.getCommentID()).removeValue();
            }
        });


        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                final EditText edittext = new EditText(context);
                alert.setTitle("Balas Mesej:");
                alert.setView(edittext);
                alert.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //What ever you want to do with the value
                        if (edittext.getText().length() > 0) {
                            Comment thisComment = new Comment();
                            thisComment.setComment(edittext.getText().toString());
                            thisComment.setName(thisPlayer.name);
                            thisComment.setPicUri(thisPlayer.picUri);
                            thisComment.setCommentID(dataComment.push().getKey());
                            thisComment.setPlayerWhoSendID(thisPlayer.id);
                            dataComment.child(comment.getPlayerWhoSendID()).child(thisComment.getCommentID()).setValue(thisComment);
                        } else {
                            dialog.dismiss();
                        }
                    }
                }).show();

            }
        });
        return myView;
    }
}
