package com.example.dockermessages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class MessageListViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<Message>> messageListLiveData = new MutableLiveData<>();

    public LiveData<ArrayList<Message>> getMessageList() {
        return messageListLiveData;
    }

    public MessageListViewModel() {
        final ArrayList<Message> messageList = new ArrayList<>();
        messageListLiveData.postValue(messageList);
    }

    void updateMessageList(ArrayList<Message> updateMessages) {
        messageListLiveData.postValue(updateMessages);
    }
}
