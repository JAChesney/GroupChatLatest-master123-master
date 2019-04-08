package com.groupchat.jasonchesney.groupchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;

public class EditGroupRecyclerView extends RecyclerView.Adapter<EditGroupRecyclerView.ViewHolder> {



    private ArrayList<String> Gnames = new ArrayList<>();
    private ArrayList<String> Gnames12 = new ArrayList<>();
    private Context mContext;
    private DatabaseReference rootRef, gnameRef, newgnameRef;
    private String changer, g, h;



  public EditGroupRecyclerView(ArrayList<String> Gnames1, ArrayList<String> Gnames11, Context c1)
  {
         mContext=c1;
         Gnames=Gnames1;
         Gnames12=Gnames11;
  }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.editgroupnamecard,parent,false);
         ViewHolder vw=new ViewHolder(view);


      return vw;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

       holder.t1.setText(Gnames.get(position));
       holder.t2.setText(Gnames12.get(position));

       holder.t1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(mContext, Gnames.get(position), Toast.LENGTH_LONG).show();
          }
      });

      holder.notacc.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              rootRef = FirebaseDatabase.getInstance().getReference().child("Admin")
                      .child("EditGroup").child(Gnames.get(position));

              rootRef.removeValue();

              Gnames.remove(position);

              Gnames12.remove(position);

              notifyItemRemoved(position);

              notifyItemRangeChanged(position,Gnames.size());

              Toast.makeText(mContext, "Deleted", Toast.LENGTH_SHORT).show();
          }
      });

      holder.acc.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              gnameRef = FirebaseDatabase.getInstance().getReference().child("Admin")
                      .child("EditGroup").child(Gnames.get(position));

              gnameRef.removeValue();

              changeGroupName(Gnames.get(position),Gnames12.get(position));

              Gnames.remove(position);
          }
      });
  }

    //changing group name

    public void changeGroupName(String old, String new1)
    {


        final DatabaseReference r1=FirebaseDatabase.getInstance().getReference().child("Groups").child(old);
        final DatabaseReference r2=FirebaseDatabase.getInstance().getReference().child("Groups").child(new1);

        h = new1;
        g = old;
        final DatabaseReference r3 = FirebaseDatabase.getInstance().getReference().child("GroupOwner");

        r3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    String x = ds.getKey();
                    String y = ds.child("gtitle").getValue().toString();
                    if(y.equals(g)){
                        r3.child(x).child("gtitle").setValue(h);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        r1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 changer = dataSnapshot.child("group_id").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        r1.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                r2.setValue(dataSnapshot.getValue(),new DatabaseReference.CompletionListener(){

                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if(databaseError!=null)
                        {
                            Toast.makeText(mContext,"failed",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(mContext,"Changed",Toast.LENGTH_LONG).show();

                            FirebaseDatabase.getInstance().getReference().child("GroupId").child(changer).setValue(h);

                            r1.removeValue();

                            r2.child("grouptitle").setValue(h);
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return Gnames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

      TextView t1,t2;
      LinearLayout notacc, acc;

      public ViewHolder(View itemView) {
          super(itemView);
         t1=(TextView) itemView.findViewById(R.id.t1);
         t2=(TextView) itemView.findViewById(R.id.t2);
         notacc = (LinearLayout) itemView.findViewById(R.id.delthis);
         acc = (LinearLayout) itemView.findViewById(R.id.accthis);
      }
  }
}
