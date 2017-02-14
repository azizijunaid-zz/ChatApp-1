package com.example.qasimnawaz.chatappp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qasimnawaz.chatappp.Modules.UserModule;
import com.example.qasimnawaz.chatappp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Qasim Nawaz on 2/1/2017.
 */

public class UserAdapter extends ArrayAdapter<UserModule> implements View.OnClickListener{
    private ArrayList<UserModule> dataSet;
    Context mContext;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;

    @Override
    public void onClick(View v) {

    }

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtEmail;
    }

    public UserAdapter(ArrayList<UserModule> data, Context context) {
        super(context, R.layout.item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    private int lastPosition = -1;

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserModule dataModel = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.messageTextView);
            viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.timeTextView);

            result = convertView;
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getUserName());
        viewHolder.txtEmail.setText(dataModel.getUserEmail());
        return convertView;
    }
}
