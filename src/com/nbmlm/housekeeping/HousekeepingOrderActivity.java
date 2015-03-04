package com.nbmlm.housekeeping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.widget.time.ScreenInfo;
import com.widget.time.WheelMain;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HousekeepingOrderActivity extends Activity implements View.OnClickListener{
	private Button mDateTime;
	private Button mAddress;
	private Button mCommit;
	
	private TextView mHours2;
	private TextView mHours3;
	private TextView mHours4;
	
	private EditText mContacts;
	private EditText mPhoneNumber;
	private EditText mNote;
	
	private int mCurrentHours=2;
	
	private AlertDialog mAlertDialog;
	private EditText mAddressEditText;
	
	private WheelMain mWheelMain;
	private DateFormat mDateFormat;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        initView();
    }

    private void initView(){
        Intent intent = getIntent();

        if(intent!=null){
        }
        View view = getLayoutInflater().inflate(R.layout.housekeepingorder,null);
        setContentView(view);
        
        mDateTime = (Button)findViewById(R.id.btn_datetime);
        mDateTime.setOnClickListener(this);
        mDateTime.setFocusable(true);
        mDateTime.setFocusableInTouchMode(true);
        mDateTime.requestFocus();
        mDateTime.requestFocusFromTouch();
        mAddress = (Button)findViewById(R.id.btn_address);
        mAddress.setOnClickListener(this);
        mCommit = (Button)findViewById(R.id.btn_commit);
        mCommit.setOnClickListener(this);
        
        mHours2 = (TextView)findViewById(R.id.hours2);
        mHours2.setOnClickListener(this);
        mHours3 = (TextView)findViewById(R.id.hours3);
        mHours3.setOnClickListener(this);
        mHours4 = (TextView)findViewById(R.id.hours4);
        mHours4.setOnClickListener(this);
        
        mContacts = (EditText)findViewById(R.id.contacts);
        mPhoneNumber = (EditText)findViewById(R.id.phone);
        mNote = (EditText)findViewById(R.id.note);
    }
    
    private boolean checkOrderValid(){
    	boolean ret = true;
    	if(mDateTime.getText().equals(getString(R.string.order_datetimehit))){
    		Toast.makeText(getApplicationContext(), R.string.order_datetimehit, Toast.LENGTH_LONG).show();
    		ret = false;
    		return ret;
    		
		}
    	if(mAddress.getText().equals(getString(R.string.order_addresshit))){
    		Toast.makeText(getApplicationContext(),  R.string.order_addresshit, Toast.LENGTH_LONG).show(); 
    		ret = false;
    		return ret;
		}
    	if(mContacts.getText().toString().equals("")){
    		Toast.makeText(getApplicationContext(), R.string.order_personhit, Toast.LENGTH_LONG).show(); 
    		ret = false;
    		return ret;
		}
    	if(mPhoneNumber.getText().toString().equals("")){
    		Toast.makeText(getApplicationContext(), R.string.order_phonehit, Toast.LENGTH_LONG).show(); 
    		ret = false;
    		return ret;
		}
    	return ret;
    }
    private void clearSelected(){
    	if(mCurrentHours >=2 && mCurrentHours<5){
    		switch(mCurrentHours){
    		  case 2:
    			  mHours2.setTextColor(getResources().getColor(R.color.bottom_text_selected));
    			  mHours2.setBackgroundResource(R.drawable.buttonshape_normal);
    			  break;
    		  case 3:
    			  mHours3.setTextColor(getResources().getColor(R.color.bottom_text_selected));
    			  mHours3.setBackgroundResource(R.drawable.buttonshape_normal);
    			  break;
    		  case 4:
    			  mHours4.setTextColor(getResources().getColor(R.color.bottom_text_selected));
    			  mHours4.setBackgroundResource(R.drawable.buttonshape_normal);
    			  break;
    		}
    	}
    }
    
    private void showDateTime(){
    	LayoutInflater inflater=LayoutInflater.from(HousekeepingOrderActivity.this);
		final View timepickerview=inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(HousekeepingOrderActivity.this);
		mWheelMain = new WheelMain(timepickerview,true);
		mWheelMain.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		mWheelMain.initDateTimePicker(year,month,day,hour,min);
		mAlertDialog = new AlertDialog.Builder(HousekeepingOrderActivity.this)
		.setTitle(R.string.order_datetimehit)
		.setIcon(android.R.drawable.ic_dialog_info)
		.setView(timepickerview)
		.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mDateTime.setText(mWheelMain.getTime());
				mAlertDialog.dismiss();
			}
		})
		.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mAlertDialog.dismiss();
			}
		})
		.show();
    }

    @Override
	public void onClick(View view) {
	   if(view==mDateTime){
		   showDateTime();
	   }else if(view==mAddress){
		   mAddressEditText = new EditText(this);
		   if(!mAddress.getText().equals(getString(R.string.order_addresshit))){
			   mAddressEditText.setText(mAddress.getText());
		   }
		   mAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.order_addresshit).setIcon(
				     android.R.drawable.ic_dialog_info).setView(
				    		 mAddressEditText).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {  
	                                     @Override  
	                                     public void onClick(DialogInterface dialog, int which) {
	                                    	 String add = mAddressEditText.getText().toString();
	                                    	 if(add!=null&&!add.equals("")){
	                                    		 mAddress.setText(add); 
	                                    	 }
	                     	                 mAlertDialog.dismiss();
	                                     }  
	                 })
				     .setNegativeButton(getString(R.string.cancel), null).show();
	   }else if(view==mCommit){
		   if(checkOrderValid()){
               String datetime = mDateTime.getText().toString();
               String time = Integer.toString(mCurrentHours);
               String address = mAddress.getText().toString();
               String contacter = mContacts.getText().toString();
               String phone = mPhoneNumber.getText().toString();
               String note = mNote.getText().toString();
               HousekeepingOrder order = new HousekeepingOrder(datetime, time, address, contacter, phone,note);
               OrderManager.getInstance().addOrder(order);
			   this.finish(); 
		   }
	   }else if(view==mHours2){
		   clearSelected();
		   mHours2.setTextColor(getResources().getColor(R.color.rect_selected));
		   mHours2.setBackgroundResource(R.drawable.buttonshape_selected);
		   mCurrentHours = 2;
	   }else if(view==mHours3){
		   clearSelected();
		   mHours3.setTextColor(getResources().getColor(R.color.rect_selected));
		   mHours3.setBackgroundResource(R.drawable.buttonshape_selected);
		   mCurrentHours = 3;
	   }else if(view==mHours4){
		   clearSelected();
		   mHours4.setTextColor(getResources().getColor(R.color.rect_selected));
		   mHours4.setBackgroundResource(R.drawable.buttonshape_selected);
		   mCurrentHours = 4;
	   }
	}

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initView();
    }
}
