package com.example.packagemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitiesList extends Activity {

	final static String PACKAGE_NAME = "packageName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		final String packName = intent.getStringExtra(PACKAGE_NAME);
		setContentView(R.layout.activity_list);
		TextView tvActList = (TextView) findViewById(R.id.tvActivitiesList);
		tvActList.setText("Activities of " + packName + ": ");
		ListView lvActivities = (ListView) findViewById(R.id.lvActivities);
		ActivityInfo[] activities = new ActivityInfo[0];
		try {
			activities = getApplicationContext().getPackageManager()
					.getPackageInfo(packName, PackageManager.GET_ACTIVITIES).activities;
		} catch (NameNotFoundException e) {
			// Doing smthing
		}
		ArrayAdapter<String> activitiesAdapter = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.activity_item,
				activityInfoToString(activities));
		lvActivities.setAdapter(activitiesAdapter);
	}

	private String[] activityInfoToString(ActivityInfo[] activities) {
		if (activities == null) {
			throw new NullPointerException();
		}
		ArrayList<String> result = new ArrayList<String>();
		for (ActivityInfo activityItem : activities) {
			result.add(activityItem.name);
		}
		return result.toArray(new String[result.size()]);
	}

}
