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

import java.util.ArrayList;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.ViewHolder> {
    private ArrayList<Question> questions;
    private String username;

    public QuestionListAdapter(ArrayList<Question> questions, String username) {
        this.questions = questions;
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
        Question question = questions.get(position);

        viewHolder.getUsername().setText(question.getCommenterID());
        viewHolder.getComment().setText(question.getContent());

        viewHolder.itemView.setOnClickListener(v -> {
//            Bundle questionDetailsBundle = new Bundle();
//            questionDetailsBundle.putSerializable();

            Intent questionActivityIntent = new Intent(v.getContext(), QuestionThread.class);
//            questionActivityIntent.putExtras(questionDetailsBundle);
            questionActivityIntent.putExtra("username", username);
            questionActivityIntent.putExtra("question", question);
            v.getContext().startActivity(questionActivityIntent);
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
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
