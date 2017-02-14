package com.example.qasimnawaz.chatappp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.qasimnawaz.chatappp.Adapter.MessageAdapter;
import com.example.qasimnawaz.chatappp.Modules.ConversationModule;
import com.example.qasimnawaz.chatappp.Modules.MessageModule;
import com.example.qasimnawaz.chatappp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseStorage storage;
    StorageReference storageRef;

    EditText mMessageEditText;
    //    Button sendButton;
    ImageView sendButton;
    String curentUUID;
    String friendUUID;

    private ArrayList<MessageModule> arrayList;
    MessageAdapter adapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        arrayList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://fir-singlevalue3-d5c4a.appspot.com");


        sendButton = (ImageView) findViewById(R.id.sendButton);
        curentUUID = mAuth.getCurrentUser().getUid();

//        arrayList = new ArrayList<MessageModule>();
//        adapter = new MessageAdapter(this, arrayList);
//        listView = (ListView) findViewById(R.id.messageListView);
//        listView.setAdapter(adapter);


//        if (mMessageEditText.getText().length()<1){
//            sendButton.setEnabled(false);
//        }else {
//            sendButton.setEnabled(true);
//        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessages();
            }
        });
        showMessagesList();
        itemsListView();
    }

    private void itemsListView() {
        adapter = new MessageAdapter(arrayList, ChatActivity.this, curentUUID);
        listView = (ListView) findViewById(R.id.messageListView);
        listView.setAdapter(adapter);
    }

    private void sendMessages() {
        myRef.child("User-conversation").child(curentUUID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ConversationModule module = dataSnapshot.getValue(ConversationModule.class);
                String pushKey = module.getPushKey();
//                friendUUID = module.getUuid().toString();
//                String loginUUID = mAuth.getCurrentUser().getUid();
                mMessageEditText = (EditText) findViewById(R.id.messageEditText);
                String message = mMessageEditText.getText().toString();
                Log.d("Message", "abc: " + message);
                String d = String.valueOf(System.currentTimeMillis());
                MessageModule messageModule = new MessageModule(message, curentUUID, d);
                myRef.child("Conversation").child(pushKey).push().setValue(messageModule);
                mMessageEditText.setText(null);
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

    private void showMessagesList() {
        myRef.child("User-conversation").child(curentUUID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final ConversationModule module = dataSnapshot.getValue(ConversationModule.class);
                String pushKey = module.getPushKey();
//                friendUUID = module.getUuid().toString();
                myRef.child("Conversation").child(pushKey).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        MessageModule module1 = dataSnapshot.getValue(MessageModule.class);
                        long yourmilliseconds = Long.parseLong(module1.getMessageTime());
                        SimpleDateFormat sdf = new SimpleDateFormat("EEE/d hh:mm aaa");
                        Time resultTime = new Time(yourmilliseconds);
                        Log.d("Time Milisecond: ", "" + yourmilliseconds);
                        Log.d("Time Milisecond: ", "" + sdf.format(resultTime));
                        String t = sdf.format(resultTime);
                        MessageModule newMessage = new MessageModule(module1.getMessageText(), t);
                        arrayList.add(new MessageModule(module1.getMessageText(), module1.getMessageUUID(), t));
//                        arrayList.add(new MessageModule(newMessage.getMessageText(), newMessage.getMessageUUID(), newMessage.getMessageTime()));
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
}
