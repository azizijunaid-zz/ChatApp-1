package com.example.qasimnawaz.chatappp.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.qasimnawaz.chatappp.Adapter.UserAdapter;
import com.example.qasimnawaz.chatappp.Modules.ConversationModule;
import com.example.qasimnawaz.chatappp.Modules.UserModule;
import com.example.qasimnawaz.chatappp.R;
import com.example.qasimnawaz.chatappp.activities.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    ArrayList<UserModule> dataModels;
    ListView listView;
    private static UserAdapter adapter;
    String friendUUID;
    String myUUID;
    boolean isConversationOld = false;
    ProgressDialog pd;
    ConversationModule conversationData;
    Intent intent;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(getContext());
        pd.setTitle("Loading...");
        pd.setMessage("Please wait...");
        pd.setCancelable(false);


        listView = (ListView) view.findViewById(R.id.list);
        dataModels = new ArrayList<>();
        intent = new Intent(getContext(), ChatActivity.class);


        myRef.child("Users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserModule userModule = dataSnapshot.getValue(UserModule.class);
                dataModels.add(new UserModule(userModule.getUserUUid(), userModule.getUserName(), userModule.getUserEmail()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter = new UserAdapter(dataModels, getContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final UserModule model = dataModels.get(position);
                Snackbar.make(view, model.getUserName() + "/" + model.getUserEmail(), Snackbar.LENGTH_SHORT)
                        .setAction("No Action", null).show();

                pd.show();
                myUUID = mAuth.getCurrentUser().getUid();
                friendUUID = model.getUserUUid();
//                MessageAdapter messageAdapter = new MessageAdapter(null, null, myUUID);
                checkConversationStatus(friendUUID, myUUID);
//                MessageAdapter adapter = new MessageAdapter(friendUUID, myUUID);

            }
        });
        return view;
    }

    private void checkConversationStatus(final String friendUUID, String myUUID) {


        myRef.child("User-conversation").child(myUUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    try {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ConversationModule module = snapshot.getValue(ConversationModule.class);
                            if (module.getUuid().equals(friendUUID)) {
                                Log.d("BBB", "Module: " + module.getUuid());
                                conversationData = module;
                                isConversationOld = true;
                                return;
                            }
                        }
                    } catch (Exception ec) {
                        ec.printStackTrace();
                    } finally {
                        getConvoDataOrCreateNew();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        myRef.child("User-conversation").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot != null) {
//                    Log.d("AAA", "d values: " + dataSnapshot.getValue().toString());
//                    Log.d("AAA", "key: " + dataSnapshot.getKey());
//                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                        Log.d("AAA", "childData: " + dataSnapshot1.getValue().toString());
//                        ConversationModule module = dataSnapshot1.getValue(ConversationModule.class);
//                        Log.d("AAA", "Child Friend's uuid: " + module.getUuid());
//                        if (module.getUuid().equals(friendUUID)) {
//                            isConversationOld = true;
//                            pd.dismiss();
//                            return;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

    private void getConvoDataOrCreateNew() {
        if (isConversationOld) {
            getConversationData();
        } else {
            createNewConversation();
        }
    }

    private void createNewConversation() {
        String s = myRef.push().getKey();
//        myRef.child(myUUID).push().setValue(new ConversationModule(s, friendUUID));
//        myRef.child(friendUUID).push().setValue(new ConversationModule(s, myUUID));
        intent.putExtra("value", "" + s);
//        getConversationData();
        final ConversationModule tempObj = new ConversationModule(s, myUUID);
        myRef.child("User-conversation").child(friendUUID).push().setValue(tempObj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    tempObj.setUuid(friendUUID);
                    myRef.child("User-conversation").child(myUUID).push().setValue(tempObj, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                getConversationData();
                            }
                        }
                    });
                }
            }
        });
//        final String pushKEY = myRef.push().getKey();
//        Log.d("CCC", "pushKey: "+pushKEY);
//        final DatabaseReference child = myRef.child(Utils.USER_CONVERSATION_NODE);
//        child.child(myUUID).push().setValue(new ConversationModule(conversationPushRef, friendUUID));
//        child.child(friendUUID).push().setValue(new ConversationModule(conversationPushRef, myUUID));
//        module2 = new ConversationModule(conversationPushRef, myUUID);
//        myRef.child("User-conversation").child(friendUUID).push().setValue(module2);
//        Toast.makeText(getContext(), "New user", Toast.LENGTH_SHORT).show();
//        pd.dismiss();
//        isConversationOld = false;
    }

    private void getConversationData() {
        startActivity(intent);
        isConversationOld = false;
        pd.dismiss();
    }

}
