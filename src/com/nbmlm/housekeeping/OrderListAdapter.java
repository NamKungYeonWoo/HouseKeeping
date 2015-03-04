package com.nbmlm.housekeeping;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class OrderListAdapter extends BaseAdapter {
	OrdersActivity mContext;
	ArrayList<Map<String,Object>> mList;
	int layout;
	
	public OrderListAdapter(OrdersActivity context, ArrayList<Map<String,Object>> list ,int layout){
		super();
		this.mContext = context;
		this.mList = list;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(final int index, View view, ViewGroup group) {
		View v=LayoutInflater.from(mContext).inflate(layout, null);
		Button icon = (Button)v.findViewById(R.id.order_icon);
		
		TextView type=(TextView) v.findViewById(R.id.order_type);
		int typevale = Integer.parseInt(mList.get(index).get("type").toString());
		if(typevale==OrderManager.ORDER_TYPE_HOUSE_KEEPING){
			type.setText(R.string.item_housekeeping);
		}else if(typevale==OrderManager.ORDER_TYPE_DRY_CLEAN){
			type.setText(R.string.item_dryclean);
		}else if(typevale==OrderManager.ORDER_TYPE_LEATHER_PROTECTION){
			type.setText(R.string.item_leather);
		}
		TextView datetime=(TextView) v.findViewById(R.id.order_datetime);
		datetime.setText(mList.get(index).get("datetime").toString());
		TextView address=(TextView) v.findViewById(R.id.order_address);
		address.setText(mList.get(index).get("address").toString());
		TextView status=(TextView) v.findViewById(R.id.order_status);
		status.setText(mList.get(index).get("status").toString());
		Button cancel=(Button) v.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				mContext.removeOrder(index);
				
			}}
		);
		return v;
	}

}