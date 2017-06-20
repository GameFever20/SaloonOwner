package app.owner.saloon.craftystudio.saloonowner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.Saloon;
import utils.Service;

public class AddSaloonServiceActivity extends AppCompatActivity {

    Saloon saloon = MainActivity.SALOON;
    ArrayAdapter<CharSequence> adapter;
    Service service = new Service();

    ProgressDialog progressDialog;

    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saloon_service);
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


        progressDialog = new ProgressDialog(this);


        //initialze service to service to be editted and call reinitialize
    }

    public void addServiceClick(View view) {
        if (saloon.getSaloonPoint() == 0 || saloon.getSaloonPoint() == -1500) {
            return;
        }
        Service service = createService();

        showProgressDialog("Adding new Service", "");

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadService(service, new FireBaseHandler.OnServiceListener() {
            @Override
            public void onSeviceUpload(boolean isSuccesful) {
                Toast.makeText(AddSaloonServiceActivity.this, "Uploaded Service", Toast.LENGTH_SHORT).show();
                closeProgressDialog();

                showExitDialogue("Service Added succesfully", "Do you want to add more service ");
            }

            @Override
            public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {

            }
        });

    }

    private void showExitDialogue(String title, String message) {
        if (saloon.getSaloonPoint()==10){
            intent =new Intent(AddSaloonServiceActivity.this, MainActivity.class);
        }

        if(intent==null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            service = new Service();
                            reInitializeActivity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    });

            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }else{
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

    }

    private void reInitializeActivity() {
        EditText editText = (EditText) findViewById(R.id.addSaloonService_serviceName_editText);
        editText.setText(service.getServiceName());
        editText = (EditText) findViewById(R.id.addSaloonService_servicePrice_editText);
        editText.setText(service.getServicePrice()+"");

        TextView textView = (TextView) findViewById(R.id.addSaloonService_serviceType_textView);
        textView.setText(service.getServiceTypeName());




    }

    private Service createService() {


        EditText editText = (EditText) findViewById(R.id.addSaloonService_serviceName_editText);
        String serviceName = editText.getText().toString().trim();
        if (serviceName.isEmpty()) {
            Toast.makeText(this, "Enter service name ", Toast.LENGTH_SHORT).show();
            return null;
        } else {

            service.setServiceName(serviceName);

        }

        editText = (EditText) findViewById(R.id.addSaloonService_servicePrice_editText);
        String servicePrice = editText.getText().toString().trim();
        if (servicePrice.isEmpty()) {
            Toast.makeText(this, "Enter service price ", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            int servicePriceint = Integer.valueOf(servicePrice);

            service.setServicePrice(servicePriceint);

        }


        if (service.getServiceType() > 0) {

        } else {
            Toast.makeText(this, "Service type not selected", Toast.LENGTH_SHORT).show();
            return null;
        }


/*
        Spinner spinner = (Spinner) findViewById(R.id.addSaloonService_serviceType_spinner);
        if (spinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select service type", Toast.LENGTH_SHORT).show();
            return null;
        } else {

            String serviceTypeName = String.valueOf(adapter.getItem(spinner.getSelectedItemPosition()));
            service.setServiceTypeName(serviceTypeName);
            service.setServiceType(spinner.getSelectedItemPosition());
            Log.d("spinner", service.getServiceType() + service.getServiceTypeName());

        }*/

        service.setSaloonUID(saloon.getSaloonUID());
        service.setSaloonName(saloon.getSaloonName());


        return service;


    }

    public void selectServiceType(View view) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Service Type")
                .setItems(R.array.service_type, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item


                        String serviceTypeName = getResources().getStringArray(R.array.service_type)[which];
                        service.setServiceTypeName(serviceTypeName);
                        service.setServiceType(which + 1);
                        Log.d("spinner", service.getServiceType() + service.getServiceTypeName());


                        TextView textView = (TextView) findViewById(R.id.addSaloonService_serviceType_textView);
                        textView.setText(serviceTypeName);


                    }
                });
        builder.create();
        builder.show();


    }


    public void showProgressDialog(String title, String message) {
        progressDialog.setMessage(message);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeProgressDialog() {
        progressDialog.dismiss();
    }

}
