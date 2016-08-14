package hammak.metronome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvPeriod;
    NumberPicker npPeriod;
    Button bStartMetronome;
    PeriodAlertDialog dfPeriodAlert;

    int periodSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPeriod = (TextView) findViewById(R.id.tvPeriod);
        bStartMetronome = (Button) findViewById(R.id.bStartMetronome);

        npPeriod = (NumberPicker) findViewById(R.id.npPeriod);

        //Добавлление от студии
        assert npPeriod != null;

        npPeriod.setMinValue(1);
        npPeriod.setMaxValue(600);


        //Добавление от студии
        assert bStartMetronome != null;

        bStartMetronome.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        Intent toMetronomeActivity = new Intent(this, MetronomeActivity.class);
        periodSec = npPeriod.getValue();
        toMetronomeActivity.putExtra("periodSec", periodSec);
        startActivity(toMetronomeActivity);
    }
}
