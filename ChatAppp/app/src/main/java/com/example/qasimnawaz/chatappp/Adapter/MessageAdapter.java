package com.example.qasimnawaz.chatappp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qasimnawaz.chatappp.Modules.MessageModule;
import com.example.qasimnawaz.chatappp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Qasim Nawaz on 2/10/2017.
 */

public class MessageAdapter extends BaseAdapter {
    //    FirebaseDatabase database;
//    DatabaseReference myRef;
//    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ArrayList<MessageModule> list;
    Context mContext;
    LayoutInflater inflater;

    private String myUUID;


//    public MessageAdapter(ArrayList<MessageModule> list, Context con) {
//        this.list = list;
//        this.mContext = con;
//        inflater = LayoutInflater.from(con);
//    }


    public MessageAdapter(ArrayList<MessageModule> list, Context context, String myUUID) {
        this.list = list;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.myUUID = myUUID;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        TextView userMessage, userTime;
        Log.d("UUID", "My: " + list.get(position).getMessageUUID());
        Log.d("UUID", "Friend: " + myUUID);
        if (list.get(position).getMessageUUID().equals(myUUID)) {
            view = inflater.inflate(R.layout.message_right_item, null);
            userMessage = (TextView) view.findViewById(R.id.right_item_message);
            userTime = (TextView) view.findViewById(R.id.right_item_time);
        } else {
            view = inflater.inflate(R.layout.message_left_item, null);
            userMessage = (TextView) view.findViewById(R.id.left_item_message);
            userTime = (TextView) view.findViewById(R.id.left_item_time);
        }

        userMessage.setText(list.get(position).getMessageText());
        userTime.setText(list.get(position).getMessageTime());
        return view;
    }
}
