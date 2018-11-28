package hu.bme.aut.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.client.Adapter.ExperimentAdapter;
import hu.bme.aut.client.Fragment.CreateExperimentFragment;
import hu.bme.aut.client.Fragment.DetailExperimentFragment;
import hu.bme.aut.client.Fragment.ListExperimentFragment;
import hu.bme.aut.client.Fragment.ListExperimentFragment.OnExperimentSelectedListner;
import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.Network.NetworkManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExperimentActivty extends AppCompatActivity
        implements CreateExperimentFragment.OnExperimentChangeListener, OnExperimentSelectedListner {
    private static final String TAG = "ExperimentActivty";
    public static final String KEY_OPEN_FRAGMENT = "KEY_OPEN_FRAGMENT";
    public static final String KEY_CREATE_EXPERIMENT = "KEY_CREATE_EXPERIMENT";
    public static final String KEY_LIST_EXPERIMENT = "KEY_LIST_EXPERIMENT";
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
            case KEY_LIST_EXPERIMENT:
                //TODO lehúzni a szerverről
                List<Experiment> experimentList = new ArrayList<>();
                experimentList.add(new Experiment("1", "Csudajó kísérlet", "Test Jhonny", "Folyamatban"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                experimentList.add(new Experiment("2", "Mégjobb kísérlet", "Jhonny Test", "Vége"));
                fragment = ListExperimentFragment.newInstance(experimentList, this);
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
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPref.getString(LoginActivity.ACCESS_TOKEN, null);
        networkManager.postExperiment(accessToken,experiment).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 401){

                    sharedPref.edit().putString(LoginActivity.ACCESS_TOKEN, null).apply();
                    Intent intent = new Intent(ExperimentActivty.this, LoginActivity.class);
                    Log.d(TAG, "Token expired");
                    startActivity(intent);
                }
                if(response.code() == 200){
                    Intent intent = new Intent(ExperimentActivty.this, MainActivity.class);
                    startActivity(intent);
                }
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


    @Override
    public void OnExperimentSelected(Experiment experiment) {
        DetailExperimentFragment detailExperimentFragment = DetailExperimentFragment.newInstance(experiment.get_id());
        getSupportFragmentManager().beginTransaction().replace(R.id.experiment_framelayout, detailExperimentFragment).commit();
    }
}
