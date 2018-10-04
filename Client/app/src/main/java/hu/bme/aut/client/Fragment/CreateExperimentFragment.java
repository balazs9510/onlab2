package hu.bme.aut.client.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hu.bme.aut.client.ExperimentActivty;
import hu.bme.aut.client.Model.CreateExperimentDTO;
import hu.bme.aut.client.R;

public class CreateExperimentFragment extends Fragment {
    private static final String IS_EDIT = "IS_EDIT";
    @BindView(R.id.create_experiment_et_name)
    EditText etName;
    @BindView(R.id.create_experiment_et_authorname)
    EditText etAuthor;
    @BindView(R.id.create_experiment_et_description)
    EditText etDescription;
    @BindView(R.id.create_experiment_dp)
    DatePicker edpEndDate;
    @BindView(R.id.register_tv_cancel)
    TextView tvCancel;
    @BindView(R.id.create_experiment_tv_create)
    TextView tvCreate;
    private boolean mIsEdit;
    private Unbinder unbinder;
    private OnExperimentChangeListener mListener;

    public CreateExperimentFragment() {
        // Required empty public constructor
    }

    public static CreateExperimentFragment newInstance(boolean isEdit) {
        CreateExperimentFragment fragment = new CreateExperimentFragment();
        Bundle args = new Bundle();
        args.putBoolean(IS_EDIT, isEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsEdit = getArguments().getBoolean(IS_EDIT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_experiment, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExperimentChangeListener) {
            mListener = (OnExperimentChangeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @OnClick(R.id.create_experiment_tv_create)
    public void createExperiment() {
        CreateExperimentDTO experiment = new CreateExperimentDTO();
        experiment.setAuthor(etAuthor.getText().toString());
        experiment.setName(etName.getText().toString());
        experiment.setDescription(etDescription.getText().toString());
        Calendar calendar = Calendar.getInstance();
        experiment.setStartDate(calendar.getTime());
        Date selectedDate = new Date(edpEndDate.getYear() - 1900, edpEndDate.getMonth(), edpEndDate.getDayOfMonth());
        experiment.setExpectedEndDate(selectedDate);
        mListener.onExperimentCreated(experiment);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        unbinder.unbind();
    }

    public interface OnExperimentChangeListener {
        void onExperimentCreated(CreateExperimentDTO experiment);

        void onExperimentEdit();
    }
}
