package com.example.dockermessages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder> {

    private ArrayList<Message> localDataSet;

    public MessageRecyclerAdapter(ArrayList<Message> data) {
        localDataSet = data;
    }

    public void addItem(Message m) {
        localDataSet.add(m);
        notifyItemChanged(localDataSet.size()-1);
    }

    public void updateItems(ArrayList<Message> messages) {
        localDataSet = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String senderText = localDataSet.get(position).getSender();
        String messageText = localDataSet.get(position).getMsgText();
        holder.messageTextView.setText(String.format("%s: %s", senderText, messageText));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageTextView;

        public ViewHolder(View view) {
            super(view);
            messageTextView = (TextView) view.findViewById(R.id.itemText);
        }

        public TextView getTextView() {
            return messageTextView;
        }


    }
}
