package com.somboi.ui;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.somboi.gdx.entities.Player;
import com.somboi.rodaimpian.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final List<Rooms> roomsList;
    private final Player thisPlayer;
    private final DatabaseReference chatDatabase = FirebaseDatabase.getInstance().getReference().getDatabase().getReference().child("Online").child("chats");

    public RoomAdapter(Context context, Player player, List<Rooms> roomsList) {
        this.context = context;
        this.thisPlayer = player;
        this.roomsList = roomsList;
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
        CircleImageView hostImg = v.findViewById(R.id.hostplayer_img);
        TextView hostPlayerName = v.findViewById(R.id.hostplayer_name);
        TextView hostPlayerStatus = v.findViewById(R.id.hostplayer_status);
        CircleImageView playerOneImg = v.findViewById(R.id.playerone_img);
        TextView playerOneName = v.findViewById(R.id.playerone_name);
        TextView playerOneStatus = v.findViewById(R.id.playerone_status);
        CircleImageView playeTwoImg = v.findViewById(R.id.playertwo_img);
        TextView playerTwoName = v.findViewById(R.id.playertwo_name);
        TextView playerTwoStatus = v.findViewById(R.id.playertwo_status);

        Button start = v.findViewById(R.id.room_start);
        Button delete = v.findViewById(R.id.room_delete);
        Button join = v.findViewById(R.id.room_join);
        Button cancel = v.findViewById(R.id.room_out);


        if (rooms.getHostPlayer().equals(thisPlayer)){
            v.findViewById(R.id.host_button).setVisibility(View.VISIBLE);
            if (rooms.getPlayer_one()!=null || rooms.getPlayer_two()!=null){
                start.setVisibility(View.VISIBLE);
            }else{
                start.setVisibility(View.GONE);
            }
        }else if (rooms.getPlayer_one().equals(thisPlayer) || rooms.getPlayer_two().equals(thisPlayer)){
            cancel.setVisibility(View.VISIBLE);
        }else if (rooms.getPlayer_one()==null || rooms.getPlayer_two() == null){
            join.setVisibility(View.VISIBLE);
        }



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

