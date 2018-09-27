package hu.bme.aut.physicexperiment.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import hu.bme.aut.physicexperiment.Model.Experiment;
import hu.bme.aut.physicexperiment.R;

public class CreateExperimentFragment extends DialogFragment {

    private OnExperimentCreateListener mListener;

    public CreateExperimentFragment() {
    }

    public static CreateExperimentFragment newInstance() {
        CreateExperimentFragment fragment = new CreateExperimentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                //.setIcon(R.drawable.alert_dialog_icon)
                .setView(createView(getActivity().getLayoutInflater()))
                .setTitle(R.string.create_experiment)
                .setPositiveButton(R.string.Create,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                mListener.onExperimentCreate(new Experiment());
                                dismiss();
                            }
                        }
                )
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                )
                .create();
    }

    private View createView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fragment_create_experiment, null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExperimentCreateListener) {
            mListener = (OnExperimentCreateListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnExperimentCreateListener {
        void onExperimentCreate(Experiment experiment);
    }
}
