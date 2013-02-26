package com.example.packagemanager;

import java.util.List;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class PackageFragment extends ListFragment {

	private boolean mDualPane;
	private int mCurrentChoisePosition = 0;
	private PackagesAdapter mPackagesAdapter;
	private List<ApplicationInfo> mPackages;

	private final String CURRENT_CHOISE = "currentChoisePosition";
	private final static String INDEX = "index";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPackages = getActivity().getPackageManager().getInstalledApplications(
				PackageManager.GET_ACTIVITIES);
		mPackagesAdapter = new PackagesAdapter(getActivity()
				.getApplicationContext(), mPackages);		
		setListAdapter(mPackagesAdapter);
		if (savedInstanceState != null) {
			mCurrentChoisePosition = savedInstanceState.getInt(CURRENT_CHOISE);
		}
		View packageDetailsFrame = getActivity().findViewById(
				R.id.llPackageDetails);
		mDualPane = (packageDetailsFrame != null)
				&& packageDetailsFrame.getVisibility() == View.VISIBLE;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		getListView().setCacheColorHint(Color.TRANSPARENT);
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(CURRENT_CHOISE, mCurrentChoisePosition);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		showPackageDetails(position);
	}

	private void showPackageDetails(int position) {
		mCurrentChoisePosition = position;
		if (mDualPane) {
			setDetailsFragment(position);
		} else {
			startDetailsActivity(position);
		}
	}
	
	private void setDetailsFragment(int position)
	{
		getListView().setItemChecked(position, true);
		PackageDetailsFragment packageDetails = (PackageDetailsFragment) getFragmentManager()
				.findFragmentById(R.id.llPackageDetails);
		if (packageDetails == null
				|| packageDetails.getShownPosition() != position) {				
			packageDetails = PackageDetailsFragment.newInstance(position,
					mPackagesAdapter.getAppInfo(position).packageName);
		}			
		FragmentTransaction fragTrans = getFragmentManager()
				.beginTransaction();
		fragTrans.replace(R.id.llPackageDetails, packageDetails);
		fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		fragTrans.commit();
	}
	
	private void startDetailsActivity(int position)
	{
		Intent intent = new Intent();
		intent.setClass(getActivity(), PackageDetailsActivity.class);
		intent.putExtra(INDEX, position);
		startActivity(intent);
	}

	@Override
	public void onStart() {
		mPackages.clear();
		mPackages.addAll(getActivity().getPackageManager()
				.getInstalledApplications(PackageManager.GET_ACTIVITIES));
		mPackagesAdapter.notifyDataSetChanged();	
		super.onStart();
		if (mDualPane)
		{
			showPackageDetails(mCurrentChoisePosition);
		}
	}
}
