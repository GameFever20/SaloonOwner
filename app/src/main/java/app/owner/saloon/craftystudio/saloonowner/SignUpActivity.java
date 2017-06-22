package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class SignUpActivity extends AppCompatActivity {

    EditText mEmailEditText, mPasswordEditText;

    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Instantiating views and buttons
        mEmailEditText = (EditText) findViewById(R.id.signUp_Email_EditText);
        mPasswordEditText = (EditText) findViewById(R.id.signUp_Password_EditText);



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


        //progress dialog
        progressDialog = new ProgressDialog(this);


    }


    private void createAccount(String email, String password) {
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
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("In sign in", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                        hideProgressDialog();
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        // updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            LoginActivity.saloonUID = user.getUid();
            Intent intent = new Intent(this, SaloonProfile.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Email id or password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Signing in..");
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

    public void signUpButtonClick(View view) {
        createAccount(mEmailEditText.getText().toString().trim(), mPasswordEditText.getText().toString().trim());
    }
}
