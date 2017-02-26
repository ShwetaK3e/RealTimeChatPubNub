package com.example.pervacio.chatapp.Pubnubs;

import android.util.Log;

import com.example.pervacio.chatapp.ChatAdapter;
import com.example.pervacio.chatapp.Model.MessagePojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

/**
 * Created by Pervacio on 2/1/2017.
 */

public class PubSubCallBack extends SubscribeCallback {
    ChatAdapter chatAdapter;

    public PubSubCallBack(ChatAdapter chatAdapter){
        this.chatAdapter=chatAdapter;
    }

    @Override
    public void status(PubNub pubnub, PNStatus status) {

    }

    @Override
    public void message(PubNub pubnub, PNMessageResult message) {
        try {
            Log.i("TAG","displayed");
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode obj= message.getMessage();
            MessagePojo msg=objectMapper.treeToValue(obj,MessagePojo.class);
            this.chatAdapter.add(msg);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void presence(PubNub pubnub, PNPresenceEventResult presence) {

    }
}
