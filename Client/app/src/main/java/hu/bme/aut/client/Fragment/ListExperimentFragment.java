package hu.bme.aut.client.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hu.bme.aut.client.Adapter.ExperimentAdapter;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.R;

public class ListExperimentFragment extends Fragment {
    private ExperimentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.rv_experiments)
    RecyclerView rvExperiments;
    private List<Experiment> experiments;
    private Unbinder unbinder;
    public List<Experiment> getExperiments() {
        return experiments;
    }
    OnExperimentSelectedListner mListener;
    public void setExperiments(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    public ListExperimentFragment() {

    }

    public static ListExperimentFragment newInstance(List<Experiment> experimentList, OnExperimentSelectedListner listener) {
        ListExperimentFragment fragment = new ListExperimentFragment();
        fragment.setExperiments(experimentList);
        fragment.mListener = listener;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_experiment, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvExperiments.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(container.getContext());
        rvExperiments.setLayoutManager(mLayoutManager);
        mAdapter = new ExperimentAdapter(experiments);
        mAdapter.setListener(new ExperimentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                mListener.OnExperimentSelected(experiments.get(position));
            }
        });
        rvExperiments.setAdapter(mAdapter);
        return view;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
        unbinder.unbind();
    }
    public interface OnExperimentSelectedListner{
        void OnExperimentSelected(Experiment experiment);
    }

}
