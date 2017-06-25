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
import utils.ServiceAdapter;

public class AddSaloonServiceActivity extends AppCompatActivity {

    Saloon saloon = MainActivity.SALOON;
    ArrayAdapter<CharSequence> adapter;
    Service service = new Service();

    ProgressDialog progressDialog;

    Intent intent;
    String saloonUID = LoginActivity.saloonUID;
    private ArrayList<Service> serviceArrayList;


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

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.downloadServiceList(saloonUID, 30, new FireBaseHandler.OnServiceListener() {
            @Override
            public void onSeviceUpload(boolean isSuccesful) {


            }

            @Override
            public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {
                if (isSuccesful) {
                    AddSaloonServiceActivity.this.serviceArrayList = serviceArrayList;


                    if (serviceArrayList.size() >= 5) {
                        saloon.setSaloonServiceUpdated(true);
                        checkSaloonPoint();

                    }

                } else {


                }
            }
        });

        //initialze service to service to be editted and call reinitialize
    }

    public void addServiceClick(View view) {
        if (saloon.getSaloonPoint() == 0 || saloon.getSaloonPoint() == -1500) {
            return;
        }
        final Service service = createService();
        if (service == null) {
            return;
        }

        showProgressDialog("Adding new Service", "");

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadService(service, new FireBaseHandler.OnServiceListener() {
            @Override
            public void onSeviceUpload(boolean isSuccesful) {
                Toast.makeText(AddSaloonServiceActivity.this, "Uploaded Service", Toast.LENGTH_SHORT).show();
                closeProgressDialog();
                serviceArrayList.add(service);
                if (serviceArrayList.size() >= 5) {
                    showExitDialogue("Service Added succesfully", "Do you want to add more service ");
                } else if (serviceArrayList.size() < 5) {
                    showExitDialogue("Service Added succesfully", "Add " + (5 - serviceArrayList.size()) + " more service to continue");
                }


            }

            @Override
            public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {

            }
        });

    }

    private void showExitDialogue(String title, String message) {
        if (!saloon.isSaloonServiceUpdated()) {
            if (serviceArrayList.size() >= 5) {
                saloon.setSaloonServiceUpdated(true);
                uploadSaloonServiceUpdated();
                checkSaloonPoint();

            }
        }


        if (intent == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            service = new Service();
                            reInitializeActivity();
                        }
                    });

            if (serviceArrayList.size() >= 5) {
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        intent = new Intent(AddSaloonServiceActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);
                        finish();
                        /*if (!saloon.checkSaloonImageUpdated()) {
                            intent = new Intent(AddSaloonServiceActivity.this, SaloonImageActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else if(!saloon.isSaloonUpdated()){
                            intent = new Intent(AddSaloonServiceActivity.this, SaloonProfile.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }else{
                            intent = new Intent(AddSaloonServiceActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }*/

                    }
                });
            }

            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        } else {

        }

    }

    private void uploadSaloonServiceUpdated() {
        new FireBaseHandler().uploadSaloonInfo(saloonUID, "saloonServiceUpdated",saloon.isSaloonServiceUpdated(), new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

                //Toast.makeText(AddSaloonServiceActivity.this, "Saloon ready to be verified ", Toast.LENGTH_SHORT).show();


            }
        });
    }

    private void checkSaloonPoint() {
        if (saloon.getSaloonPoint() < -1 && saloon.getSaloonPoint() > -100) {

            int i = -100;

            if (!saloon.checkSaloonImageUpdated()) {
                i = i + 10;


            }
            if (!saloon.isSaloonServiceUpdated()) {
                i = i + 10;

            }
            if (!saloon.isSaloonUpdated()) {
                i = i + 10;

            }

            if (i == -100) {
                //show pending approval screen and initialize intent with pending approval screen
                saloon.setSaloonPoint(i);

                uploadSaloon();
            } else if (i < 0) {
                saloon.setSaloonPoint(i);
                uploadSaloon();
            }

        }

        if (saloon.getSaloonPoint() == -1000) {
            //blocked by admin
            Toast.makeText(this, "Blocked by admin", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void uploadSaloon() {
        new FireBaseHandler().uploadSaloonInfo(saloonUID, "saloonPoint",saloon.getSaloonPoint(), new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {

            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

                Toast.makeText(AddSaloonServiceActivity.this, "Saloon ready to be verified ", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private void reInitializeActivity() {
        EditText editText = (EditText) findViewById(R.id.addSaloonService_serviceName_editText);
        editText.setText(service.getServiceName());
        editText = (EditText) findViewById(R.id.addSaloonService_servicePrice_editText);
        if(service.getServicePrice()==0){
            editText.setText(null);
        }else {
            editText.setText(service.getServicePrice() + "");
        }

        editText = (EditText) findViewById(R.id.addSaloonService_serviceOfferPrice_editText);
        if(service.getServiceOfferPrice()==0){
            editText.setText(null);
        }else {
            editText.setText(service.getServiceOfferPrice() + "");
        }


        TextView textView = (TextView) findViewById(R.id.addSaloonService_serviceType_textView);
        textView.setText(service.getServiceTypeName());


    }

    private Service createService() {


        EditText editText = (EditText) findViewById(R.id.addSaloonService_serviceName_editText);
        String serviceName = editText.getText().toString().trim();
        if (serviceName.isEmpty()) {
            Toast.makeText(this, "Enter service name ", Toast.LENGTH_SHORT).show();
            //editText.setError("Required");
            return null;
        } else {

            service.setServiceName(serviceName);

        }

        editText = (EditText) findViewById(R.id.addSaloonService_servicePrice_editText);
        String servicePrice = editText.getText().toString().trim();
        if (servicePrice.isEmpty()) {
            Toast.makeText(this, "Enter service price ", Toast.LENGTH_SHORT).show();
            //editText.setError("Required");
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

        editText = (EditText) findViewById(R.id.addSaloonService_servicePrice_editText);
        String serviceOfferPrice = editText.getText().toString().trim();
        if (serviceOfferPrice.isEmpty()) {

            service.setServiceOfferPrice(service.getServicePrice());
        } else {
            int servicePriceint = Integer.valueOf(serviceOfferPrice);

            service.setServiceOfferPrice(servicePriceint);

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
