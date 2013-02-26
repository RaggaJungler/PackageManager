package com.example.packagemanager;

import java.util.ArrayList;
import java.util.List;

import android.app.LauncherActivity.ListItem;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class PackageDetailsActivity extends FragmentActivity {

	private final static String INDEX = "index";
	private ViewPagerAdapter mPagerAdapter;
	private List<ApplicationInfo> mPackages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
	}

	@Override
	protected void onStart() {
		setContentView(R.layout.view_pager);
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
		mPackages = getPackageManager().getInstalledApplications(
				PackageManager.GET_ACTIVITIES);
		mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
				(ArrayList<ApplicationInfo>) mPackages);
		viewPager.setAdapter(mPagerAdapter);
		viewPager.setCurrentItem(getIntent().getIntExtra(INDEX, 0));
		super.onStart();
	}
}
