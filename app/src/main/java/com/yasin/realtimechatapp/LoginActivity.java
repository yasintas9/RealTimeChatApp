package com.yasin.realtimechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText kullaniciadiedittext;
    Button login;
    ImageView imageView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tanimlamalar();
    }

    public void tanimlamalar(){
        kullaniciadiedittext=findViewById(R.id.username);
        login=findViewById(R.id.login);
        imageView=findViewById(R.id.imagee);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = kullaniciadiedittext.getText().toString();
                kullaniciadiedittext.setText("");
                addUserDatabase(user);
            }
        });
    }

    public void addUserDatabase(final String username){
          reference.child("Kullancılar").child(username).child("kullanıcıadi").setValue(username).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                  Toast.makeText(getApplicationContext(),"Başarı ile giriş yaptınız",Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                  intent.putExtra("username",username);
                  startActivity(intent);
              }}
          });
    }
}