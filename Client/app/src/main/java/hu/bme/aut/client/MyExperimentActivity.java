package hu.bme.aut.client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.client.Adapter.MyExperimentAdapter;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.Model.ExperimentDTO;
import hu.bme.aut.client.Network.NetworkManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyExperimentActivity extends AppCompatActivity
        implements MyExperimentAdapter.OnItemClickListener {
    @BindView(R.id.myExpRecyclerView)
    RecyclerView recyclerView;
    MyExperimentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_experiment);
        ButterKnife.bind(this);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final String accessToken = sharedPref.getString(LoginActivity.ACCESS_TOKEN, null);
        final NetworkManager networkManager = NetworkManager.getInstance();
        final List<Experiment> myExperiments = new ArrayList<>();
        networkManager.getMyId(accessToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String myId = response.body();
                networkManager.getUserExperiments(accessToken, myId).enqueue(new Callback<List<ExperimentDTO>>() {
                    @Override
                    public void onResponse(Call<List<ExperimentDTO>> call, Response<List<ExperimentDTO>> response) {
                        for (ExperimentDTO exp : response.body()) {
                            myExperiments.add(exp.toExperiment());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ExperimentDTO>> call, Throwable t) {
                        Snackbar.make(recyclerView, "Váratlan hiba a lekérdezés során.", Snackbar.LENGTH_LONG);
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyExperimentAdapter(myExperiments);
        mAdapter.setListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Experiment exp = mAdapter.getExperimentInPosition(position);
        Intent intent = new Intent(MyExperimentActivity.this, MyExperimentDetailActivity.class);
        intent.putExtra(MyExperimentDetailActivity.EXPERIMENTID, exp.get_id());
        startActivity(intent);
    }
}
