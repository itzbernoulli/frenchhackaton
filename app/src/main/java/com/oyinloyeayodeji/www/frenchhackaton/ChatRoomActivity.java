package com.oyinloyeayodeji.www.frenchhackaton;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oyinloyeayodeji.www.frenchhackaton.Managers.SharedPrefManager;
import com.oyinloyeayodeji.www.frenchhackaton.Objects.ChatMessage;

public class ChatRoomActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,
            userRoot,
            allUsers,
            allChats,
            twoWayChatRef;

    ListView listOfMessages;
    EditText input;
    String chatId;
    private FirebaseListAdapter<ChatMessage> adapter;

    SharedPrefManager sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        userRoot = databaseReference.child("Userbase");
        allUsers = userRoot.child("dbusers");
        allChats = userRoot.child("dbchats");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.get("chatId")!= null){
                chatId = extras.get("chatId").toString();
            }
        }

        twoWayChatRef = allChats.child(chatId);
        listOfMessages = (ListView)findViewById(R.id.list_of_messages);
        input = (EditText)findViewById(R.id.input);
        sharedPref = SharedPrefManager.getmInstance(this);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                twoWayChatRef.push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                sharedPref.getUser().getmFirstname() + " " + sharedPref.getUser().getmLastname())
                        );

                // Clear the input
                input.setText("");
            }
        });

        displayChatMessages();
    }

    private void displayChatMessages() {
        adapter = new FirebaseListAdapter<ChatMessage>(ChatRoomActivity.this,ChatMessage.class,R.layout.message, twoWayChatRef) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }

}
