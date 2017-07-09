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

public class TabbedOrder extends Fragment {

    TextView mfragmentOrderStatus, mfragmentOrderTime;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_order, container, false);
        View tempView=rootView;

        if (FullDetailActivity.ORDER !=null) {

            mfragmentOrderStatus = (TextView) tempView.findViewById(R.id.fragment_order_status_textview);
            mfragmentOrderTime = (TextView) tempView.findViewById(R.id.fragment_order_time_textview);


            mfragmentOrderStatus.setText(mfragmentOrderStatus.getText().toString() + "  " + FullDetailActivity.ORDER.resolveOrderStatus());
            mfragmentOrderTime.setText(mfragmentOrderTime.getText().toString() + "  " + FullDetailActivity.ORDER.resolveOrderDate());
        }

        return tempView;
    }
}
