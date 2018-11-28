package hu.bme.aut.client;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static hu.bme.aut.client.ExperimentActivty.KEY_CREATE_EXPERIMENT;
import static hu.bme.aut.client.ExperimentActivty.KEY_LIST_EXPERIMENT;
import static hu.bme.aut.client.ExperimentActivty.KEY_OPEN_FRAGMENT;
import static hu.bme.aut.client.LoginActivity.ACCESS_TOKEN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tvNewExperiment)
    TextView tvNewExperiment;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvSearchExperiment)
    TextView tvSearchExperiment;
    @BindView(R.id.loginFrameLayout)
    FrameLayout logFrameLayout;
    @BindView(R.id.myExperimentsFrameLayout)
    FrameLayout myExperimentsFL;
    @BindView(R.id.newExperimentFrameLayout)
    FrameLayout newExperimentFrameLayout;
    @BindView(R.id.logoutFrameLayout)
    FrameLayout logoutFrameLayout;
    @BindView(R.id.tvMyExperiments)
    TextView tvMyExperiments;
    @BindView(R.id.logoutTv)
    TextView tvLogout;
    @BindView(R.id.tvSettings)
    TextView tvSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvNewExperiment.setOnClickListener(this);
        tvSearchExperiment.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvMyExperiments.setOnClickListener(this);
        tvLogout.setOnClickListener(this);
        tvSettings.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPref.getString(ACCESS_TOKEN, null);
        if(accessToken != null){
            logoutFrameLayout.setVisibility(View.VISIBLE);
            logFrameLayout.setVisibility(View.GONE);
            myExperimentsFL.setVisibility(View.VISIBLE);
            newExperimentFrameLayout.setVisibility(View.VISIBLE);
        }else{
            logoutFrameLayout.setVisibility(View.GONE);
            logFrameLayout.setVisibility(View.VISIBLE);
            myExperimentsFL.setVisibility(View.GONE);
            newExperimentFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tvNewExperiment:
                intent = new Intent(MainActivity.this, ExperimentActivty.class);
                intent.putExtra(KEY_OPEN_FRAGMENT, KEY_CREATE_EXPERIMENT);
                startActivity(intent);
                break;
            case R.id.tvSearchExperiment:
                intent = new Intent(MainActivity.this, ExperimentActivty.class);
                intent.putExtra(KEY_OPEN_FRAGMENT, KEY_LIST_EXPERIMENT);
                startActivity(intent);
                break;
            case R.id.tvLogin:
                intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.tvMyExperiments:
                intent = new Intent(MainActivity.this, MyExperimentActivity.class);
                startActivity(intent);
                break;
            case R.id.logoutTv:
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(ACCESS_TOKEN, null);
                editor.apply();
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tvSettings:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
