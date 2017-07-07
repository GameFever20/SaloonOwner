package app.owner.saloon.craftystudio.saloonowner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utils.FireBaseHandler;
import utils.Saloon;

/**
 * Created by Aisha on 6/16/2017.
 */

public class TabbedSaloon extends Fragment {

    TextView mfragmentSaloonName, mfragmentSaloonPhoneNo, mfragmentSaloonAddress, mfragmentSaloonLocation, mfragmentSaloonRating, mfragmentSaloonPoint;

    Saloon saloon = null;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FireBaseHandler fireBaseHandler = new FireBaseHandler();

        fireBaseHandler.downloadSaloon(FullDetailActivity.ORDER.getSaloonID(), new FireBaseHandler.OnSaloonDownload() {
            @Override
            public void onSaloon(Saloon saloon) {
                Log.d("In tabbed Activity :", "Saloon name -" + saloon.getSaloonName());

                TabbedSaloon.this.saloon = saloon;

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(TabbedSaloon.this).attach(TabbedSaloon.this).commit();


            }

            @Override
            public void onSaloonValueUploaded(boolean isSucessful) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_saloon, container, false);
        View tempView = rootView;

        if (saloon != null) {

            mfragmentSaloonName = (TextView) tempView.findViewById(R.id.fragment_saloon_name_textview);
            mfragmentSaloonPhoneNo = (TextView) tempView.findViewById(R.id.fragment_saloon_calllink_textview);
            mfragmentSaloonAddress = (TextView) tempView.findViewById(R.id.fragment_saloon_Adress_textview);
            mfragmentSaloonLocation = (TextView) tempView.findViewById(R.id.fragment_saloon_location_textview);
            mfragmentSaloonRating = (TextView) tempView.findViewById(R.id.fragment_saloon_rating_textview);
            mfragmentSaloonPoint = (TextView) tempView.findViewById(R.id.fragment_saloon_point_textview);


            mfragmentSaloonName.setText(mfragmentSaloonName.getText().toString() + "  " + saloon.getSaloonName());
            mfragmentSaloonPhoneNo.setText(saloon.getSaloonPhoneNumber());
            mfragmentSaloonAddress.setText(mfragmentSaloonAddress.getText().toString() + "  " + saloon.getSaloonAddress());
            mfragmentSaloonLocation.setText(mfragmentSaloonLocation.getText().toString() + "  " + saloon.getSaloonLocation());
            mfragmentSaloonRating.setText(mfragmentSaloonRating.getText().toString() + "  " + saloon.getSaloonRating());
            mfragmentSaloonPoint.setText(mfragmentSaloonPoint.getText().toString() + "  " + saloon.getSaloonPoint());


        }

        return tempView;
    }
}
