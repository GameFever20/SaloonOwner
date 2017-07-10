package app.owner.saloon.craftystudio.saloonowner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import utils.FireBaseHandler;
import utils.Order;
import utils.Service;
import utils.ServiceAdapter;

/**
 * Created by Aisha on 6/16/2017.
 */

public class TabbedService extends Fragment {




    RecyclerView recyclerView ;
    ArrayList<Service> serviceArrayList =new ArrayList<>();
    ServiceAdapter serviceAdapter ;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        for (Service service : FullDetailActivity.ORDER.getOrderServiceIDList().values()){

            serviceArrayList.add(service);

        }

        serviceAdapter = new ServiceAdapter(TabbedService.this.serviceArrayList);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_service, container, false);
        View tempView=rootView;


        recyclerView =(RecyclerView) rootView.findViewById(R.id.fragmentTabbedService_serviceList_recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(serviceAdapter);



        /*mfragmentServiceName = (TextView) tempView.findViewById(R.id.fragment_service_name_textview);
            mfragmentServicePrice = (TextView) tempView.findViewById(R.id.fragment_service_price_textview);
            mFragmentServiceTypeName = (TextView) tempView.findViewById(R.id.fragment_service_serviceTypeName_textview);


            mfragmentServiceName.setText(mfragmentServiceName.getText().toString() + "  " + FullDetailActivity.ORDER.resolveOrderServiceList());
            mfragmentServicePrice.setText(mfragmentServicePrice.getText().toString() + "  " + FullDetailActivity.ORDER.getOrderPrice());
           // mFragmentServiceTypeName.setText(mFragmentServiceTypeName.getText().toString() + "  " + FullDetailActivity.SERVICE.getServiceTypeName());
*/

        return tempView;
    }
}
