package com.nbmlm.housekeeping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

public class OrdersActivity extends Activity implements View.OnClickListener{
	ListView mOrderList;
    OrderListAdapter mOrderListAdapter;
    private  ArrayList<Map<String,Object>>  mArrayList=  new ArrayList<Map<String,Object>>();
    private TextView mEmpty;
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView(){
        Intent intent = getIntent();
        if(intent!=null){
        	//
        }
        setContentView(R.layout.orderview);  
        mOrderList = (ListView)findViewById(R.id.listview);
        mEmpty = (TextView)findViewById(R.id.emptyview);
        
        initListDate();
       
    }

    private void initListView() {
    	mOrderListAdapter=new OrderListAdapter(
                this,
                mArrayList,
                R.layout.orderlistadapt
                );
        mOrderList.setAdapter(mOrderListAdapter);
        mOrderList.setOnItemClickListener(new OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
        	Map<String,Object> map = (Map<String,Object>)mOrderListAdapter.getItem(arg2);
        }
         
    });
        mOrderListAdapter.notifyDataSetChanged();
    }
    
    private void initListDate(){
    	List<Order> orderList = OrderManager.getInstance().getOrders();
    	mArrayList.clear();
    	if(orderList==null || orderList.size()==0){
    		mOrderList.setVisibility(View.GONE);
    		mEmpty.setVisibility(View.VISIBLE);
    	}else{
    		mOrderList.setVisibility(View.VISIBLE);
    		mEmpty.setVisibility(View.GONE);
    		for(int i=0;i< orderList.size();i++){
            	Map map = new HashMap<String, Object>();
            	map.put("type", orderList.get(i).mType);
                map.put("datetime", orderList.get(i).mDateTime);
                map.put("address", orderList.get(i).mAddress);
                map.put("status", getString(R.string.order_status_wait));
                mArrayList.add(map);
            }
    		initListView();
    	}
    }
    
    public void removeOrder(int index){
    	List<Order> orderList = OrderManager.getInstance().getOrders();
    	orderList.remove(index);
    	initListDate();
    }

    @Override
	public void onClick(View view) {
	   
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
