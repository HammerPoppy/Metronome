package hammak.metronome;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        SetPeriodDialog.OnCompleteListener, SetTPMDialog.OnCompleteListener, SavePrefFragment.OnAddPrefListener {

    TextView tvPeriodText, tvPeriodValue, tvTPMText, tvTPMValue;
    Button bStartMetronome, bSetPeriod, bSetTPM, bAdd, bLoad;
    CheckBox cbSound, cbVibration;
    SavePrefFragment savePrefFragment;
    FragmentTransaction fTrans;


    SharedPreferences prefList;
    SharedPreferences sp;

    long periodMSec = 1000;
    boolean shouldTick;
    boolean shouldVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPeriodText = (TextView) findViewById(R.id.tvPeriodText);
        tvPeriodValue = (TextView) findViewById(R.id.tvPeriodValue);
        tvTPMText = (TextView) findViewById(R.id.tvTPMText);
        tvTPMValue = (TextView) findViewById(R.id.tvTPMValue);

        bStartMetronome = (Button) findViewById(R.id.bStartMetronome);
        bSetPeriod = (Button) findViewById(R.id.bSetPeriod);
        bSetTPM = (Button) findViewById(R.id.bSetTPM);
        bAdd = (Button) findViewById(R.id.bAdd);
        bLoad = (Button) findViewById(R.id.bLoad);

        bStartMetronome.setOnClickListener(this);
        bSetPeriod.setOnClickListener(this);
        bSetTPM.setOnClickListener(this);
        bAdd.setOnClickListener(this);
        bLoad.setOnClickListener(this);

        cbSound = (CheckBox) findViewById(R.id.cbSound);
        cbVibration = (CheckBox) findViewById(R.id.cbVibration);
        cbSound.setChecked(true);

        Intent fromListView = getIntent();
        if(fromListView.hasExtra("prefName")) {
            loadPref(fromListView.getStringExtra("prefName"));
        }

        savePrefFragment = new SavePrefFragment();

        prefList = getSharedPreferences("prefsName", MODE_PRIVATE);
    }

    private void loadPref(String prefName) {
        sp = getSharedPreferences(prefName, MODE_PRIVATE);
        periodMSec = (long) (sp.getFloat("Period", (float) 1.0) * 1000);
        tvPeriodValue.setText(Float.toString(sp.getFloat("Period", (float) 1.0)));
        tvTPMValue.setText(sp.getString("TPM", "60"));
        cbSound.setChecked(sp.getBoolean("Sound", true));
        cbVibration.setChecked(sp.getBoolean("Vibration", false));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bStartMetronome:
                Intent toMetronomeActivity = new Intent(this, MetronomeActivity.class);
                shouldTick = cbSound.isChecked();
                shouldVibrate = cbVibration.isChecked();
                toMetronomeActivity.putExtra("shouldTick", shouldTick);
                toMetronomeActivity.putExtra("shouldVibrate", shouldVibrate);
                toMetronomeActivity.putExtra("periodMSec", periodMSec);
                startActivity(toMetronomeActivity);
                break;
            case R.id.bSetPeriod:
                DialogFragment setPeriodDialog = new SetPeriodDialog();
                setPeriodDialog.show(getFragmentManager(), setPeriodDialog.getClass().getName());
                break;
            case R.id.bSetTPM:
                DialogFragment setTPMDialog = new SetTPMDialog();
                setTPMDialog.show(getFragmentManager(), setTPMDialog.getClass().getName());
                break;
            case R.id.bAdd:
                fTrans = getFragmentManager().beginTransaction();
                fTrans.add(R.id.flContainer, savePrefFragment);
                fTrans.addToBackStack(null);
                fTrans.commit();
                break;
            case R.id.bLoad:
                Intent toListActivity = new Intent(this, ListActivity.class);

                HashSet<String> prefNameList = (HashSet<String>) prefList
                        .getStringSet("prefNameList", null);

                String[] prefNameArray = {};
                if (prefNameList != null) {
                    prefNameArray = prefNameList.toArray(new String[prefNameList.size()]);
                }

                toListActivity.putExtra("prefNameArray", prefNameArray);
                startActivity(toListActivity);
                finish();
                break;
        }
    }

    @Override
    public void onComplete(long periodMSec) {
        setPeriodAndTPM(periodMSec);
    }

    @Override
    public void onComplete(int TPM) {
        long periodMSec = (long) 60000 / TPM;
        setPeriodAndTPM(periodMSec);
    }

    public void setPeriodAndTPM(long periodMSec){
        this.periodMSec = periodMSec;

        double doublePeriodSec = (double) periodMSec / 1000;
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        tvPeriodValue.setText(df.format(doublePeriodSec));

        long tpm = 60000 / periodMSec;
        tvTPMValue.setText(Long.toString(tpm));
    }

    @Override
    public void addPref(String prefName) {
        SharedPreferences.Editor e = prefList.edit();

        HashSet<String> prefNameList = (HashSet<String>) prefList.getStringSet("prefNameList", null);
        if (prefNameList == null){
            prefNameList = new HashSet<>();
        }

        String[] prefNameArray = prefNameList.toArray(new String[prefNameList.size() + 1]);

        prefNameArray[prefNameArray.length - 1] = prefName;

        prefNameList = new HashSet<>(Arrays.asList(prefNameArray));

        e.putStringSet("prefNameList", prefNameList);
        e.apply();
        
        savePreferences(prefName);
    }

    private void savePreferences(String prefName) {
        sp = getSharedPreferences(prefName, MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();

        float period = (float) Float.parseFloat(tvPeriodValue.getText().toString().replace(",", "."));
        e.putFloat("Period", period);
        e.putString("TPM", (String) tvTPMValue.getText());

        e.putBoolean("Sound", cbSound.isChecked());
        e.putBoolean("Vibration", cbVibration.isChecked());

        e.apply();;
    }


}
