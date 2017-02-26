package com.example.pervacio.chatapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pervacio.chatapp.Model.MessagePojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pervacio on 2/1/2017.
 */

public class ChatAdapter extends ArrayAdapter<MessagePojo> {

    List<MessagePojo> chats=new ArrayList<>();
    Context context;

    public ChatAdapter(Context context) {
        super(context,R.layout.chat_row);
        this.context=context;
    }

    @Override
    public void add(MessagePojo object) {
        this.chats.add(0,object);
        ((Activity)this.context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    class Message {
       private TextView sender;
       private TextView message;
       private TextView timestamp;
   }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessagePojo msgPojo=(MessagePojo)this.chats.get(position);
        Message msg;
        if(convertView==null){
            msg=new Message();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.chat_row,parent,false);
            msg.sender=(TextView)convertView.findViewById(R.id.senderName);
            msg.timestamp=(TextView)convertView.findViewById(R.id.timeStamp);
            msg.message=(TextView)convertView.findViewById(R.id.message);
            convertView.setTag(msg);
        }else{
            msg=(Message)convertView.getTag();
        }

        msg.sender.setText(msgPojo.getSender());
        msg.message.setText(msgPojo.getMessage());
        msg.timestamp.setText(msgPojo.getTimestamp());
      return convertView;
    }


    @Override
    public int getCount() {
        return this.chats.size();
    }

    public void clear() {
        this.chats.clear();
        notifyDataSetChanged();
    }
}
