package com.nbmlm.housekeeping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 
 */
public class MainActivity extends Activity implements View.OnClickListener{
	private ViewPager mViewPager; 
	private List<ImageView> mImageViews; 

	private int[] mImageResId;
	private List<View> mDots; 

	private int mCurrentItem = 0; 
	
	private Button mHousekeeping;
	private Button mDryClean;
	private Button mLeatherCare;
	
	BottomViewItem mBottomView;
	private int mCurrentIndex = 0;
	
	private AlertDialog mAlertDialog;

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService mScheduledExecutorService;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(mCurrentItem);
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mImageResId = new int[] { R.drawable.b, R.drawable.a, R.drawable.b,R.drawable.a, R.drawable.b};

		mImageViews = new ArrayList<ImageView>();

		for (int i = 0; i < mImageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(mImageResId[i]);
			//imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setScaleType(ScaleType.FIT_XY);
			mImageViews.add(imageView);
		}

		mDots = new ArrayList<View>();
		mDots.add(findViewById(R.id.v_dot0));
		mDots.add(findViewById(R.id.v_dot1));
		mDots.add(findViewById(R.id.v_dot2));
		mDots.add(findViewById(R.id.v_dot3));
		mDots.add(findViewById(R.id.v_dot4));

		mViewPager = (ViewPager) findViewById(R.id.vp);
		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		
		mHousekeeping = (Button) findViewById(R.id.btn1);
		mHousekeeping.setOnClickListener(this);
		mDryClean = (Button) findViewById(R.id.btn2);
		mDryClean.setOnClickListener(this);
		mLeatherCare = (Button) findViewById(R.id.btn3);
		mLeatherCare.setOnClickListener(this);
		
		mBottomView = BottomViewItem.getInstance();
		for (int i = 0; i < mBottomView.viewNum; i++) {
			mBottomView.linears[i] = (LinearLayout) findViewById(mBottomView.linears_id[i]);
			mBottomView.linears[i].setOnClickListener(this);
			mBottomView.images[i] = (ImageView) findViewById(mBottomView.images_id[i]);
			mBottomView.texts[i] = (TextView) findViewById(mBottomView.texts_id[i]);
		}
		
		for (int i = 0; i < mBottomView.viewNum; i++) {
			mBottomView.images[i].setBackgroundResource(i == mCurrentIndex ? mBottomView.images_selected[i] : mBottomView.images_unselected[i]);
			mBottomView.texts[i].setTextColor(i == mCurrentIndex ? getResources().getColor(R.color.bottom_text_selected) : getResources().getColor(R.color.bottom_text_unselected));
		}

	}

	@Override
	protected void onStart() {
		mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 10, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		mScheduledExecutorService.shutdown();
		super.onStop();
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (mViewPager) {
				//mCurrentItem = (mCurrentItem + 1) % mImageViews.size();
				mCurrentItem++;
				mHandler.obtainMessage().sendToTarget(); 
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			mCurrentItem = position;
			position = position % mImageViews.size();
			//mCurrentItem = position;
			mDots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			mDots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
	}

	private class MyAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			//return mImageResId.length;
			return Integer.MAX_VALUE;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
            int index = arg1 % mImageViews.size();
            ImageView v = mImageViews.get(index);
            if ( index%2 == 0 ) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent aboutintent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(aboutintent);
                    }
                });
            }else{
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent regintent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(regintent);
                    }
                });
            }
			((ViewPager) arg0).addView(v);
            return v;
			//return mImageViews.get(arg1% mImageViews.size());
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mImageViews.get(arg1%mImageViews.size()));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
	
	private void resestFoucus(){
		mBottomView.images[mCurrentIndex].setBackgroundResource(mBottomView.images_unselected[mCurrentIndex]);
		mBottomView.texts[mCurrentIndex].setTextColor(getResources().getColor(R.color.bottom_text_unselected));
		mCurrentIndex = 0 ;
		mBottomView.images[mCurrentIndex].setBackgroundResource(mBottomView.images_selected[mCurrentIndex]);
		mBottomView.texts[mCurrentIndex].setTextColor(getResources().getColor(R.color.bottom_text_selected));
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		for (int i = 0; i < mBottomView.viewNum; i++) {
			if (v.getId() == mBottomView.linears_id[i]) {
//				for(int j= 0; j < mBottomView.viewNum;j++) {
//					mBottomView.images[j].setBackgroundResource(mBottomView.images_unselected[j]);
//					mBottomView.texts[j].setTextColor(getResources().getColor(R.color.bottom_text_unselected));
//				}
				if(mCurrentIndex==i&&mCurrentIndex==0)break;
				if(mCurrentIndex>=0 && mCurrentIndex<mBottomView.viewNum){
					mBottomView.images[mCurrentIndex].setBackgroundResource(mBottomView.images_unselected[mCurrentIndex]);
					mBottomView.texts[mCurrentIndex].setTextColor(getResources().getColor(R.color.bottom_text_unselected));
				}
				mCurrentIndex = i ;
				mBottomView.images[i].setBackgroundResource(i == mCurrentIndex ? mBottomView.images_selected[i] : mBottomView.images_unselected[i]);
				mBottomView.texts[i].setTextColor(i == mCurrentIndex ? getResources().getColor(R.color.bottom_text_selected) : getResources().getColor(R.color.bottom_text_unselected));
				if(mCurrentIndex==0){//home
					finish();
					Intent intentitem = new Intent();
			    	intentitem.setClassName("com.nbmlm.housekeeping","com.nbmlm.housekeeping.MainActivity");
		            startActivity(intentitem);
				}else if(mCurrentIndex==1){//order
					Log.e("", "order");
				}else if(mCurrentIndex==2){//contacts
					Log.e("", "contacts");
				}else if(mCurrentIndex==3){//call
					mAlertDialog = new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.phone_title)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage(R.string.phone_number)
						.setPositiveButton(getString(R.string.phone_call), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								resestFoucus();
								mAlertDialog.dismiss();
								Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.phone_number)));
								startActivity(intent);
							}
						})
						.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								resestFoucus();
								mAlertDialog.dismiss();
							}
						})
						.setCancelable(false)
						.show();
				}
			}
		}
		if(v==mHousekeeping){
			Intent intentitem = new Intent();
	    	intentitem.setClassName("com.nbmlm.housekeeping","com.nbmlm.housekeeping.HousekeepingOrderActivity");
            startActivity(intentitem);
		}else if(v==mDryClean){
			Intent intentitem = new Intent();
	    	intentitem.setClassName("com.nbmlm.housekeeping","com.nbmlm.housekeeping.DryCleanOrderActivity");
	    	intentitem.putExtra("isDryClean", true);
            startActivity(intentitem);
		}else if(v==mLeatherCare){
			Intent intentitem = new Intent();
	    	intentitem.setClassName("com.nbmlm.housekeeping","com.nbmlm.housekeeping.DryCleanOrderActivity");
	    	intentitem.putExtra("isDryClean", false);
            startActivity(intentitem);
		} 
		
	}

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle(R.string.dialog_quit)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                }).show();
    }
}
