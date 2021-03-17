package com.yasin.realtimechatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    List<String> userList;
    String userName;
    RecyclerView userRecy;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defines();
        list();
    }

    public void defines(){

        userName = getIntent().getExtras().getString("username");
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
        userList = new ArrayList<>();
        userRecy = (RecyclerView)findViewById(R.id.userRecyler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this,3); // 2 kullanıcı listelensin (spancount)
        userRecy.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(MainActivity.this,userList,MainActivity.this,this.userName);
        userRecy.setAdapter(userAdapter);
    }

    public void list(){
        reference.child("Kullancılar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(!snapshot.getKey().equals(userName)){
                 userList.add(snapshot.getKey());
                 userAdapter.notifyDataSetChanged(); // adaptör güncelleme
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}