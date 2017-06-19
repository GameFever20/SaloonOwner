package app.owner.saloon.craftystudio.saloonowner;

import android.content.DialogInterface;
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

        Spinner spinner = (Spinner) findViewById(R.id.addSaloonService_serviceType_spinner);

        adapter = ArrayAdapter.createFromResource(this,
                R.array.service_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }

    public void addServiceClick(View view) {
        if (saloon.getSaloonPoint() == 0 || saloon.getSaloonPoint() == -1500) {
            return;
        }
        Service service = createService();

        FireBaseHandler fireBaseHandler = new FireBaseHandler();
        fireBaseHandler.uploadService(service, new FireBaseHandler.OnServiceListener() {
            @Override
            public void onSeviceUpload(boolean isSuccesful) {
                Toast.makeText(AddSaloonServiceActivity.this, "Uploaded Service", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onServiceList(ArrayList<Service> serviceArrayList, boolean isSuccesful) {

            }
        });

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


        if(service.getServiceType()>0){

        }else{
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
                        service.setServiceType(which+1);
                        Log.d("spinner", service.getServiceType() + service.getServiceTypeName());


                        TextView textView = (TextView)findViewById(R.id.addSaloonService_serviceType_textView);
                        textView.setText(serviceTypeName);



                    }
                });
        builder.create();
        builder.show();


    }
}
