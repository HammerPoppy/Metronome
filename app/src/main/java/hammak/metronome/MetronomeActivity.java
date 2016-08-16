package hammak.metronome;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MetronomeActivity extends AppCompatActivity implements OnClickListener {

    Button bStop;

    float periodSec;
    boolean shouldTick;
    SoundPool sp;

    Thread metronome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metronome);

        bStop = (Button) findViewById(R.id.bStop);
        if (bStop != null)
            bStop.setOnClickListener(this);

        Intent fromMainActivity = getIntent();
        periodSec = fromMainActivity.getFloatExtra("periodSec", 2);

        shouldTick = true;

        final long periodMSec = (long) periodSec * 1000;

        metronome = new Thread(new Runnable() {
            @Override
            public void run() {
                sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 1);
                Context mContext = getApplicationContext();
                final int soundId = sp.load(mContext, R.raw.metronome_click_hq, 1);

                while (shouldTick) {
                    try {sp.play(soundId, 1, 1, 0, 0, 1);
                        Thread.sleep(periodMSec);
                    }
                    catch (InterruptedException e) {
                        return;
                    }
                }
            }
        });
        metronome.start();
    }

    @Override
    public void onClick(View v) {
        shouldTick = false;
        metronome.interrupt();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        metronome.interrupt();
    }
}
