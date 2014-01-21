package com.example.packagemanager;

import java.util.ArrayList;

import android.content.pm.ApplicationInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	private ArrayList<ApplicationInfo> mData;

	public ViewPagerAdapter(FragmentManager fm, ArrayList<ApplicationInfo> data) {
		super(fm);
		mData = data;
	}

	@Override
	public Fragment getItem(int position) {
		return PackageDetailsFragment.newInstance(position,
				mData.get(position).packageName);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

}