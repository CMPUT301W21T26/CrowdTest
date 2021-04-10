package com.example.crowdtest.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crowdtest.CommentManager;
import com.example.crowdtest.Question;
import com.example.crowdtest.R;
import com.example.crowdtest.Reply;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class QuestionThread extends AppCompatActivity {
    TextView username;
    TextView questionContent;
    RecyclerView rvReplies;
    RecyclerView.LayoutManager layoutManager;
    Button postReply;
    EditText replyEditText;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CommentManager commentManager = CommentManager.getInstance();
    Question question;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.question_thread);

        Bundle questionBundle = getIntent().getExtras();
        question = (Question) questionBundle.getSerializable("question");
        String currentUser = questionBundle.getString("username");

        rvReplies = (RecyclerView) findViewById(R.id.question_thread_replies_recyclerView);
        layoutManager = new LinearLayoutManager(this);
        rvReplies.setLayoutManager(layoutManager);
        ReplyListAdapter replyListAdapter = new ReplyListAdapter(question.getReplies(), currentUser);
        rvReplies.setAdapter(replyListAdapter);

        CollectionReference replyCollectionReference = db.collection("Questions/" + question.getCommentID() + "/replies");
        ArrayList<Long> replyCollectionSize = new ArrayList<>();
        replyCollectionReference.addSnapshotListener((value, error) ->
        {
            question.getReplies().clear();
            for (QueryDocumentSnapshot document : value) {
                if (document.getId().equals("size")) {
                    replyCollectionSize.add((Long) document.getData().get("value"));
                } else {
                    Reply reply = commentManager.getReply(document);
                    question.addReply(reply);
                }

                replyListAdapter.notifyDataSetChanged();
            }
        });

        username = findViewById(R.id.question_thread_username);
        username.setText(question.getCommenterID());

        questionContent = findViewById(R.id.question_thread_question);
        questionContent.setText(question.getContent());

        replyEditText = findViewById(R.id.question_thread_reply_editText);

        postReply = findViewById(R.id.question_thread_post_button);
        postReply.setOnClickListener(v -> {
            String comment = replyEditText.getText().toString();
            replyEditText.setText("");
            commentManager.postReply(question.getCommentID(), currentUser, comment, replyCollectionSize.get(0));
            replyCollectionSize.set(0, replyCollectionSize.get(0) + 1);
        });
    }
}
