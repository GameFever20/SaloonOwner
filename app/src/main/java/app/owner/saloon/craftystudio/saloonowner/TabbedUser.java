package app.owner.saloon.craftystudio.saloonowner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import utils.FireBaseHandler;
import utils.User;

/**
 * Created by Aisha on 6/16/2017.
 */

public class TabbedUser extends Fragment {

User user =null;

    TextView mUserNameTextView , mUserPhoneNumberTextView ,mUserGenderTextView ,mUserAgeTextView ;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new FireBaseHandler().downloadUser(FullDetailActivity.ORDER.getUserID(), new FireBaseHandler.OnUserlistener() {
            @Override
            public void onUserDownLoad(User user, boolean isSuccessful) {
                if (isSuccessful){
                    TabbedUser.this.user = user;
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(TabbedUser.this).attach(TabbedUser.this).commit();

                }
            }

            @Override
            public void onUserUpload(boolean isSuccessful) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_user, container, false);

        if (user != null){
            //update ui

            mUserNameTextView =(TextView)rootView.findViewById(R.id.tabbedUser_username_textview);
            mUserPhoneNumberTextView =(TextView)rootView.findViewById(R.id.tabbedUser_userPhoneNumber_textview);
            mUserAgeTextView =(TextView)rootView.findViewById(R.id.tabbedUser_userAge_textview);
            mUserGenderTextView = (TextView)rootView.findViewById(R.id.tabbedUser_userGender_textview);

            mUserNameTextView.setText(user.getUserName());
            mUserGenderTextView.setText(user.getUserGender());

            mUserAgeTextView.setText(user.getUserAge()+"");

            if (FullDetailActivity.ORDER.getOrderStatus() >1){
                mUserPhoneNumberTextView.setText(user.getUserPhoneNumber());
            }



        }



        return rootView;
    }
}
