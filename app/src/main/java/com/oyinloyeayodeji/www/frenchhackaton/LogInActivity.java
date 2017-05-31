package com.oyinloyeayodeji.www.frenchhackaton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oyinloyeayodeji.www.frenchhackaton.Objects.Shiuser;
import com.oyinloyeayodeji.www.frenchhackaton.Managers.SharedPrefManager;

public class LogInActivity extends BaseActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button createUser;

    SharedPreferences sharedPreferences;

    DatabaseReference myRef,dbUsers;

    public static final String PREFS = "Restaurant";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        sharedPreferences = getSharedPreferences(PREFS,0);

        // Views
        mEmailField = (EditText) findViewById(R.id.field_phone_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);
        createUser = (Button)findViewById(R.id.go_to_create);
        // Buttons
        findViewById(R.id.log_in_button).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        myRef = FirebaseDatabase.getInstance().getReference("Userbase");
        dbUsers = myRef.child("dbusers");

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Toast.makeText(LogInActivity.this, "Signed in " + user.getEmail(), Toast.LENGTH_SHORT).show();
                            if(user!=null){
                                interceptSignIn(user);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LogInActivity.this, "Authentication failed. Please try again.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(LogInActivity.this,  R.string.auth_failed, Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    //User as a sign in use case when i was using the offline methodology

    private void interceptSignIn(FirebaseUser user) {
        final FirebaseUser currentUser = user;
        dbUsers.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Shiuser dbuser = dataSnapshot.getValue(Shiuser.class);
                redirectUser(dbuser,currentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void redirectUser(Shiuser extraData,FirebaseUser currentUser) {

        String mToken = SharedPrefManager.getmInstance(this).getToken();

        //update user token on user login
        if(!mToken.equals(extraData.getmToken())){
            extraData.setmToken(mToken);
            dbUsers.child(currentUser.getUid()).setValue(extraData);
            Toast.makeText(this, "Token Updated", Toast.LENGTH_SHORT).show();
        }

        //Update shared preferences on login
        SharedPrefManager.getmInstance(this).storeUser(extraData);
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString() + "@gmail.com";
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        }else if (email.length() < 9){
            mEmailField.setError("Incomplete.");
            valid = false;
        }else if (mEmailField.getText().toString().length() >  9){
            mEmailField.setError("Too many numbers.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.log_in_button) {
            signIn(mEmailField.getText().toString() + "@gmail.com", mPasswordField.getText().toString());
        }
    }
}
