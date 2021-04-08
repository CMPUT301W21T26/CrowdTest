package com.example.crowdtest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdtest.Question;
import com.example.crowdtest.R;
import com.example.crowdtest.Reply;

import java.util.ArrayList;

public class ReplyListAdapter extends RecyclerView.Adapter<ReplyListAdapter.ViewHolder> {
    private ArrayList<Reply> replies;
    private String username;

    public ReplyListAdapter(ArrayList<Reply> replies, String username) {
        this.replies = replies;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View questionView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_content, parent, false);

        ViewHolder viewHolder = new ViewHolder(questionView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Reply reply = replies.get(position);

        viewHolder.getUsername().setText(reply.getCommenterID());
        viewHolder.getComment().setText(reply.getContent());

//        viewHolder.itemView.setOnClickListener(v -> {
//            Bundle questionDetailsBundle = new Bundle();
//        });
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.comment_content_username);
            comment = (TextView) itemView.findViewById(R.id.comment_content_reply);
        }

        public TextView getUsername() {
            return username;
        }

        public TextView getComment() {
            return comment;
        }
    }
}
