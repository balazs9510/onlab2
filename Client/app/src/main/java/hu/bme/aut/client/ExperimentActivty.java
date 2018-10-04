package hu.bme.aut.client;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.client.Fragment.CreateExperimentFragment;
import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Network.NetworkManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExperimentActivty extends AppCompatActivity
        implements CreateExperimentFragment.OnExperimentChangeListener {
    private static final String TAG = "ExperimentActivty";
    public static final String KEY_OPEN_FRAGMENT = "KEY_OPEN_FRAGMENT";
    public static final String KEY_CREATE_EXPERIMENT = "KEY_CREATE_EXPERIMENT";

    @BindView(R.id.experiment_framelayout)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_activty);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String intentValue = intent.getStringExtra(KEY_OPEN_FRAGMENT);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.experiment_framelayout, getFragment(intentValue)).commit();
    }

    private Fragment getFragment(String intentValue) {
        Fragment fragment;
        switch (intentValue) {
            case KEY_CREATE_EXPERIMENT:
                fragment = CreateExperimentFragment.newInstance(false);
                break;
            default:
                fragment = CreateExperimentFragment.newInstance(false);
                break;
        }
        return fragment;
    }


    @Override
    public void onExperimentCreated(CreateExperimentDTO experiment) {
        //Todo befejezni
        NetworkManager networkManager = NetworkManager.getInstance();
        networkManager.postExperiment(experiment).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG,t.toString());
                Snackbar.make(frameLayout, "Váratlan hiba  kísérlet létrehozása során.", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onExperimentEdit() {
        //TODO
    }
}
