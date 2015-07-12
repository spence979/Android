package test.athena;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public MySimpleArrayAdapter(Context context, String[] values) {
		super(context, R.layout.custom_list, values);
		this.context = context;
		this.values = values;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.mainmenu_layout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.first_row);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(values[position]);
		// Change the icon for Windows and iPhone
		String s = values[position];
		if (s.startsWith("Patient") )
		{
			imageView.setImageResource(R.drawable.home);
		} 
		else if(s.startsWith("Appointment"))
		{
			imageView.setImageResource(R.drawable.appointment);
		}
		else if(s.startsWith("Administration"))
		{
			imageView.setImageResource(R.drawable.settings);
		}
		else if(s.startsWith("Logout"))
		{
			imageView.setImageResource(R.drawable.exit);
		}
		else {
			imageView.setImageResource(R.drawable.logo);
		}

		return rowView;
	}
}
