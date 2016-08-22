package hammak.metronome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListActivity extends android.app.ListActivity {

    ListView list;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = this;

        Intent fromMainActivity = getIntent();

        final String[] prefNameArray = fromMainActivity.getStringArrayExtra("prefNameArray");

        ListView list = (ListView) findViewById(android.R.id.list);
        MyArrayAdapter adapter = new MyArrayAdapter(this, prefNameArray);
        setListAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String prefName = prefNameArray[position];

                Intent toMainActivity = new Intent(context, MainActivity.class);

                toMainActivity.putExtra("prefName", prefName);
                startActivity(toMainActivity);
                finish();
            }
        });
    }
}
