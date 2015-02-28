package com.nbmlm.housekeeping;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomViewItem {

	public static BottomViewItem instance;

	public static BottomViewItem getInstance() {
		if (instance == null) {
			instance = new BottomViewItem();
		}
		return instance;
	}

	public int viewNum = 4;
	public ImageView[] images = new ImageView[viewNum];
	public TextView[] texts = new TextView[viewNum];
	public LinearLayout[] linears = new LinearLayout[viewNum];
	public int[] images_id = new int[] { R.id.home_image, R.id.order_image, R.id.person_image, R.id.service_image };
	public int[] texts_id = new int[] { R.id.home_text, R.id.order_text, R.id.person_text, R.id.service_text };
	public int[] linears_id = new int[] { R.id.home_layout, R.id.order_layout, R.id.person_layout, R.id.service_layout };
	public int[] images_selected = new int[] { R.drawable.home_selected, R.drawable.order_selected, R.drawable.person_selected, R.drawable.service_selected };
	public int[] images_unselected = new int[] { R.drawable.home_unselected, R.drawable.order_unselected, R.drawable.person_unselected, R.drawable.service_unselected };

}
