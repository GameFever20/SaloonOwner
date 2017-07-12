package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import utils.FireBaseHandler;
import utils.Saloon;

public class SignUpActivity extends AppCompatActivity {

    EditText mEmailEditText, mPasswordEditText;
    Button mSignInButton;
    ProgressDialog progressDialog;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //remove actionbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //Instantiating views and buttons
        mEmailEditText = (EditText) findViewById(R.id.signup_Email_EditText);
        mPasswordEditText = (EditText) findViewById(R.id.signup_Password_EditText);
        //mPasswordEditText = (EditText) findViewById(R.id.signup_Password_EditText);

        mSignInButton = (Button) findViewById(R.id.signup_signUp_Button);


        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //On click of sign in button
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(mEmailEditText.getText().toString().trim(), mPasswordEditText.getText().toString().trim());
            }
        });

        //progress dialog
        progressDialog = new ProgressDialog(this);


    }


    private void signUp(String email, String password) {
        Log.d("In sign in ", "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d("In sign in ", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            LoginActivity.saloonUID = user.getUid();
                            uploadSaloon();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("In sign in", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }



                    }
                });
        // [END sign_in_with_email]
    }

    public void signInOptionalButton(View view){
        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void uploadSaloon() {
        final Saloon saloon = new Saloon();
        saloon.setSaloonPoint(-10);
        EditText editText = (EditText) findViewById(R.id.signup_saloonName_EditText);

        saloon.setSaloonName(editText.getText().toString().trim());
        saloon.setSaloonUID(LoginActivity.saloonUID);
        saloon.setSaloonEmailID(mEmailEditText.getText().toString().trim());


        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadSaloon(LoginActivity.saloonUID, saloon, new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {
                MainActivity.SALOON = saloon;
                hideProgressDialog();
                updateUI(mAuth.getCurrentUser());
            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        // updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            LoginActivity.saloonUID = user.getUid();
            Intent intent = new Intent(this, PhoneNumerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email id or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Signing up..");
        progressDialog.show();
    }

    public void hideProgressDialog() {

        progressDialog.cancel();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError("Required.");
            valid = false;
        } else {
            mEmailEditText.setError(null);
        }

        String password = mPasswordEditText.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError("Required.");
            valid = false;
        } else {
            mPasswordEditText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            return;
        } else {
            LoginActivity.saloonUID = currentUser.getUid();
            updateUI(currentUser);
            //Toast.makeText(this, "phone " + currentUser.getPhoneNumber(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "email "+currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }


}
