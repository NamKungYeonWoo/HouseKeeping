package com.nbmlm.housekeeping;

import android.content.Context;

/**
 * Created by Home on 2015/3/4.
 */
public class Order {
    protected Context mContext;
    protected int mType;
    protected String mDateTime;
    protected String mAddress;
    protected String mContacter;
    protected String mPhoneNumber;
    protected String mNotes;

    public Order(Context context, String datetime, String address, String contacter, String phone, String notes){
        mContext = context;
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
