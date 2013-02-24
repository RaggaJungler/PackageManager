package com.example.packagemanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitiesList extends Activity {

	final String packageName = "packageName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		final String packName = intent.getStringExtra(packageName);
		setContentView(R.layout.activity_list);
		TextView tvActList = (TextView) findViewById(R.id.tvActivitiesList);
		tvActList.setText("Activities of " + packName + ": ");
		ListView lvActivities = (ListView) findViewById(R.id.lvActivities);
		Context context = this.getApplicationContext();
		ActivityInfo[] activities = new ActivityInfo[0];
		try {
			activities = context.getPackageManager().getPackageInfo(packName,
					PackageManager.GET_ACTIVITIES).activities;
		} catch (NameNotFoundException e) {
			// DOing smthing
		}
		ArrayAdapter<String> activitiesAdapter = new ArrayAdapter<String>(
				context, R.layout.activity_item,
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
