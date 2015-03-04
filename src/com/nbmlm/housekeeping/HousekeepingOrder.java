package com.nbmlm.housekeeping;

import android.content.Context;

/**
 * Created by Home on 2015/3/4.
 */
public class HousekeepingOrder extends Order{
    private String mTime;

    public HousekeepingOrder(Context context, String datetime, String time, String address, String contacter, String phone, String notes){
        super(context,datetime,address,contacter,phone,notes);
        mTime = time;
        mType = OrderManager.ORDER_TYPE_HOUSE_KEEPING;
    }
    public String getTime(){
        return mTime;
    }
    public String toString(){
        return mContext.getString(R.string.item_housekeeping) + "\n"
            + mContext.getString(R.string.order_datetime) + ": "+ mDateTime + "\n"
            + mContext.getString(R.string.order_hours) + ": "+ mTime + "\n"
            + mContext.getString(R.string.order_address) + ": "+ mAddress + "\n"
            + mContext.getString(R.string.order_personhit) + ": "+ mContacter + "\n"
            + mContext.getString(R.string.order_phonehit) + ": "+ mPhoneNumber + "\n"
            + mContext.getString(R.string.order_note) + ": "+ mNotes;
    }
}
