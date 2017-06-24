package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bunny on 12/06/17.
 */

public class Saloon {

    private String saloonUID ;
    private String saloonName ; //input
    private String saloonPhoneNumber ;//input
    private String saloonAddress;//input
    private String saloonLocation ;//input in background
    private int saloonPoint ;
    private int saloonRating ;
    //images

    double saloonLocationLatitude ;
    double saloonLocationLongitude ;

    //for initiale
    boolean saloonServiceUpdated;


    private Map<String ,String> saloonImageList ;

    //timing
    private int openingTimeHour , openingTimeMinute , closingTimeHour ,closingTimeMinute ;


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

    public void setSaloonPhoneNumber(String saloonPhoneNo) {
        this.saloonPhoneNumber = saloonPhoneNo;
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

    public Map<String, String> getSaloonImageList() {
        return saloonImageList;
    }

    public void setSaloonImageList(Map<String, String> saloonImageList) {
        this.saloonImageList = saloonImageList;
    }


    public int getOpeningTimeHour() {
        return openingTimeHour;
    }

    public void setOpeningTimeHour(int openingTimeHour) {
        this.openingTimeHour = openingTimeHour;
    }

    public int getOpeningTimeMinute() {
        return openingTimeMinute;
    }

    public void setOpeningTimeMinute(int openingTimeMinute) {
        this.openingTimeMinute = openingTimeMinute;
    }

    public int getClosingTimeHour() {
        return closingTimeHour;
    }

    public void setClosingTimeHour(int closingTimeHour) {
        this.closingTimeHour = closingTimeHour;
    }

    public int getClosingTimeMinute() {
        return closingTimeMinute;
    }

    public void setClosingTimeMinute(int closingTimeMinute) {
        this.closingTimeMinute = closingTimeMinute;
    }

    public double getSaloonLocationLatitude() {
        return saloonLocationLatitude;
    }

    public void setSaloonLocationLatitude(double saloonLocationLatitude) {
        this.saloonLocationLatitude = saloonLocationLatitude;
    }

    public double getSaloonLocationLongitude() {
        return saloonLocationLongitude;
    }

    public void setSaloonLocationLongitude(double saloonLocationLongitude) {
        this.saloonLocationLongitude = saloonLocationLongitude;
    }

    public boolean isSaloonServiceUpdated() {
        return saloonServiceUpdated;
    }

    public void setSaloonServiceUpdated(boolean saloonServiceUpdated) {
        this.saloonServiceUpdated = saloonServiceUpdated;
    }

    public boolean isSaloonUpdated(){

        if(saloonPhoneNumber == null || saloonAddress==null ){
            return false;
        }
        if(saloonPhoneNumber.isEmpty() ||saloonAddress.isEmpty() ){

            return  false;
        }

        if((openingTimeHour ==0 && openingTimeMinute==0) || (closingTimeHour == 0 &&closingTimeMinute==0)){

            return false ;
        }

        if(saloonLocationLatitude == 0 || saloonLocationLongitude == 0 ){
            return false;
        }

        return true;


    }

    public boolean checkSaloonImageUpdated(){
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
