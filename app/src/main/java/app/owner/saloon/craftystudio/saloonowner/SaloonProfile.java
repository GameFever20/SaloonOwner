package app.owner.saloon.craftystudio.saloonowner;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import utils.FireBaseHandler;
import utils.Saloon;

public class SaloonProfile extends AppCompatActivity {

    EditText saloonNameEditText, saloonAddressEditText, saloonLocationEditText, saloonPhoneNumberEditText;
    String saloonName, saloonAddress, saloonLocation, saloonPhoneNumber;


    FireBaseHandler fireBaseHandler;
    Saloon saloon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_profile);
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

        saloon = MainActivity.SALOON;

      /*  fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadSaloon("abc", new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {
                SaloonProfile.this.saloon = saloon;
            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

                Toast.makeText(SaloonProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();
            }
        });*/

        saloonNameEditText = (EditText) findViewById(R.id.saloonprofile_saloonName_editText);
        saloonAddressEditText = (EditText) findViewById(R.id.saloonprofile_saloonAddress_editText);
        saloonLocationEditText = (EditText) findViewById(R.id.saloonprofile_saloonLocation_editText);
        saloonPhoneNumberEditText = (EditText) findViewById(R.id.saloonprofile_saloonPhoneNumber_editText);


    }


    public void uploadSaloonInfo(String saloonKeyValue, String value) {

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadSaloonInfo("abc", saloonKeyValue, value, new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {


            }
        });
    }

    public void onSaveButtonClick(View view) {


        saloon.setSaloonName(saloonNameEditText.getText().toString().trim());
        saloon.setSaloonAddress(saloonAddressEditText.getText().toString().trim());
        saloon.setSaloonPhoneNumber(saloonPhoneNumberEditText.getText().toString().trim());
        saloon.setSaloonLocation(saloonLocationEditText.getText().toString().trim());



        if (saloon.getSaloonPoint() == -10 || saloon.getSaloonPoint() == -1 || saloon.getSaloonPoint() > 0) {


            if (saloon.getSaloonPoint() == -10 || saloon.getSaloonPoint() == -1) {
                if (saloon.isSaloonUpdated()) {
                    if (saloon.isSaloonImageUpdated()) {
                        saloon.setSaloonPoint(10);
                    } else {
                        saloon.setSaloonPoint(-1);
                    }

                } else {
                    saloon.setSaloonPoint(-10);
                }
            }


            fireBaseHandler.uploadSaloon("abc", saloon, new FireBaseHandler.OnSaloonDownload() {
                @Override
                public void onSaloon(Saloon saloon) {

                }

                @Override
                public void onSaloonValueUploaded(boolean isSucessful) {

                    Toast.makeText(SaloonProfile.this, "uploaded", Toast.LENGTH_SHORT).show();

                }
            });

        } else if (saloon.getSaloonPoint() == -100) {
            // account blocked alert


        }


    }
}
