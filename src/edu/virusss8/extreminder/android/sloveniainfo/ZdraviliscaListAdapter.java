package edu.virusss8.extreminder.android.sloveniainfo;

import java.util.List;

import edu.virusss8.extreminder.android.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ZdraviliscaListAdapter extends ArrayAdapter<String> {
	LayoutInflater mInflater;

	public ZdraviliscaListAdapter(Context context, int textViewResourceId, List<String> obj) {
		super(context, textViewResourceId, obj);
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String tmp = getItem(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.seznam_layout, null);
			holder = new ViewHolder();
			holder.one = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		} 
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.one.setText(tmp);
		Drawable img = getContext().getResources().getDrawable(showLogoPic(position));
		img.setBounds(0, 0, 200, 100);
		holder.one.setCompoundDrawables(null, null, null, img);
		return convertView;
	}
	
	public int showLogoPic(int pos) {
		switch(pos) {
		case 0:
			return R.drawable.strunjan;
		case 1:
			return R.drawable.portoroz;
		case 2:
			return R.drawable.moravske;
		case 3:
			return R.drawable.catez;
		case 4:
			return R.drawable.dobrna;
		case 5:
			return R.drawable.dolenjske;
		case 6:
			return R.drawable.lendava;
		case 7:
			return R.drawable.podcetrtek;
		case 8:
			return R.drawable.ptuj;
		case 9:
			return R.drawable.smarjeske;
		case 10:
			return R.drawable.topolsica;
		case 11:
			return R.drawable.zrece;
		case 12:
			return R.drawable.lasko;
		case 13:
			return R.drawable.radenci;
		case 14:
			return R.drawable.rogaska;
		default:
			return 0;
		}
	}
	
	static class ViewHolder {
		TextView one;
	}
}
