package com.example.dockermessages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private MessageClient messageClient;
    private MessageListViewModel messageListViewModel;
    private MessageRecyclerAdapter messageRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageClient = new MessageClient(this);
        initRecyclerView();
        initViewModel();
        initButtonListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchMessages();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (messageClient != null) {
            messageClient.cancelRequests();
        }
    }

    private void initRecyclerView() {
        messageRecyclerAdapter = new MessageRecyclerAdapter(new ArrayList<>());
        RecyclerView messageRecycler = findViewById(R.id.messageRecyclerView);
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        messageRecycler.setAdapter(messageRecyclerAdapter);
    }

    private void initViewModel() {
        messageListViewModel = new ViewModelProvider(this).get(MessageListViewModel.class);
        messageListViewModel.getMessageList().observe(this, messageList -> {
            if (!messageList.isEmpty()) {
                messageRecyclerAdapter.updateItems(messageList);
            }
        });
    }

    private void initButtonListener() {
        final EditText senderEditText = findViewById(R.id.editTextSender);
        final EditText messageEditText = findViewById(R.id.editTextMessage);
        findViewById(R.id.button).setOnClickListener(view -> {
            final String sender = senderEditText.getText().toString();
            final String message = messageEditText.getText().toString();
            final Message m = new Message("0", sender, message);
            try {
                messageClient.sendMessage(
                        m,
                        response -> Log.d(TAG, response.toString()),
                        error -> Log.e(TAG, error.getMessage()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            messageEditText.setText("");
        });
    }

    private void fetchMessages() {
        messageClient.getMessagesJSON(response -> {
            ArrayList<Message> messages = new ArrayList<>();
            for (int i=0; i<response.length(); i++) {
                try {
                    JSONObject messageJSON = response.getJSONObject(i);
                    messages.add(new Message(
                            "0",
                            messageJSON.getString("sender"),
                            messageJSON.getString("msgText")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            messageListViewModel.updateMessageList(messages);
        }, error -> Log.e(TAG, error.getMessage()));
    }
}