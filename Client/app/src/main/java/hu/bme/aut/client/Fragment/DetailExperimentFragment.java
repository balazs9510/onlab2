package hu.bme.aut.client.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.R;
import hu.bme.aut.client.Service.ExperimentService;


public class DetailExperimentFragment extends Fragment {
    private static final String EXPERIMENT_ID = "EXPERIMENT_ID";
    private String expId;
    private Experiment experiment;
    private ExperimentService mExperimentService;
    @BindView(R.id.tv_details_autor)
    TextView tvAuthor;
    @BindView(R.id.tv_details_desc)
    TextView tvDesc;
    @BindView(R.id.tv_details_name)
    TextView tvName;
    @BindView(R.id.tv_details_state)
    TextView tvState;

    public DetailExperimentFragment() {
        mExperimentService = new ExperimentService();
    }

    public static DetailExperimentFragment newInstance(String experimentId) {
        DetailExperimentFragment fragment = new DetailExperimentFragment();
        Bundle args = new Bundle();
        args.putString(EXPERIMENT_ID, experimentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            expId = getArguments().getString(EXPERIMENT_ID);
            experiment = mExperimentService.getExperimnet(expId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_experiment, container, false);
        ButterKnife.bind(this, view);
        setViewData();
        return view;
    }


    private void setViewData(){
        tvAuthor.setText(experiment.getAuthor());
        tvName.setText(experiment.getName());
        tvDesc.setText(experiment.getDescription());
        tvState.setText(experiment.getState());
    }
}
