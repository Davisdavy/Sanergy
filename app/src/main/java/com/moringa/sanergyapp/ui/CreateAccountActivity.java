package com.moringa.sanergyapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moringa.sanergyapp.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private static final int PICK_IMAGE = 1;
    @BindView(R.id.createUserButton)
    Button mCreateUserButton;
    @BindView(R.id.nameEditText)
    EditText mNameEditText;
    @BindView(R.id.emailEditText)
    EditText mEmailEditText;
    @BindView(R.id.passwordEditText)
    EditText mPasswordEditText;
    @BindView(R.id.registerProgress)
    ProgressBar mProgress;
    @BindView(R.id.loginTextView)
    TextView mLoginTextView;
    @BindView (R.id.create_image_user)
    CircleImageView mAdminImage;


    private FirebaseAuth mAuth;
    private StorageReference mStorage;
    private FirebaseFirestore mFireStore;
    private String mName;
    private Uri mImageUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        mImageUri = null;

        mAuth = FirebaseAuth.getInstance();
        mFireStore=FirebaseFirestore.getInstance();
        mAdminImage = (CircleImageView) findViewById(R.id.create_image_user);
        mStorage = FirebaseStorage.getInstance().getReference().child("images");
        getSupportActionBar().hide();

        mCreateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mImageUri != null){
                    mProgress.setVisibility(View.VISIBLE);
                    String name = mNameEditText.getText().toString();
                    String email = mEmailEditText.getText().toString();
                    String password = mPasswordEditText.getText().toString();

                    if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String emp_id = mAuth.getCurrentUser().getUid();
                                    StorageReference user_profile = mStorage.child(emp_id + ".jpg");
                                    user_profile.putFile(mImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {
                                            if(uploadTask.isSuccessful()){

                                                String download_url = uploadTask.getResult().getStorage().getDownloadUrl().toString();
                                                Map<String, Object> userMap = new HashMap<>();
                                                userMap.put("name",name);
                                                userMap.put("image",download_url);
                                                mFireStore.collection("Employees").document(emp_id).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mProgress.setVisibility(View.INVISIBLE);
                                                        sendToMain();
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(CreateAccountActivity.this,"Error: " + uploadTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    Toast.makeText(CreateAccountActivity.this, "Error" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    mProgress.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
                    }
                }
            }

         });

        mAdminImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE);
            }
        });
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void sendToMain() {
        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE){
           mImageUri = data.getData();
           mAdminImage.setImageURI(mImageUri);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginTextView) {
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }
    }
}
