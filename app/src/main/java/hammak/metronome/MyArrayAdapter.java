package hammak.metronome; /*
 *Created by Hammak on 21.08.2016 for Metronome.
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String>{

    Context context;
    String[] prefNameArray;

    public MyArrayAdapter(Context context, String[] prefNameArray) {
        super(context, R.layout.custom_view, prefNameArray);
        this.context = context;
        this.prefNameArray = prefNameArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_view, parent, false);
        TextView tvPrefName = (TextView) customView.findViewById(R.id.tvPrefName);

        tvPrefName.setText(prefNameArray[position]);

        return customView;
    }
}
