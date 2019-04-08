package com.groupchat.jasonchesney.groupchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class SelectedGroupRecyclerAdapter extends RecyclerView.Adapter<SelectedGroupRecyclerAdapter.ViewHolder> {


    private ArrayList<String> Memnames = new ArrayList<>();
    private Context mContext;
    private DatabaseReference rootRef, gnameRef, newgnameRef;
    private String changer, g, h;

    public SelectedGroupRecyclerAdapter(ArrayList<String> Memnames1, Context c1)
    {
        mContext = c1;
        Memnames = Memnames1;
    }


    @NonNull
    @Override
    public SelectedGroupRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.onlinerecycler,parent,false);

        ViewHolder vw=new ViewHolder(view);

        return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedGroupRecyclerAdapter.ViewHolder holder, final int position) {

        holder.t1.setText(Memnames.get(position));

        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, Memnames.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return Memnames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t1;

        public ViewHolder(View itemView) {
            super(itemView);

            t1=(TextView) itemView.findViewById(R.id.online_user_profile_name);
        }
    }
}
