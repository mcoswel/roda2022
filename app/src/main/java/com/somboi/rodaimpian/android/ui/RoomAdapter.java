package com.somboi.rodaimpian.android.ui;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.somboi.rodaimpian.android.PlayerOnline;
import com.somboi.rodaimpian.android.RoomInterface;
import com.somboi.rodaimpian.android.Rooms;
import com.somboi.rodaimpian.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Rooms> roomsList;
    private final PlayerOnline thisPlayer;
    private final DatabaseReference roomsDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("rooms2022");
    private final RoomInterface roomInterface;
    public RoomAdapter(Context context, PlayerOnline player, List<Rooms> roomsList, RoomInterface roomInterface) {
        this.context = context;
        this.thisPlayer = player;
        this.roomsList = roomsList;
        this.roomInterface = roomInterface;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.roomadapter, parent, false);
        RoomAdapter.MyViewHolder vh = new RoomAdapter.MyViewHolder(v);
        return vh;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View v = holder.itemView;
        Rooms rooms = roomsList.get(position);
        TextView roomTitle = v.findViewById(R.id.room_title);
        roomTitle.setText(context.getString(R.string.bilik)+" "+rooms.getHostPlayer().name);
        CircleImageView hostImg = v.findViewById(R.id.hostplayer_img);
        if (rooms.getHostPlayer().picUri!=null){
           Glide.with(context).load(rooms.getHostPlayer().picUri).into(hostImg);
        }
        TextView hostPlayerName = v.findViewById(R.id.hostplayer_name);
        hostPlayerName.setText(context.getString(R.string.host) + " " + rooms.getHostPlayer().name);

        TextView hostPlayerStatus = v.findViewById(R.id.hostplayer_status);


        CircleImageView playerOneImg = v.findViewById(R.id.playerone_img);

        TextView playerOneName = v.findViewById(R.id.playerone_name);
        TextView playerOneStatus = v.findViewById(R.id.playerone_status);
        if (rooms.getPlayer_one() != null) {
            playerOneName.setText(rooms.getPlayer_one().name);
            playerOneStatus.setText(context.getString(R.string.waitinghos));
            if (rooms.getPlayer_one().picUri!=null){
                Glide.with(context).load(rooms.getPlayer_one().picUri).into(playerOneImg);
            }
        }
        CircleImageView playeTwoImg = v.findViewById(R.id.playertwo_img);

        TextView playerTwoName = v.findViewById(R.id.playertwo_name);
        TextView playerTwoStatus = v.findViewById(R.id.playertwo_status);
        if (rooms.getPlayer_two() != null) {
            playerTwoName.setText(rooms.getPlayer_two().name);
            playerTwoStatus.setText(context.getString(R.string.waitinghos));
            if (rooms.getPlayer_two().picUri!=null){
                Glide.with(context).load(rooms.getPlayer_two().picUri).into(playeTwoImg);
            }
        }
        Button start = v.findViewById(R.id.room_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rooms.setStart(true);
                roomsDatabase.child(rooms.getId()).setValue(rooms);
            }
        });
        Button delete = v.findViewById(R.id.room_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomsDatabase.child(rooms.getId()).removeValue();
            }
        });
        Button join = v.findViewById(R.id.room_join);
        Button cancel = v.findViewById(R.id.room_out);


        if (rooms.getHostPlayer().id.equals(thisPlayer.id)) {
            if (rooms.isStart()){
                roomInterface.startRodaOnline(rooms);
            }
            v.findViewById(R.id.host_button).setVisibility(View.VISIBLE);
            if (rooms.getPlayer_one() != null || rooms.getPlayer_two() != null) {
                start.setVisibility(View.VISIBLE);
            } else {
                start.setVisibility(View.GONE);
            }
        } else if (rooms.getPlayer_one() != null) {
            if (rooms.getPlayer_one().id.equals(thisPlayer.id)) {
                if (rooms.isStart()){
                    roomInterface.startRodaOnline(rooms);
                }
                cancel.setVisibility(View.VISIBLE);
            }
        } else if (rooms.getPlayer_two() != null) {
            if (rooms.getPlayer_two().id.equals(thisPlayer.id)) {
                if (rooms.isStart()){
                    roomInterface.startRodaOnline(rooms);
                }
                cancel.setVisibility(View.VISIBLE);
            }
        } else if (rooms.getPlayer_one() == null || rooms.getPlayer_two() == null) {
            join.setVisibility(View.VISIBLE);
        }


        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomsDatabase.child(thisPlayer.id).removeValue();
                if (rooms.getPlayer_one()==null){
                    rooms.setPlayer_one(thisPlayer);
                }else{
                    rooms.setPlayer_two(thisPlayer);
                }
               roomsDatabase.child(rooms.getId()).setValue(rooms);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomsList.size();
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

