package com.example.packagemanager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class PackageDetailsActivity extends FragmentActivity {

	final String packageName = "packageName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			finish();
			return;
		}
		if (savedInstanceState == null) {
			// During initial setup, plug in the details fragment.
			Log.d("pm", "pda saved == null");
			PackageDetailsFragment details = new PackageDetailsFragment();
			details.setArguments(getIntent().getExtras());
			getSupportFragmentManager().beginTransaction()
					.add(android.R.id.content, details).commit();
		}
	}
}
