package hu.bme.aut.client;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.aut.client.Fragment.Camera2BasicFragment;
import hu.bme.aut.client.Fragment.DetailExperimentFragment;
import hu.bme.aut.client.Fragment.MyExperimentDetailFragment;
import hu.bme.aut.client.Helpers.TimeHelper;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.Model.ExperimentDTO;
import hu.bme.aut.client.Model.Time;
import hu.bme.aut.client.Network.NetworkManager;
import hu.bme.aut.client.Preference.TimePreference;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hu.bme.aut.client.LoginActivity.ACCESS_TOKEN;

public class MyExperimentDetailActivity extends AppCompatActivity  implements Camera2BasicFragment.OnImageTakenListener{
    public final static String EXPERIMENTID = "EXPERIMENTID";
    private static final String TAG = "MyExpDActivity" ;
    private Experiment experiment;
    @BindView(R.id.myexp_start_button)
    Button btnStartRecord;
    private Camera2BasicFragment camera2BasicFragment;
    private Handler handler = new Handler();
    private long periodMillisecond;
    private boolean isRunning = false;
    private NetworkManager networkManager;
    private String experimentId;
    private String accessToken;

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
        setContentView(R.layout.activity_my_experiment_detail);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        ButterKnife.bind(this);
        accessToken = sharedPref.getString(ACCESS_TOKEN, null);
        experimentId = getIntent().getStringExtra(EXPERIMENTID);
        networkManager = NetworkManager.getInstance();
        networkManager.getExperiment(accessToken, experimentId).enqueue(new Callback<ExperimentDTO>() {
            @Override
            public void onResponse(Call<ExperimentDTO> call, Response<ExperimentDTO> response) {
                experiment = response.body().toExperiment();
                MyExperimentDetailFragment detailExperimentFragment = MyExperimentDetailFragment.newInstance(experiment);
                getSupportFragmentManager().beginTransaction().replace(R.id.myexperiment_detail_fl, detailExperimentFragment).commit();
                if (response.body().getState() != 1) {
                    btnStartRecord.setVisibility(View.GONE);
                } else {
                    camera2BasicFragment = Camera2BasicFragment.newInstance();
                    camera2BasicFragment.setListener(MyExperimentDetailActivity.this);
                    getSupportFragmentManager().beginTransaction().replace(R.id.camera_fl, camera2BasicFragment).commit();
                }
            }

            @Override
            public void onFailure(Call<ExperimentDTO> call, Throwable t) {
                Snackbar.make(findViewById(R.id.detailcontent), getString(R.string.cannot_load_exp), Snackbar.LENGTH_LONG).show();
            }
        });
    }
    @OnClick(R.id.myexp_start_button)
    public void startRecord(){
        if(!isRunning){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String periodTime = preferences.getString(TimePreference.KEY_PERIOD_TIME, "0:0:10");
            Time time = TimeHelper.GetTimeFromString(periodTime);
            periodMillisecond = TimeHelper.GetMilllisecondFromTime(time);
            handler.post(runnableCode);
            btnStartRecord.setText(R.string.stopRecording);
            isRunning = true;
        }else {
            handler.removeCallbacksAndMessages(null);
            networkManager.stopExperiment(accessToken, experimentId).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        btnStartRecord.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().remove(camera2BasicFragment).commit();
                        Snackbar.make(findViewById(R.id.detailcontent), R.string.end_Exp, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void ImageTaken(String filePath, String fileName) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPref.getString(ACCESS_TOKEN, null);
        networkManager.uploadImage(accessToken, experiment.get_id(), Uri.fromFile(new File(filePath))).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "Sikeres kép feltöltés");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });
    }
}
