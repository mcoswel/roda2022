package com.somboi.rodaimpian.android.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.somboi.rodaimpian.R;
import com.somboi.rodaimpian.android.AndroidLauncher;
import com.somboi.rodaimpian.android.PlayerOnline;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreAdapter extends RecyclerView.Adapter {
    private ImageView badgeCrown;
    private ImageView badgeBankrupt;
    private ImageView badgeScore;
    private ImageView badgeTopFan;
    private ImageView[] badge_g;
    private ImageView[] badge_b;
    private Button chat;
    private Button likeBtn;
    private Button deleteThis;

    private CircleImageView profilePic;
    private final Context context;
    private final List<PlayerOnline> playerlist;
    private final PlayerOnline thisPlayer;
    private TextView rank_txt;
    private ImageView rank_img;
    private TextView scoreTxt;
    private TextView nameTxt;
    private LoadBadge loadbadge;
    private Button info;
    public ScoreAdapter(Context context, List<PlayerOnline> playerlist, PlayerOnline thisPlayer) {
        this.context = context;
        this.playerlist = playerlist;
        this.thisPlayer = thisPlayer;
        loadbadge = new LoadBadge(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        rank_img = holder.itemView.findViewById(R.id.rank_Img);
        rank_txt = holder.itemView.findViewById(R.id.rank_Txt);


        PlayerOnline player = playerlist.get(holder.getAdapterPosition());
        DatabaseReference dataLike = FirebaseDatabase.getInstance().getReference().child("Offline").child("Likes");
        int rank = player.rank;
        if (rank == 1) {
            Glide.with(context).load(R.drawable.noonerank).into(rank_img);
        } else if (rank == 2) {
            Glide.with(context).load(R.drawable.notworank).into(rank_img);
        } else if (rank == 3) {
            Glide.with(context).load(R.drawable.nothreerank).into(rank_img);
        } else {
            rank_txt.setText(String.valueOf(rank));
        }


        nameTxt = holder.itemView.findViewById(R.id.commenter_name);
        nameTxt.setText(player.name);


        scoreTxt = holder.itemView.findViewById(R.id.score_txt);
        scoreTxt.setText("$" + player.bestScore);
        if (player.id.equals(thisPlayer.id)) {
            nameTxt.setTextColor(Color.MAGENTA);
            scoreTxt.setTextColor(Color.RED);
        }
        dataLike.child(player.id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TextView likeCount = holder.itemView.findViewById(R.id.likes_count);
                if ((int) snapshot.getChildrenCount() > 0) {
                    likeCount.setText(String.valueOf((int) snapshot.getChildrenCount()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        profilePic = holder.itemView.findViewById(R.id.player1_image);

        chat = holder.itemView.findViewById(R.id.chatfriend);
        Glide.with(context).load(player.picUri).placeholder(R.drawable.default_avatar).into(profilePic);

        likeBtn = holder.itemView.findViewById(R.id.like_btn);
        deleteThis = holder.itemView.findViewById(R.id.delete_this);
        badgeCrown = holder.itemView.findViewById(R.id.badge_crown);
        badgeBankrupt = holder.itemView.findViewById(R.id.badge_bankrupt);
        badgeScore = holder.itemView.findViewById(R.id.badge_score);
        badgeTopFan = holder.itemView.findViewById(R.id.badge_top_fan);
        badge_g = new ImageView[]{holder.itemView.findViewById(R.id.badge_g0), holder.itemView.findViewById(R.id.badge_g1), holder.itemView.findViewById(R.id.badge_g2)};
        badge_b = new ImageView[]{holder.itemView.findViewById(R.id.badge_b0), holder.itemView.findViewById(R.id.badge_b1), holder.itemView.findViewById(R.id.badge_b2)};

        //
        badgeScore.setVisibility(View.GONE);

        if (player.giftsList.isEmpty()) {
            for (ImageView c : badge_g) {
                c.setVisibility(View.GONE);
            }
        } else {
            loadbadge.giftVisibility(badge_g, player.giftsList);
            loadbadge.giftImage(player.giftsList, badge_g);
        }
        if (player.bonusList.isEmpty()) {
            for (ImageView c : badge_b) {
                c.setVisibility(View.GONE);
            }
        } else {
            loadbadge.bonusVisibility(badge_b, player.bonusList);
            loadbadge.bonusImages(player.bonusList, badge_b);
        }

        loadbadge.bankrupt(player.bankrupt, badgeBankrupt);
        loadbadge.crown(rank, badgeCrown);
        loadbadge.topFan(player.timesplayed, badgeTopFan);


        if (player.id.equals(thisPlayer.id)) {
            deleteThis.setVisibility(View.VISIBLE);
        }

        deleteThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Offline").child("september2021");
                databaseReference.child(player.id).removeValue();
                context.startActivity(new Intent(context, AndroidLauncher.class));
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataLike.child(player.id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (thisPlayer.logged) {
                            if (!snapshot.exists()) {
                                dataLike.child(player.id).child(thisPlayer.id).setValue(1);
                            } else {
                                if (!snapshot.hasChild(thisPlayer.id)) {
                                    dataLike.child(player.id).child(thisPlayer.id).setValue(1);
                                }
                            }
                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setMessage("Maaf, ciri khas ini hanya untuk pemain yang berdaftar menggunakan Facebook.");
                            alert.setTitle("Gagal");
                            alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        DatabaseReference dataComment = FirebaseDatabase.getInstance().getReference().child("Offline").child("Comment2022");
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (thisPlayer.logged) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    final EditText edittext = new EditText(context);
                    alert.setMessage("Tinggalkan Mesej:");
                    alert.setTitle(player.name);
                    alert.setView(edittext);
                    alert.setPositiveButton("Kirim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //What ever you want to do with the value
                            if (edittext.getText().length() > 0) {
                                DatabaseReference thisCommentDB = dataComment.child(player.id);
                                Comment thisComment = new Comment();
                                thisComment.setComment(edittext.getText().toString());
                                thisComment.setName(thisPlayer.name);
                                thisComment.setPicUri(thisPlayer.picUri);
                                thisComment.setCommentID(thisCommentDB.push().getKey());
                                thisComment.setPlayerWhoSendID(thisPlayer.id);
                                thisCommentDB.child(thisComment.getCommentID()).setValue(thisComment);
                            } else {
                                dialog.dismiss();
                            }

                        }
                    });

                    alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                            dialog.dismiss();
                        }
                    });

                    alert.show();
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setMessage("Maaf, ciri khas ini hanya untuk pemain yang berdaftar menggunakan Facebook.");
                    alert.setTitle("Gagal");
                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alert.show();
                }
            }
        });


        info = holder.itemView.findViewById(R.id.infobtn);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog d = new InfoUser(context, player);
                d.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                d.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerlist.size();
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