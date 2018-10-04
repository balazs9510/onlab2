package hu.bme.aut.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static hu.bme.aut.client.ExperimentActivty.KEY_CREATE_EXPERIMENT;
import static hu.bme.aut.client.ExperimentActivty.KEY_OPEN_FRAGMENT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.tvNewExperiment)
    TextView tvNewExperiment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvNewExperiment.setOnClickListener(this);
        //Todo többire is onclick
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvNewExperiment:
                Intent intent = new Intent(MainActivity.this, ExperimentActivty.class);
                intent.putExtra(KEY_OPEN_FRAGMENT, KEY_CREATE_EXPERIMENT);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
