package com.groupchat.jasonchesney.groupchat;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditGroupActivity extends AppCompatActivity {
    private RecyclerView r1;
    Toolbar mtoolbar;
    AppBarLayout apb;
    private EditGroupRecyclerView r2;
    private DataSnapshot ds;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private DatabaseReference rootRef, memberref, gRef, userRef, gnameRef, editRef;

    private String currentUserID, userProfileimage, currentUserName, newGroupName, randfetch, randfetch1, memtype;
    private String MEMBER_TYPE = "Admin";
    private ArrayList<String> a1= new ArrayList<>();
    private ArrayList<String> a2= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        DatabaseReference ref45 = FirebaseDatabase.getInstance().getReference().child("Admin");

        ref45.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Toast.makeText(MainpageActivity.this,"in ds",Toast.LENGTH_LONG).show();
                ds = dataSnapshot;

                //check pending requests
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {

        a1.clear();
        a2.clear();

        for (DataSnapshot datasnap : ds.child("EditGroup").getChildren()) {

            a1.add(datasnap.getKey());
            a2.add((String) datasnap.getValue());
        }

        r1 = findViewById(R.id.sremainlist);

        r2 = new EditGroupRecyclerView(a1,a2,this);
        r1.setAdapter(r2);
        r1.setLayoutManager(new LinearLayoutManager(this));
    }
}