package com.nbmlm.housekeeping;

/**
 * Created by Home on 2015/3/4.
 */
public class DrycleanOrder extends Order{
    private String mCount;
    private String mPrice;

    public DrycleanOrder (String datetime, String count, String price, String address, String contacter, String phone, String notes){
        super(datetime,address,contacter,phone,notes);
        mCount = count;
        mPrice = price;
        mType = OrderManager.ORDER_TYPE_DRY_CLEAN;
    }
    public String getCount(){
        return mCount;
    }
    public String getPrice(){
        return mPrice;
    }
    public String toString(){
        return mDateTime + "\n"
                + mCount + "\n"
                + mPrice + "\n"
                + mAddress + "\n"
                + mContacter + "\n"
                + mPhoneNumber + "\n"
                + mNotes;
    }
}
