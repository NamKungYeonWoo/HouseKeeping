package com.nbmlm.housekeeping;

/**
 * Created by Home on 2015/3/4.
 */
public class HousekeepingOrder extends Order{
    private String mTime;

    public HousekeepingOrder(String datetime, String time, String address, String contacter, String phone, String notes){
        super(datetime,address,contacter,phone,notes);
        mTime = time;
        mType = OrderManager.ORDER_TYPE_HOUSE_KEEPING;
    }
    public String getTime(){
        return mTime;
    }
    public String toString(){
        return mDateTime + "\n"
             + mTime + "\n"
             + mAddress + "\n"
             + mContacter + "\n"
             + mPhoneNumber + "\n"
             + mNotes;
    }
}
