package hammak.metronome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MetronomeActivity extends AppCompatActivity {

    int periodSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);
        // TODO: 14.08.2016 New tread for sounds and delays

        Intent fromMainActivity = getIntent();

        periodSec = fromMainActivity.getIntExtra("periodSec", 2);
    }
}
