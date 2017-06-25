package app.owner.saloon.craftystudio.saloonowner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import utils.FireBaseHandler;
import utils.Saloon;

public class PhoneNumerActivity extends AppCompatActivity {

    String mVerificationID = null;
    private String apiKeyFacto = "5a75fe84-599e-11e7-94da-0200cd936042";
    EditText mPhoneNumberEditText, mOTPEditText;
    Button sendOtpButton, verifyOtpButton;

    String mphoneNumber = null;

    Saloon saloon = MainActivity.SALOON;
    Intent intent;
    boolean isUpdating = false;

    TextView mHeadingTextview;

    LinearLayout mOptlinerlayout,mPhonenumberLinearlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        mOptlinerlayout=(LinearLayout)findViewById(R.id.otp_Linearlayout) ;
        mPhonenumberLinearlayout=(LinearLayout)findViewById(R.id.phonenumber_Linearlayout);


        mHeadingTextview = (TextView) findViewById(R.id.heading_phone_number_textview);
        mPhoneNumberEditText = (EditText) findViewById(R.id.phoneNumber_phoneNumber_EditText);
        mOTPEditText = (EditText) findViewById(R.id.phoneNumber_otp_EditText);
        //mOTPEditText.setVisibility(View.GONE);
        mOptlinerlayout.setVisibility(View.GONE);

        sendOtpButton = (Button) findViewById(R.id.phoneNumber_sentOtp_Button);
        verifyOtpButton = (Button) findViewById(R.id.phoneNumber_verifyOtp_Button);
        verifyOtpButton.setVisibility(View.GONE);


        mPhoneNumberEditText.setText(saloon.getSaloonPhoneNumber());

    }


    public void sendOtpRequest(final String phoneNumber) {
        RequestQueue queue = Volley.newRequestQueue(this);


        String url = "https://2factor.in/API/V1/" + apiKeyFacto + "/SMS/+91" + phoneNumber + "/AUTOGEN";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(PhoneNumerActivity.this, "response " + response, Toast.LENGTH_SHORT).show();

                try {
                    if (response.getString("Status").equalsIgnoreCase("Success")) {
                        mVerificationID = response.getString("Details");
                        mphoneNumber = phoneNumber;

                        //otp sent succesfully
                        openOtpView();
                        //otp sentccesfully
                    } else {
                        Toast.makeText(PhoneNumerActivity.this, "Otp sent error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(jsObjRequest);

    }

    public void verifyOtpRequest(String mVerificationID, String otp) {
        RequestQueue queue = Volley.newRequestQueue(this);


        String url = "https://2factor.in/API/V1/" + apiKeyFacto + "/SMS/VERIFY/" + mVerificationID + "/" + otp;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(PhoneNumerActivity.this, "response " + response, Toast.LENGTH_SHORT).show();

                try {
                    if (response.getString("Status").equalsIgnoreCase("Success")) {
                        Toast.makeText(PhoneNumerActivity.this, response.getString("Details"), Toast.LENGTH_SHORT).show();

                        checkSaloonPoint();

                    } else {
                        Toast.makeText(PhoneNumerActivity.this, "Otp sent error", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PhoneNumerActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(jsObjRequest);

    }

    private void openOtpView() {
        //mOTPEditText.setVisibility(View.VISIBLE);
        mOptlinerlayout.setVisibility(View.VISIBLE);
        verifyOtpButton.setVisibility(View.VISIBLE);
        mHeadingTextview.setText("Validate your number");

      //  mPhoneNumberEditText.setVisibility(View.GONE);
        mPhonenumberLinearlayout.setVisibility(View.GONE);
        sendOtpButton.setVisibility(View.GONE);
    }


    public void sendotp(View view) {
        sendOtpRequest(mPhoneNumberEditText.getText().toString().trim());

    }

    public void verifyOtp(View view) {
        verifyOtpRequest(mVerificationID, mOTPEditText.getText().toString().trim());
    }


    private void checkSaloonPoint() {

        saloon.setSaloonPhoneNumber(mphoneNumber);

        if (saloon.getSaloonPoint() < -1 && saloon.getSaloonPoint() > -100) {

            int i = -100;


            if (!saloon.isSaloonServiceUpdated()) {
                i = i + 10;
                intent = new Intent(PhoneNumerActivity.this, AddSaloonServiceActivity.class);
            }

            if (!saloon.checkSaloonImageUpdated()) {
                i = i + 10;
                intent = new Intent(PhoneNumerActivity.this, SaloonImageActivity.class);


            }

            if (!saloon.isSaloonUpdated()) {
                i = i + 10;
                intent = new Intent(PhoneNumerActivity.this, SaloonProfile.class);
            }


            if (saloon.getSaloonPhoneNumber() == null) {
                i = i + 10;
            } else {
                if (saloon.getSaloonPhoneNumber().isEmpty()) {
                    i = i + 10;
                }
            }

            if (i == -100) {
                //show pending approval screen and initialize intent with pending approval screen
                saloon.setSaloonPoint(i);
                intent = new Intent(PhoneNumerActivity.this, MainActivity.class);
                uploadSaloon();
            } else if (i < 0) {
                saloon.setSaloonPoint(i);
                uploadSaloon();
            }

        } else {
            intent = null;
            uploadSaloon();
        }

        if (saloon.getSaloonPoint() == -1000) {
            //blocked by admin
            Toast.makeText(this, "Blocked by admin", Toast.LENGTH_SHORT).show();
            return;
        }


    }


    private void uploadSaloon() {
        new FireBaseHandler().uploadSaloon(LoginActivity.saloonUID, saloon, new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

                if (isSucessful) {
                    openActivity();
                }


            }
        });


    }

    private void openActivity() {
        if (intent != null) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }


}
