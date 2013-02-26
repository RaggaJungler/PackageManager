package com.example.packagemanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PackagesAdapter extends BaseAdapter {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<ApplicationInfo> mData;

	public PackagesAdapter(Context context, List<ApplicationInfo> data) {
		mContext = context;
		mData = (ArrayList<ApplicationInfo>) data;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public ApplicationInfo getAppInfo(int index) {
		return (ApplicationInfo) getItem(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View view;
		ApplicationInfo appInfo = getAppInfo(position);
		PackageManager pm = mContext.getPackageManager();
		if (convertView == null) {
			view = mInflater.inflate(R.layout.packageitem, parent, false);
			holder = new ViewHolder((ImageView) view.findViewById(R.id.ivIcon),
					(TextView) view.findViewById(R.id.tvPackName));
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		try {
			holder.ivIcon.setImageDrawable(pm.getApplicationIcon(appInfo.packageName));
		} catch (NameNotFoundException e) {
			holder.ivIcon.setImageDrawable(null);
		}
		holder.tvPackageName.setText(appInfo.packageName);
		return view;
	}

	private static final class ViewHolder {
		public final ImageView ivIcon;
		public final TextView tvPackageName;

		public ViewHolder(ImageView icon, TextView packName) {
			this.ivIcon = icon;
			this.tvPackageName = packName;
		}

	}
}
