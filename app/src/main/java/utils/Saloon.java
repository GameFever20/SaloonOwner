package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bunny on 12/06/17.
 */

public class Saloon {

    String saloonUID ;
    String saloonName ; //input
    String saloonPhoneNumber ;//input
    String saloonAddress;//input
    String saloonLocation ;//input in background
    int saloonPoint ;
    int saloonRating ;
    Map<String ,String> saloonImageList ;

    //timing
    //images


    public Saloon() {

    }

    public String getSaloonName() {
        return saloonName;
    }

    public void setSaloonName(String saloonName) {
        this.saloonName = saloonName;
    }

    public String getSaloonUID() {
        return saloonUID;
    }

    public void setSaloonUID(String saloonUID) {
        this.saloonUID = saloonUID;
    }

    public String getSaloonPhoneNumber() {
        return saloonPhoneNumber;
    }

    public void setSaloonPhoneNumber(String saloonPhoneNumber) {
        this.saloonPhoneNumber = saloonPhoneNumber;
    }

    public String getSaloonAddress() {
        return saloonAddress;
    }

    public void setSaloonAddress(String saloonAddress) {
        this.saloonAddress = saloonAddress;
    }

    public String getSaloonLocation() {
        return saloonLocation;
    }

    public void setSaloonLocation(String saloonLocation) {
        this.saloonLocation = saloonLocation;
    }

    public int getSaloonPoint() {
        return saloonPoint;
    }

    public void setSaloonPoint(int saloonPoint) {
        this.saloonPoint = saloonPoint;
    }

    public int getSaloonRating() {
        return saloonRating;
    }

    public void setSaloonRating(int saloonRating) {
        this.saloonRating = saloonRating;
    }

    public boolean isSaloonUpdated(){

        if(saloonPhoneNumber == null || saloonLocation==null || saloonAddress==null ||saloonName ==null){
            return false;
        }

        if (!saloonName.isEmpty()){

            if(!saloonPhoneNumber.isEmpty()){

                if(!saloonAddress.isEmpty()){

                    if(!saloonLocation.isEmpty()){

                        return true ;

                    }else{
                        return false;
                    }

                }else {
                    return false;
                }

            }else{
                return false;
            }

        }else{
            return false;
        }

    }

    public boolean isSaloonImageUpdated(){
        if(saloonImageList != null) {
            if (!saloonImageList.get("profile_image").isEmpty()) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }
    }

}
