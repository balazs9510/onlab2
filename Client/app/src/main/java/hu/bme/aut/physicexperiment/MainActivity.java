package hu.bme.aut.physicexperiment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.aut.physicexperiment.Fragment.Camera2BasicFragment;
import hu.bme.aut.physicexperiment.Fragment.CreateExperimentFragment;
import hu.bme.aut.physicexperiment.Helpers.TimeHelper;
import hu.bme.aut.physicexperiment.Model.Experiment;
import hu.bme.aut.physicexperiment.Model.GalleryInteractor;
import hu.bme.aut.physicexperiment.Model.Time;
import hu.bme.aut.physicexperiment.Preference.TimePreference;
import hu.bme.aut.physicexperiment.Services.UploadService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Camera2BasicFragment.OnImageTakenListener, CreateExperimentFragment.OnExperimentCreateListener {
    public static final String TMP_IMAGE_JPG = "/tmp_image.jpg";
    private static final String TAG = "MainActivity";
    private final int REQUEST_CAMERA_IMAGE = 101;
    @BindView(R.id.startRecordTv)
    TextView startRecordTextView;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    private Camera2BasicFragment camera2BasicFragment;
    private Handler handler = new Handler();
    private long periodMillisecond;
    private boolean isRunning = false;
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            Log.d(TAG, "Handler called captureStillPicture on fragment");
            Log.d(TAG, "Time : " + date);
            camera2BasicFragment.captureStillPicture();

            handler.postDelayed(this, periodMillisecond);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(myToolbar);

        camera2BasicFragment = Camera2BasicFragment.newInstance();
        camera2BasicFragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, camera2BasicFragment)
               .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
            case R.id.action_create_experiment:
                CreateExperimentFragment fragment = CreateExperimentFragment.newInstance();
                fragment.show(getSupportFragmentManager(), "dialog");

            default:
                return super.onOptionsItemSelected(item);

        }
    }

     @OnClick(R.id.startRecordTv)
    public void recordButtonOnClick() {
        if (!isRunning) {
            startRecording();
            startRecordTextView.setText(R.string.stopRecord);
        } else {
            stopRecording();
            startRecordTextView.setText(R.string.startRecord);
        }
    }

    private void stopRecording() {
        isRunning = false;
        handler.removeCallbacksAndMessages(null);
    }

    private void startRecording() {
        isRunning = true;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String periodTime = preferences.getString(TimePreference.KEY_PERIOD_TIME, "0:0:10");
        Time time = TimeHelper.GetTimeFromString(periodTime);
        periodMillisecond = TimeHelper.GetMilllisecondFromTime(time);
        handler.post(runnableCode);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int resultCode = bundle.getInt(UploadService.RESULT);
                boolean isSucces = bundle.getBoolean(UploadService.IS_SUCCESS);
                if (isSucces)
                    Toast.makeText(MainActivity.this, "File upload was successful", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "File upload failure, response: " + resultCode, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                UploadService.NOTIFICATION));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    public void ImageTaken(String filePath, String fileName) {
        UploadService uploadService = new UploadService();
        uploadService.startActionImageTaken(MainActivity.this, filePath, fileName);

    }

    @Override
    public void onExperimentCreate(Experiment experiment) {
        Log.d(TAG, "Experiment created");
        GalleryInteractor galleryInteractor = new GalleryInteractor();
        galleryInteractor.createExperiment(experiment).enqueue(new Callback<Experiment>() {
            @Override
            public void onResponse(Call<Experiment> call, Response<Experiment> response) {
                Log.d(TAG, "asd");
            }

            @Override
            public void onFailure(Call<Experiment> call, Throwable t) {
                Log.d(TAG, "asd");
            }
        });
    }
}
