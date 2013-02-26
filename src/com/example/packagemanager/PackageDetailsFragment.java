package com.example.packagemanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PackageDetailsFragment extends Fragment {

	private boolean mUninstalled = false;
	private final static String INDEX = "index";
	private final static String PACKAGE_NAME = "packageName";
	private final static String LAUNCH_MESSAGE = "You can't start this application";
	private final static String NO_ACTIVITIES_MESSAGE = "This application has no activities";

	public static PackageDetailsFragment newInstance(int index,
			String packageName) {
		PackageDetailsFragment detailsFragment = new PackageDetailsFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(INDEX, index);
		bundle.putString(PACKAGE_NAME, packageName);
		detailsFragment.setArguments(bundle);
		return detailsFragment;
	}

	public int getShownPosition() {
		if (mUninstalled) {
			return -1;
		}
		return getArguments().getInt(INDEX, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}
		final String packName = getArguments().getString(PACKAGE_NAME);
		View resultView = inflater.inflate(R.layout.package_item_extended,
				container, false);
		final PackageManager packageManager = getActivity()
				.getApplicationContext().getPackageManager();
		setViews(packName, resultView, packageManager);
		setButtons(packName, resultView, packageManager);
		return resultView;
	}

	private void setViews(String packName, View resultView,
			PackageManager packageManager) {
		ImageView ivPackageIcon = (ImageView) resultView
				.findViewById(R.id.ivPackageIcon);
		try {
			ivPackageIcon.setImageDrawable(packageManager
					.getApplicationIcon(packName));
		} catch (NameNotFoundException e) {
			ivPackageIcon.setImageDrawable(null);
		}
		TextView tvPackageName = (TextView) resultView
				.findViewById(R.id.tvPackageName);
		tvPackageName.setText(packName);
	}

	private void setButtons(String packName, View resultView,
			PackageManager packageManager) {
		Button btnViewActivities = (Button) resultView
				.findViewById(R.id.btnViewAcrivities);
		btnViewActivities.setOnClickListener(setViewActOnClickListener(
				packageManager, packName));
		Button btnLaunch = (Button) resultView
				.findViewById(R.id.btnLaunchPackage);
		btnLaunch.setOnClickListener(setLaunchOnClickListener(packageManager,
				packName));
		Button btnUninstall = (Button) resultView
				.findViewById(R.id.btnUninstall);
		btnUninstall.setOnClickListener(setUninstallOnClickListener(
				packageManager, packName));
	}

	private OnClickListener setViewActOnClickListener(
			final PackageManager packageManager, final String packName) {
		final Context context = getActivity().getApplicationContext();
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (packageManager.getPackageInfo(packName,
							PackageManager.GET_ACTIVITIES).activities != null) {
						Intent activitiesIntent = new Intent(context,
								ActivitiesList.class);
						activitiesIntent.putExtra(PACKAGE_NAME, packName);
						startActivity(activitiesIntent);
					} else {
						Toast.makeText(getActivity(), NO_ACTIVITIES_MESSAGE,
								Toast.LENGTH_SHORT).show();
					}
				} catch (NameNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
	}

	private OnClickListener setLaunchOnClickListener(
			final PackageManager packageManager, final String packName) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent launchIntent = packageManager
						.getLaunchIntentForPackage(packName);
				if (launchIntent != null) {
					startActivity(launchIntent);
				} else {
					Toast.makeText(getActivity(), LAUNCH_MESSAGE,
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	private OnClickListener setUninstallOnClickListener(
			final PackageManager packageManager, final String packName) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				mUninstalled = true;
				Intent intent = new Intent(Intent.ACTION_UNINSTALL_PACKAGE);
				intent.setData(Uri.parse("package:" + packName));
				startActivity(intent);
			}
		};
	}
}
