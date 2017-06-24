package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.akhgupta.easylocation.EasyLocationActivity;
import com.akhgupta.easylocation.EasyLocationRequest;
import com.akhgupta.easylocation.EasyLocationRequestBuilder;
import com.google.android.gms.location.LocationRequest;

import utils.FireBaseHandler;
import utils.Saloon;

public class SaloonProfile extends EasyLocationActivity {

    EditText saloonNameEditText, saloonAddressEditText, saloonLocationEditText, saloonPhoneNumberEditText;
    String saloonName, saloonAddress, saloonLocation, saloonPhoneNumber;

    TextView saloonNameTextView;


    FireBaseHandler fireBaseHandler;
    Saloon saloon;

    ProgressDialog progressDialog;

    Intent intent;

    String saloonUID = LoginActivity.saloonUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_profile);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onSaveButtonClick();
            }
        });


        initializeActivity();


    }

    private void initializeActivity() {
        if (MainActivity.SALOON != null) {
            saloon = MainActivity.SALOON;

            //saloonNameEditText = (EditText) findViewById(R.id.saloonprofile_saloonName_editText);
            saloonAddressEditText = (EditText) findViewById(R.id.saloonprofile_saloonAddress_editText);
            // saloonLocationEditText = (EditText) findViewById(R.id.saloonprofile_saloonLocation_editText);
            saloonPhoneNumberEditText = (EditText) findViewById(R.id.saloonprofile_saloonPhoneNumber_editText);
            saloonNameTextView = (TextView) findViewById(R.id.saloonprofile_saloonName_textView);


            if (saloon.getSaloonPoint() != 0) {
                try {
                    // saloonNameEditText.setText(saloon.getSaloonName());
                    saloonNameTextView.setText(saloon.getSaloonName());
                    saloonPhoneNumberEditText.setText(saloon.getSaloonPhoneNumber());
                    // saloonLocationEditText.setText(saloon.getSaloonLocation());
                    saloonAddressEditText.setText(saloon.getSaloonAddress());

                    TextView textView = (TextView) findViewById(R.id.saloonProfile_openingTime_textView);
                    textView.setText(saloon.getOpeningTimeHour() + ":" + saloon.getOpeningTimeMinute());

                    textView = (TextView) findViewById(R.id.saloonProfile_closingTime_textView);
                    textView.setText(saloon.getClosingTimeHour() + ":" + saloon.getClosingTimeMinute());

                    textView = (TextView) findViewById(R.id.saloonProfile_saloonLocation_textView);
                    textView.setText(saloon.getSaloonLocationLatitude() + "," + saloon.getSaloonLocationLongitude());


                } catch (Exception e) {
                    Toast.makeText(this, "Something went wrong ", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            showExitDialogue("Connection problem", "Data not available");
        }

        fireBaseHandler = new FireBaseHandler();

        progressDialog = new ProgressDialog(this);

    }

    private void showExitDialogue(String title, String message) {

        if (intent == null) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }


    public void onSaveButtonClick(View view) {


        if(saloon.getSaloonPoint() == 0){
            Toast.makeText(this, "Not a registered saloon", Toast.LENGTH_SHORT).show();

            return;
        }

        if (validateForm()) {

            //saloon.setSaloonName(saloonNameEditText.getText().toString().trim());
            saloon.setSaloonAddress(saloonAddressEditText.getText().toString().trim());
            saloon.setSaloonPhoneNumber(saloonPhoneNumberEditText.getText().toString().trim());
            //saloon.setSaloonLocation(saloonLocationEditText.getText().toString().trim());
        } else {
            return;
        }





        if (saloon.getSaloonPoint() < -1 && saloon.getSaloonPoint()> -100){

            int i=-100;
            if (!saloon.isSaloonUpdated()){
                i=i+10;
            }

            if(!saloon.isSaloonServiceUpdated()){
                i=i+10;
                intent = new Intent(SaloonProfile.this , AddSaloonServiceActivity.class);
            }
            if(!saloon.checkSaloonImageUpdated()){
                i=i+10;
                intent = new Intent(SaloonProfile.this , SaloonImageActivity.class);
            }

            if(i==-100){
                //show pending approval screen
                saloon.setSaloonPoint(i);
            }else if(i<0){
                saloon.setSaloonPoint(i);
            }

        }

        if (saloon.getSaloonPoint()==-1000){
            //blocked by admin
            Toast.makeText(this, "Blocked by admin", Toast.LENGTH_SHORT).show();
            return;
        }



        if(saloon.isSaloonUpdated()) {

            showProgressDialog("Updating Profile", "Please wait");

            fireBaseHandler.uploadSaloon(saloonUID, saloon, new FireBaseHandler.OnSaloonDownload() {
                @Override
                public void onSaloon(Saloon saloon) {

                }

                @Override
                public void onSaloonValueUploaded(boolean isSucessful) {

                    closeProgressDialog();
                    Toast.makeText(SaloonProfile.this, "uploaded", Toast.LENGTH_SHORT).show();
                    showExitDialogue("Profile uploaded", "Profile updated successfully \n click yes to close");

                }
            });

        }else{
            Toast.makeText(this, "Some details not filled ", Toast.LENGTH_SHORT).show();
            return;
        }




    }

    public void selectClosingTime(View view) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        TextView textView = (TextView) findViewById(R.id.saloonProfile_closingTime_textView);
                        textView.setText(hourOfDay + ":" + minute);
                        saloon.setClosingTimeHour(hourOfDay);
                        saloon.setClosingTimeMinute(minute);

                        //Toast.makeText(SaloonProfile.this, "Time is "+hourOfDay +"minutes "+minute, Toast.LENGTH_SHORT).show();

                    }
                }, 21, 00, false);
        timePickerDialog.show();

    }

    public void selectOpeningTime(View view) {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        TextView textView = (TextView) findViewById(R.id.saloonProfile_openingTime_textView);
                        textView.setText(hourOfDay + ":" + minute);
                        saloon.setOpeningTimeHour(hourOfDay);
                        saloon.setOpeningTimeMinute(minute);


                    }
                }, 10, 00, false);
        timePickerDialog.show();

    }

    public void selectLocationClick(View view) {
        double latitude, longitude;

        if (saloon.getSaloonLocationLatitude() == 0 && saloon.getSaloonLocationLongitude() == 0) {
            latitude = 22.624853;
            longitude = 88.408329;
        } else {
            latitude = saloon.getSaloonLocationLatitude();
            longitude = saloon.getSaloonLocationLongitude();
        }


        LocationRequest locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(5000)
                .setFastestInterval(5000);


        EasyLocationRequest easyLocationRequest = new EasyLocationRequestBuilder()
                .setLocationRequest(locationRequest)
                .setFallBackToLastLocationTime(3000)
                .build();

        requestSingleLocationFix(easyLocationRequest);

        showProgressDialog("Calculating Location", "");

    }


    @Override
    public void onLocationPermissionGranted() {

    }

    @Override
    public void onLocationPermissionDenied() {


    }

    @Override
    public void onLocationReceived(Location location) {
        Toast.makeText(this, "Latitude - " + location.getLatitude(), Toast.LENGTH_SHORT).show();
        saloon.setSaloonLocationLatitude(location.getLatitude());
        saloon.setSaloonLocationLongitude(location.getLongitude());

        TextView textView = (TextView) findViewById(R.id.saloonProfile_saloonLocation_textView);
        textView.setText(saloon.getSaloonLocationLatitude() + "," + saloon.getSaloonLocationLongitude());

        closeProgressDialog();

    }

    @Override
    public void onLocationProviderEnabled() {

    }

    @Override
    public void onLocationProviderDisabled() {

    }


    public boolean validateForm() {
        String string = saloonAddressEditText.getText().toString().trim();

        boolean valid = true;
        if (string.isEmpty()) {
            saloonAddressEditText.setError("Required");
            valid = false;
        } else {

        }

        string = saloonPhoneNumberEditText.getText().toString().trim();

        if (string.isEmpty()) {
            saloonPhoneNumberEditText.setError("Required");
            valid = false;
        } else {

        }

        return valid;


    }


    public void showProgressDialog(String title, String message) {
        progressDialog.setMessage(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeProgressDialog() {
        progressDialog.dismiss();
    }

}
