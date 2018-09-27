package hu.bme.aut.physicexperiment;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.physicexperiment.Fragment.Camera2BasicFragment;
import hu.bme.aut.physicexperiment.Fragment.CreateExperimentFragment;
import hu.bme.aut.physicexperiment.Helpers.TimeHelper;
import hu.bme.aut.physicexperiment.Model.Experiment;
import hu.bme.aut.physicexperiment.Model.GalleryInteractor;
import hu.bme.aut.physicexperiment.Model.Time;
import hu.bme.aut.physicexperiment.Preference.TimePreference;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements Camera2BasicFragment.OnImageTakenListener, CreateExperimentFragment.OnExperimentCreateListener {
    public static final String TMP_IMAGE_JPG = "/tmp_image.jpg";
    private static final String TAG = "MainActivity";
    private final int REQUEST_CAMERA_IMAGE = 101;
    /*@BindView(R.id.startRecordTv)
    TextView startRecordTextView;*/
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    private Camera2BasicFragment camera2BasicFragment;
    private Handler handler = new Handler();
    private long periodMillisecond;
    private boolean isRunning = false;
    private String IMAGE_PATH;
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

        //camera2BasicFragment = Camera2BasicFragment.newInstance();
        //camera2BasicFragment.setListener(this);
        //getSupportFragmentManager().beginTransaction()
        //        .replace(R.id.container, camera2BasicFragment)
        //       .commit();

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
                // User chose the "Settings" item, show the app settings UI...
                Intent i = new Intent(this, SettingActivity.class);
                startActivity(i);
            case R.id.action_create_experiment:
                CreateExperimentFragment fragment = CreateExperimentFragment.newInstance();
                fragment.show(getSupportFragmentManager(), "dialog");

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // @OnClick(R.id.startRecordTv)
    public void recordButtonOnClick() {
        /*if (!isRunning) {
            startRecording();
            startRecordTextView.setText(R.string.stopRecord);
        } else {
            stopRecording();
            startRecordTextView.setText(R.string.startRecord);
        }*/
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

    @Override
    public void ImageTaken(String filePath) {
        IMAGE_PATH = filePath;
        final GalleryInteractor galleryInteractor = new GalleryInteractor(MainActivity.this);

        String name = "testImage";
        String description = "testImage";
        galleryInteractor.getString(new GalleryInteractor.ResponseListener<ResponseBody>() {
            @Override
            public void onResponse(ResponseBody responseBody) {
                Log.d(TAG, "Response");
               // finish();
            }

            @Override
            public void onError(Exception e) {
                finish();
            }
        });
//        galleryInteractor.uploadImage(Uri.fromFile(new File(IMAGE_PATH)), name, description, new GalleryInteractor.ResponseListener<ResponseBody>() {
//            @Override
//            public void onResponse(ResponseBody responseBody) {
//               // Toast.makeText(MainActivity.this, "Successfully uploaded!", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            public void onError(Exception e) {
//               // Toast.makeText(MainActivity.this, "Error during uploading photo!", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//            }
//        });
    }

    @Override
    public void onExperimentCreate(Experiment experiment) {
        Log.d(TAG, "Experiment created");
    }
}
