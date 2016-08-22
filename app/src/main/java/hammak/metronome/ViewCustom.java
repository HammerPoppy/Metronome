package hammak.metronome; /*
 *Created by Hammak on 21.08.2016 for Metronome.
*/

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewCustom extends LinearLayout {

    TextView tvPrefName;

    public ViewCustom(Context context) {
        super(context);
    }

    public ViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout llRoot = (LinearLayout) inflater.inflate(R.layout.custom_view, this, true);

        tvPrefName = (TextView) llRoot.findViewById(R.id.tvPrefName);
    }
}
