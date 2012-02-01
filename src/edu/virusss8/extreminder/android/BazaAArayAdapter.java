package edu.virusss8.extreminder.android;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BazaAArayAdapter extends ArrayAdapter<Alarm> {
	LayoutInflater mInflater;
	String hour = null, minute = null;

	public BazaAArayAdapter(Context context, int textViewResourceId, List<Alarm> objects) {
		super(context, textViewResourceId, objects);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Alarm tmp = getItem(position);
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.alarmi_layout, null);
			holder = new ViewHolder();
			holder.one = (TextView) convertView.findViewById(R.id.tvOneA);
			holder.two = (TextView) convertView.findViewById(R.id.tvTwoA);
			holder.three = (TextView) convertView.findViewById(R.id.tvThreeA);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if (tmp.getHour() < 10)
			hour = "0"+tmp.getHour();
		else hour = ""+tmp.getHour();
		
		if (tmp.getMinute() < 10)
			minute = "0"+tmp.getMinute();
		else minute = ""+tmp.getMinute();
		
		holder.one.setText(""+tmp.getName());
		holder.two.setText("ostanek: "+tmp.getSt_tablet());
		holder.three.setText("Alarm ob: "+hour+":"+minute);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView one;
		TextView two;
		TextView three;
	}
}
