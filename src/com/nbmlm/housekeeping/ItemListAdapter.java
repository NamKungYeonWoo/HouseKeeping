package com.nbmlm.housekeeping;

import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ItemListAdapter extends BaseAdapter {
	DryCleanOrderActivity mContext;
	ArrayList<Map<String,Object>> mList;
	int layout;
	
	public ItemListAdapter(DryCleanOrderActivity context, ArrayList<Map<String,Object>> list ,int layout){
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
		ImageView iv =(ImageView) v.findViewById(R.id.item_img);
		iv.setImageResource(Integer.parseInt(mList.get(index).get("image").toString()));
		TextView name=(TextView) v.findViewById(R.id.item_name);
		name.setText(mList.get(index).get("name").toString());
		TextView price=(TextView) v.findViewById(R.id.item_price);
		price.setText("µ¥¼Û£º£¤"+mList.get(index).get("price").toString());

		final EditText count=(EditText) v.findViewById(R.id.item_count);
		count.setText(mList.get(index).get("count").toString());
		ImageButton sub=(ImageButton) v.findViewById(R.id.item_sub);
		ImageButton add=(ImageButton) v.findViewById(R.id.item_add);
		sub.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				int n =Integer.parseInt(count.getText().toString());
				n--;
				if(n<1)n=0;
				count.setText(String.valueOf(n));
				mContext.mItemCount[index] = n;
			    mContext.updateCheck();
			}}
		);
		add.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				int n=Integer.parseInt(count.getText().toString())+1;
				count.setText(String.valueOf(n));
				mContext.mItemCount[index] = n;
				mContext.updateCheck();
			}});
		return v;
	}

}