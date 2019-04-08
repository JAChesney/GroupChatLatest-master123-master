package com.groupchat.jasonchesney.groupchat;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class OnetoOneAdapter extends RecyclerView.Adapter<OnetoOneAdapter.OnetoOneViewHolder> {

    private List<OnetoOneMessage> userMessageList;
    private DatabaseReference gnameRef;
    private FirebaseAuth mAuth;
    private String gn, groupname;

    public OnetoOneAdapter (List<OnetoOneMessage> userMessageList){
        this.userMessageList = userMessageList;
    }

    public class OnetoOneViewHolder extends RecyclerView.ViewHolder{

        public TextView cname, cmessage, sname, smessage;

        public OnetoOneViewHolder(View itemView) {
            super(itemView);

            cname = (TextView) itemView.findViewById(R.id.chatname);
            cmessage = (TextView) itemView.findViewById(R.id.chatmessage);
            sname = (TextView) itemView.findViewById(R.id.chatnamesender);
            smessage = (TextView) itemView.findViewById(R.id.chatmsgsender);


        }
    }

    @NonNull
    @Override
    public OnetoOneViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.chat_msg_row, viewGroup, false);

        mAuth = FirebaseAuth.getInstance();

        return new OnetoOneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnetoOneViewHolder holder, int position) {

        String currentUserID = mAuth.getCurrentUser().getUid();
        OnetoOneMessage oneMessage = userMessageList.get(position);

        String name = oneMessage.getCname();
        String message = oneMessage.getMessage();
        String userid = oneMessage.getUserid();
        groupname = userid;

        holder.cname.setVisibility(View.INVISIBLE);
        holder.cmessage.setVisibility(View.INVISIBLE);

        if(userid.equals(currentUserID)){
            holder.smessage.setBackgroundResource(R.drawable.bubble2);
            holder.smessage.setTextColor(Color.BLACK);
            holder.smessage.setText(oneMessage.getMessage().trim());
            holder.sname.setText(oneMessage.getCname());
        }
        else{
            holder.cname.setVisibility(View.VISIBLE);
            holder.cmessage.setVisibility(View.VISIBLE);
            holder.sname.setVisibility(View.INVISIBLE);
            holder.smessage.setVisibility(View.INVISIBLE);

            holder.cmessage.setBackgroundResource(R.drawable.bubble1);
            holder.cmessage.setTextColor(Color.BLACK);
            holder.cmessage.setText(oneMessage.getMessage().trim());
            holder.cname.setText(oneMessage.getCname());

        }
    }

    @Override
    public int getItemCount() {
        return userMessageList.size();
    }


}
