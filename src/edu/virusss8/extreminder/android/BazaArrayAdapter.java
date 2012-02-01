package edu.virusss8.extreminder.android;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BazaArrayAdapter extends ArrayAdapter<DataBase> {
	LayoutInflater mInflater;
	
	public BazaArrayAdapter(Context context, int textViewResourceId, List<DataBase> objects) {
		super(context, textViewResourceId, objects);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		DataBase tmp = getItem(position);
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.baza_layout, null);
			holder = new ViewHolder();
			holder.one = (TextView) convertView.findViewById(R.id.tvOne);
			holder.two = (TextView) convertView.findViewById(R.id.tvTwo);
			holder.three = (TextView) convertView.findViewById(R.id.tvThree);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		String temp = null;
		if (tmp.getSt_tablet() > 4)
			temp = " tablet";
		else
			if (tmp.getSt_tablet() == 3 || tmp.getSt_tablet() == 4)
				temp = " tablete";
			else
				if (tmp.getSt_tablet() == 2)
					temp = " tableti";
				else
					if (tmp.getSt_tablet() == 1)
						temp = " tableta";
		
		String tempp = null;
		if (tmp.getEveryXHours() > 4)
			tempp = "vzeti vsakih "+tmp.getEveryXHours()+" ur";
		else
			if (tmp.getEveryXHours() == 3 || tmp.getEveryXHours() == 4)
				tempp = "vzeti vsake "+tmp.getEveryXHours()+" ure";
			else
				if (tmp.getEveryXHours() == 2)
					tempp = "vzeti vsaki "+tmp.getEveryXHours()+" uri";
				else
					if (tmp.getEveryXHours() == 1)
						tempp = "vzeti vsako "+tmp.getEveryXHours()+" uro";
		
		holder.one.setText(""+tmp.getName());
		holder.two.setText(""+tmp.getSt_tablet()+temp);
		holder.three.setText(tempp);
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView one;
		TextView two;
		TextView three;
	}
}
