package app.owner.saloon.craftystudio.saloonowner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utils.FireBaseHandler;
import utils.Order;

/**
 * Created by Aisha on 6/16/2017.
 */

public class TabbedService extends Fragment {

    TextView mfragmentServiceName, mfragmentServicePrice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_service, container, false);
        View tempView=rootView;

        mfragmentServiceName=(TextView) tempView.findViewById(R.id.fragment_service_name_textview);
        mfragmentServicePrice=(TextView) tempView.findViewById(R.id.fragment_service_price_textview);



        FireBaseHandler fireBaseHandler= new FireBaseHandler();

        fireBaseHandler.downloadOrder( FullDetailActivity.SaloonID,FullDetailActivity.OrderID, new FireBaseHandler.OnOrderDownloadListner() {
            @Override
            public void onOrder(Order order) {
                Log.d("Order status","status:"+order.getOrderServiceName());

                mfragmentServiceName.setText(mfragmentServiceName.getText().toString()+"  "+order.getOrderServiceName());
                mfragmentServicePrice.setText(mfragmentServicePrice.getText().toString()+"  "+order.getOrderPrice());

            }
        });


        return tempView;
    }
}
