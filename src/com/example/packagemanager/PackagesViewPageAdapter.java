package com.example.packagemanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PackagesViewPageAdapter extends PagerAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<ApplicationInfo> mData;

	public PackagesViewPageAdapter(Context context,
			ArrayList<ApplicationInfo> data) {
		mContext = context;
		mData = data;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public boolean isViewFromObject(View v, Object o) {
		return v.equals(o);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}

	private View createPackageView(int position) {

		return null;
	}
}
