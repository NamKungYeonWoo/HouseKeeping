package com.nbmlm.housekeeping;

/**
 * Created by Home on 2015/3/4.
 */
public class Order {
    protected int mType;
    protected String mDateTime;
    protected String mAddress;
    protected String mContacter;
    protected String mPhoneNumber;
    protected String mNotes;

    public Order(String datetime, String address, String contacter, String phone, String notes){
        mDateTime = datetime;
        mAddress = address;
        mContacter = contacter;
        mPhoneNumber = phone;
        mNotes = notes;
    }

    public String getDateTime(){
        return mDateTime;
    }
    public String getAddress(){
        return mAddress;
    }
    public String getContacter(){
        return mContacter;
    }
    public String getPhone(){
        return mPhoneNumber;
    }
    public String getNotes(){
        return mNotes;
    }
}
