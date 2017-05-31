package com.oyinloyeayodeji.www.frenchhackaton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oyinloyeayodeji.www.frenchhackaton.Managers.SharedPrefManager;
import com.oyinloyeayodeji.www.frenchhackaton.Objects.Shiuser;
import com.oyinloyeayodeji.www.frenchhackaton.Objects.Shiuser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ayo on 14/05/2017.
 */

public class TabChat extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,
                        userRoot,
                        allUsers,
                        allChats;

    private FirebaseListAdapter<Shiuser> adapter;

    SharedPrefManager sharedPref;

    ArrayList<String> chatIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout rootView = (RelativeLayout)inflater.inflate(R.layout.tab_chat, container, false);
        showProgressDialog();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        userRoot = databaseReference.child("Userbase");
        allUsers = userRoot.child("dbusers");
        allChats = userRoot.child("dbchats");

//        allChats.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Toast.makeText(getContext(), " " + dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();
////                chatIds.add(dataSnapshot.getKey());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        sharedPref = SharedPrefManager.getmInstance(getContext());

        final ListView listOfMessages = (ListView)rootView.findViewById(R.id.list_of_users);

        adapter = new FirebaseListAdapter<Shiuser>(getActivity(),Shiuser.class,R.layout.user,allUsers) {
            @Override
            protected void populateView(View v, Shiuser model, int position) {
                // Get references to the views of message.xml

//                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                ImageView userImage = (ImageView)v.findViewById(R.id.user_image);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);
                TextView messageBio = (TextView)v.findViewById(R.id.short_bio);
                    // Set their text
                    Picasso.with(getContext())
                            .load(model.getmImageUrl())
                            .resize(300,300)
                            .centerInside()
                            .transform(new CircleTransform())
                            .into(userImage);

                    messageTime.setText("+233 - " + model.getmEmail().replace("@gmail.com",""));
                    messageUser.setText(model.getmFirstname() + " " + model.getmLastname());
                    messageBio.setText(model.getmNetwork());
                hideProgressDialog();

                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);

        listOfMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shiuser theUser = adapter.getItem(i);
                String chatReference = theUser.getmEmail().replace("@gmail.com","")
                        +theUser.getmFirstname()
                        +sharedPref.getUser().getmEmail().replace("@gmail.com","")
                        +sharedPref.getUser().getmFirstname();
                char[] chatArray = chatReference.toCharArray();
                Arrays.sort(chatArray);
                String newChatRef = "";
                for (Character c : chatArray)
                    newChatRef += c.toString();
//                Toast.makeText(getContext(), newChatRef, Toast.LENGTH_LONG).show();
                Intent chatIntent = new Intent(getContext(),ChatRoomActivity.class);
                chatIntent.putExtra("chatId",newChatRef);
                startActivity(chatIntent);
//
            }
        });

        return rootView;
    }

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
