package com.example.packagemanager;

import java.util.List;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class PackageFragment extends ListFragment {

	private boolean mDualPane;
	private int mCurrentChoisePosition = 0;
	private PackagesAdapter packagesAdapter;
	private List<ApplicationInfo> packages;

	private final String CURRENT_CHOISE = "currentChoisePosition";
	private final static String INDEX = "index";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		packages = getActivity().getPackageManager().getInstalledApplications(
				PackageManager.GET_ACTIVITIES);
		packagesAdapter = new PackagesAdapter(getActivity()
				.getApplicationContext(), packages);		
		setListAdapter(packagesAdapter);
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
			getListView().setItemChecked(position, true);
			PackageDetailsFragment packageDetails = (PackageDetailsFragment) getFragmentManager()
					.findFragmentById(R.id.llPackageDetails);
			if (packageDetails == null
					|| packageDetails.getShownPosition() != position) {				
				packageDetails = PackageDetailsFragment.newInstance(position,
						packagesAdapter.getAppInfo(position).packageName);
			}
			Log.d("pm", Boolean.toString(packageDetails.getShownPosition() != position));
			FragmentTransaction fragTrans = getFragmentManager()
					.beginTransaction();
			fragTrans.replace(R.id.llPackageDetails, packageDetails);
			fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			fragTrans.commit();
		} else {
			Intent intent = new Intent();
			intent.setClass(getActivity(), PackageDetailsActivity.class);
			intent.putExtra(INDEX, position);
			startActivity(intent);
		}

	}

	@Override
	public void onStart() {
		packages.clear();
		packages.addAll(getActivity().getPackageManager()
				.getInstalledApplications(PackageManager.GET_ACTIVITIES));
		packagesAdapter.notifyDataSetChanged();	
		super.onStart();
		if (mDualPane)
		{
			showPackageDetails(mCurrentChoisePosition);
		}
	}
}
