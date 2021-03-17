package com.yasin.realtimechatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    String userName,otherName;
    TextView chatUser;
    ImageView back;
    ImageView sendMessage;
    EditText sendText;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    List<MessageModel> mlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        definitions();
        loadMessage();
    }

    public void definitions(){
            firebaseDatabase = FirebaseDatabase.getInstance();
            reference=firebaseDatabase.getReference();
            mlist = new ArrayList<>();
            userName = getIntent().getExtras().getString("username");
            otherName=getIntent().getExtras().getString("othername");

            chatUser = findViewById(R.id.chatUser);
            back=findViewById(R.id.backImage);
            sendMessage=findViewById(R.id.sendMessage);
            sendText=findViewById(R.id.sendText);
            chatUser.setText(otherName);

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatActivity.this,MainActivity.class);
                    intent.putExtra("username",userName);
                    startActivity(intent);
                }
            });

            sendMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = sendText.getText().toString();
                    sendText.setText("");
                    sendMessagee(message);
                }
            });

            recyclerView = (RecyclerView)findViewById(R.id.chatRecylerView);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChatActivity.this,1);
            recyclerView.setLayoutManager(layoutManager);
            messageAdapter = new MessageAdapter(ChatActivity.this,mlist,ChatActivity.this,userName);
            recyclerView.setAdapter(messageAdapter);
    }

    public void sendMessagee(String text){

        String key = reference.child("Messages").child(userName).child(otherName).push().getKey(); //  id assigns to messages
        Map messageMap = new HashMap();
        messageMap.put("text",text);
        messageMap.put("from",userName);
        reference.child("Messages").child(userName).child(otherName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    reference.child("Messages").child(otherName).child(userName).child(key).setValue(messageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }
            }
        });


    }


    public void loadMessage(){
        reference.child("Messages").child(userName).child(otherName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                MessageModel messageModel = snapshot.getValue(MessageModel.class);
                                mlist.add(messageModel);
                                messageAdapter.notifyDataSetChanged(); // güncelleme işlemi
                                recyclerView.scrollToPosition(mlist.size()-1); // scroll the message
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