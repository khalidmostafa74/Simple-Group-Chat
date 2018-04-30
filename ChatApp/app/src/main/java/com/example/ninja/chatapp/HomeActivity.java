package com.example.ninja.chatapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText msgtxt ;
    ImageView snd;
    ListView lv;
    // Write a message to the database
    ArrayList<Message> messageArrayList;
    FirebaseDatabase database ;
    DatabaseReference myRef;
    ArrayList<Message> arrayList = new ArrayList<>();
    FirebaseUser cur_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.conv_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Conversation Room");
        lv = findViewById(R.id.list_item);
        mAuth = FirebaseAuth.getInstance();
        msgtxt = findViewById(R.id.msg);
        cur_user = mAuth.getCurrentUser();
        snd = findViewById(R.id.snd);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("messages");
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        final CustomAdapter adapter = new CustomAdapter(arrayList,this);
        lv.setAdapter(adapter);
        snd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa");
                String datetime = dateformat.format(c.getTime());
                String msgContent = msgtxt.getText().toString();
                Random rand = new Random();
                int  n = rand.nextInt(50) + 1;
                HashMap<String,String>map = new HashMap<>();
                map.put("sender",cur_user.getEmail());
                map.put("content",msgContent);
                myRef.child(datetime+"").setValue(map);
                msgtxt.setText("");
            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message message = dataSnapshot.getValue(Message.class);
                arrayList.add(new Message(message.getSender(),message.getContent()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:mAuth.signOut();goToMain();break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(HomeActivity.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
