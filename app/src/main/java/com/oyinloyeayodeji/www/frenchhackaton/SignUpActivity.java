package com.oyinloyeayodeji.www.frenchhackaton;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.oyinloyeayodeji.www.frenchhackaton.Objects.Shiuser;
import com.oyinloyeayodeji.www.frenchhackaton.Managers.SharedPrefManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignUpActivity extends BaseActivity {

    private String[] roles = {
            "MTN",
            "TIGO",
            "AIRTEL",
            "VODAFONE"
    };


    private static final String TAG = "CREATEACTIVITY";
    FirebaseAuth mAuth;
    FirebaseUser dbuser;

    private AppCompatSpinner mSpinnerSlide;

    EditText mPhoneNumber,
            mPassword,
            mFirstname,
            mLastname;

    TextView mImageName;

    Button mCreateUser, mBrowseImages, mUpload;

    private ImageView mImageView;

    private StorageReference mStorageRef;

    DatabaseReference myRef, dbUsers;
    FirebaseDatabase database;

    private Uri imgUri;

//    String phoneNumber = "";
//    String password = "";

    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        dbuser = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        myRef = database.getReference("Userbase");
        dbUsers = myRef.child("dbusers");

//        if(dbuser != null){
//            Toast.makeText(this, dbuser.getEmail() + " Is signed in", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(this, "No dbuser is signed in", Toast.LENGTH_LONG).show();
//        }

        mFirstname = (EditText)findViewById(R.id.field_firstname);
        mLastname = (EditText)findViewById(R.id.field_lastname);
        mPhoneNumber = (EditText) findViewById(R.id.field_phone);
        mPassword = (EditText) findViewById(R.id.field_password);
        mCreateUser = (Button)findViewById(R.id.create_user_button);
//        mImageView = (ImageView)findViewById(R.id.imageView);
//        mImageName = (TextView)findViewById(R.id.image_name);
//        mBrowseImages = (Button)findViewById(R.id.browse_image);
//        mUpload = (Button)findViewById(R.id.upload_image);
//
//        mSpinnerSlide = (AppCompatSpinner) findViewById(R.id.spinner_role);
//        mSpinnerSlide.setAdapter(new ArrayAdapter<>(this, R.layout.spinner, roles));
//        mSpinnerSlide.setSelection(1);

//        mBrowseImages.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent();
//                i.setType("image/*");
//                i.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(i,"Select Profile Image"),REQUEST_CODE);
//            }
//        });

//        mUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(imgUri != null){
//                    final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
//                    dialog.setTitle("Uploading Image");
//                    dialog.show();
//                    StorageReference ref = mStorageRef.child(FB_DATABASE_PATH + System.currentTimeMillis()+"."+ getImageExt(imgUri));
//
//                    ref.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                            dialog.dismiss();
//                            Toast.makeText(SignUpActivity.this, "Successfully Uploaded.", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//
//                                }
//                            })
//                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//
//                                }
//                            });
//                }
//            }
//        });

        mCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(SignUpActivity.this, "" + mPhoneNumber.getText().toString() + "@gmail.com", Toast.LENGTH_SHORT).show();
//                Toast.makeText(SignUpActivity.this, "" + mPassword.getText().toString(), Toast.LENGTH_SHORT).show();
                createAccount(mPhoneNumber.getText().toString() + "@gmail.com",mPassword.getText().toString());

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUri = data.getData();

                try {
                    Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                    mImageView.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public String getImageExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        getSelectedRole();

        showProgressDialog();

        // [START create_dbuser_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in dbuser's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser dbuser = mAuth.getCurrentUser();
                            createOtherUserProperties(dbuser);
                        } else {
                            // If sign in fails, display a message to the dbuser.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_dbuser_with_email]
    }

    private void createOtherUserProperties(FirebaseUser dbuser) {
        String theNetwork = "net";
        String mFirst = mFirstname.getText().toString();
        String mLast = mLastname.getText().toString();
        String mToken = SharedPrefManager.getmInstance(this).getToken();
        String image = "https://res.cloudinary.com/dw0nbbvlf/image/upload/t_media_lib_thumb/v1495890833/proxeepay/face4.jpg";

        Shiuser UserData = new Shiuser(mFirst, mLast,theNetwork,dbuser.getEmail(),mToken,image);
        dbUsers.child(dbuser.getUid()).setValue(UserData);

        SharedPrefManager.getmInstance(this).storeUser(UserData);
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mPhoneNumber.getText().toString() + "@gmail.com";

        if (TextUtils.isEmpty(email)) {
            mPhoneNumber.setError("Required.");
            valid = false;
        }else if (email.length() < 9){
            mPhoneNumber.setError("Incomplete.");
            valid = false;
        }else if (mPhoneNumber.getText().toString().length() >  9){
            mPhoneNumber.setError("Too many numbers.");
            valid = false;
        } else {
            mPhoneNumber.setError(null);
        }
        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        return valid;
    }

//    private String getSelectedRole(AppCompatSpinner spinner) {
//        int index = spinner.getSelectedItemPosition();
//        String u = roles[index];
//        return u;
//    }

}
