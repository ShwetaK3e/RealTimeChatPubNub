package com.example.pervacio.chatapp;

import android.icu.text.AlphabeticIndex;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pervacio.chatapp.Model.MessagePojo;
import com.example.pervacio.chatapp.Pubnubs.PubSubCallBack;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    EditText newMsg;
    Button sendMsg;
    PubSubCallBack pubSubCallBack;
    ChatAdapter chatAdapter;
    PubNub pubNub;
    ListView list;
    MessagePojo msgpojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newMsg=(EditText)findViewById(R.id.newMsg);
        sendMsg=(Button)findViewById(R.id.send);
        list=(ListView)findViewById(R.id.messageList);

        chatAdapter=new ChatAdapter(this);
        msgpojo=new MessagePojo("Satyam","hi","00:00");
        chatAdapter.add(msgpojo);
        list.setAdapter(chatAdapter);
        pubSubCallBack=new PubSubCallBack(chatAdapter);

        initPubnub();
        initChannels();
    }

    void initPubnub(){
        PNConfiguration pnConfiguration=new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-71dcfe34-e6d2-11e6-889b-02ee2ddab7fe");
        pnConfiguration.setPublishKey("pub-c-bcd30f31-2a9c-4106-9f65-b60982f0d531");
        pnConfiguration.setUuid(getIntent().getStringExtra("userName"));
        pnConfiguration.setSecure(true);
        pubNub=new PubNub(pnConfiguration);
    }

    void initChannels(){
        pubNub.addListener(pubSubCallBack);
        pubNub.subscribe().channels(Arrays.asList("awesomeChannel")).withPresence().execute();
    }

    public void sendMessage(View v){
        final Map<String,String> message=new HashMap<String, String>(){{put("sender",getIntent().getStringExtra("userName"));}{put("message",newMsg.getText().toString());}{put("timestamp", DateTime.now(DateTimeZone.UTC).toString(ISODateTimeFormat.dateTime()));}};

        pubNub.publish().channel("awesomeChannel").message(message).async(new PNCallback<PNPublishResult>() {
            @Override
            public void onResponse(PNPublishResult result, PNStatus status) {
              if(status.isError()){
                  Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
              }else{
                  newMsg.setText("");
                  Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    void disconnectAndCleanup(){
        pubNub.unsubscribe().channels(Arrays.asList("awesomeChannel")).execute();
        pubNub.removeListener(pubSubCallBack);
        pubNub.stop();
        pubNub=null;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disconnectAndCleanup();
    }
}
