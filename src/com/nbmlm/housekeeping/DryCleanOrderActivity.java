package com.nbmlm.housekeeping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DryCleanOrderActivity extends Activity implements View.OnClickListener{
	private Button mDateTime;
	private Button mAddress;
	private Button mCommit;
	
	private Button mType;
	private TextView mSubCount;
	private TextView mSubTotal;
	public int mCount;
	public int mTotal;
	
	private EditText mContacts;
	private EditText mPhoneNumber;
	private EditText mNote;
	
	private AlertDialog mAlertDialog;
	private EditText mAddressEditText;
	
	private WheelMain mWheelMain;
	private DateFormat mDateFormat;
	
	private String mServiceType;
	
	ListView mItemList;
    ItemListAdapter mItemListAdapter;
    private  ArrayList<Map<String,Object>>  mArrayList=  new ArrayList<Map<String,Object>>();
    
    public String[] mItemName;
    public String[] mItemPrice;
    public int[] mItemCount;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        mServiceType = getString(R.string.item_dryclean);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();

        if(intent!=null){
        	if(intent.getBooleanExtra("isDryClean", true)){
        		mServiceType = getString(R.string.item_dryclean);
        	}else{
        		mServiceType = getString(R.string.item_leather);
        	}
        }
        View view = getLayoutInflater().inflate(R.layout.drycleanorder,null);
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
        
    	mType = (Button)findViewById(R.id.type);
    	mType.setText(mServiceType);
    	mType.setOnClickListener(this);
    	mSubCount = (TextView)findViewById(R.id.subcount);
    	mSubTotal = (TextView)findViewById(R.id.subtotal);
        
        
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
    	if(mSubCount.getText().toString().equals("")||mSubTotal.getText().toString().equals("")){
    		Toast.makeText(getApplicationContext(),  R.string.order_item, Toast.LENGTH_LONG).show(); 
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
    
    private void showDateTime(){
    	LayoutInflater inflater=LayoutInflater.from(DryCleanOrderActivity.this);
		final View timepickerview=inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(DryCleanOrderActivity.this);
		mWheelMain = new WheelMain(timepickerview,true);
		mWheelMain.screenheight = screenInfo.getHeight();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		mWheelMain.initDateTimePicker(year,month,day,hour,min);
		mAlertDialog = new AlertDialog.Builder(DryCleanOrderActivity.this)
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
    
    private void initListView() {
    	mItemListAdapter=new ItemListAdapter(
                this,
                mArrayList,
                R.layout.itemlistadapt
                );
        mItemList.setAdapter(mItemListAdapter);
        mItemList.setOnItemClickListener(new OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
        	Map<String,Object> map = (Map<String,Object>)mItemListAdapter.getItem(arg2);
        }
         
    });
        mItemListAdapter.notifyDataSetChanged();
    }
    public void updateCheck() {
    	mCount = 0;
		mTotal = 0;
		for(int i=0;i<mItemName.length;i++){
			if(mItemCount[i]==0)continue;
			mCount += mItemCount[i];
			mTotal += mItemCount[i]* Integer.parseInt(mItemPrice[i]);
		}
    	mSubCount.setText(""+ mCount);
    	mSubTotal.setText(""+ mTotal);
    }
    private void initListDate(){
    	if(mServiceType.equals(getString(R.string.item_dryclean))){
    		mItemName = getResources().getStringArray(R.array.dryclean_list);
    		mItemPrice = getResources().getStringArray(R.array.dryclean_price);
    		
    	}else{
    		mItemName = getResources().getStringArray(R.array.leathercare_list);
    		mItemPrice = getResources().getStringArray(R.array.leathercare_price);
    	}
    	mItemCount = new int[mItemName.length];
        for(int i=0;i< mItemName.length;i++){
        	Map map = new HashMap<String, Object>();
        	map.put("image", R.drawable.sample);//TODO
            map.put("name", mItemName[i]);
            map.put("price", mItemPrice[i]);
            map.put("count", mItemCount[i]);
            mArrayList.add(map);
        }
    }
    
    private void showServiceItem(){ 
    	initListDate();
    	LayoutInflater inflater=LayoutInflater.from(DryCleanOrderActivity.this);
		final View timeview=inflater.inflate(R.layout.itemview, null);
		mItemList = (ListView)timeview.findViewById(R.id.listview);
        initListView();
		mAlertDialog = new AlertDialog.Builder(DryCleanOrderActivity.this)
		.setTitle(R.string.order_item)
		.setIcon(android.R.drawable.ic_dialog_info)
		.setView(timeview)
		.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mArrayList.clear();
				mAlertDialog.dismiss();
			}
		})
		.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mArrayList.clear();
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
               String count = mSubCount.getText().toString();
               String price = mSubTotal.getText().toString();
               String address = mAddress.getText().toString();
               String contacter = mContacts.getText().toString();
               String phone = mPhoneNumber.getText().toString();
               String note = mNote.getText().toString();
               if (mServiceType.equals(getString(R.string.item_dryclean))){
                   DrycleanOrder order = new DrycleanOrder(datetime,count, price,address, contacter, phone,note);
                   OrderManager.getInstance().addOrder(order);
               }else{
                   LeatherOrder order = new LeatherOrder(datetime,count,price,address, contacter, phone, note);
                   OrderManager.getInstance().addOrder(order);
               }
			   this.finish();
		   }
	   }else if(view==mType){
		   showServiceItem();
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
