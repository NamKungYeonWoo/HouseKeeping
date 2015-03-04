package com.nbmlm.housekeeping;

import android.content.Context;

/**
 * Created by Home on 2015/3/4.
 */
public class LeatherOrder extends Order{
    private String mCount;
    private String mPrice;

    public LeatherOrder (Context context, String datetime, String count, String price, String address, String contacter, String phone, String notes){
        super(context, datetime,address,contacter,phone,notes);
        mCount = count;
        mPrice = price;
        mType = OrderManager.ORDER_TYPE_LEATHER_PROTECTION;
    }
    public String getCount(){
        return mCount;
    }
    public String getPrice(){
        return mPrice;
    }
    public String toString(){
        return mContext.getString(R.string.item_leather) + "\n"
                + mContext.getString(R.string.order_datetime) + ": "+ mDateTime + "\n"
                + mContext.getString(R.string.order_subcount) + ": "+ mCount + "\n"
                + mContext.getString(R.string.order_subtotal) + ": "+ mPrice + "\n"
                + mContext.getString(R.string.order_address) + ": "+ mAddress + "\n"
                + mContext.getString(R.string.order_personhit) + ": "+ mContacter + "\n"
                + mContext.getString(R.string.order_phonehit) + ": "+ mPhoneNumber + "\n"
                + mContext.getString(R.string.order_note) + ": "+ mNotes;
    }
}
