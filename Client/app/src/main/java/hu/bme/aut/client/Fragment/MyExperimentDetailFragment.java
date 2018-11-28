package hu.bme.aut.client.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.Model.ExperimentDTO;
import hu.bme.aut.client.Network.NetworkManager;
import hu.bme.aut.client.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyExperimentDetailFragment extends Fragment {

    private String accessToken;
    private Experiment experiment;

    @BindView(R.id.myexp_author_tv)
    TextView authorTv;
    @BindView(R.id.myexp_name_tv)
    TextView nameTv;
    @BindView(R.id.myexp_startdate_tv)
    TextView startdateTv;


    public MyExperimentDetailFragment() {
        // Required empty public constructor
    }

    public static MyExperimentDetailFragment newInstance(Experiment experiment) {
        MyExperimentDetailFragment fragment = new MyExperimentDetailFragment();
        fragment.experiment = experiment;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_experiment_detail, container, false);
        ButterKnife.bind(this, view);
        authorTv.setText(experiment.getAuthor());
        nameTv.setText(experiment.getName());
       //startdateTv.setText(experiment.getStartDate().toString());

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
