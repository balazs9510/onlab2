package hu.bme.aut.client.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.client.Model.Experiment;
import hu.bme.aut.client.R;

public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ExperimentViewHolder> {
    private List<Experiment> experiments;

    public ExperimentAdapter(List<Experiment> experimentList) {
        experiments = experimentList;
    }
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExperimentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_experiment, viewGroup, false);
        ExperimentViewHolder vh = new ExperimentViewHolder(root);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExperimentViewHolder experimentViewHolder, int i) {
        Experiment experiment = experiments.get(i);
        experimentViewHolder.tvAuthor.setText(experiment.getAuthor());
        experimentViewHolder.tvName.setText(experiment.getName());
        experimentViewHolder.tvState.setText(experiment.getState());
    }

    @Override
    public int getItemCount() {
        return experiments.size();
    }

    public class ExperimentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.row_tv_author)
        TextView tvAuthor;
        @BindView(R.id.row_tv_name)
        TextView tvName;
        @BindView(R.id.row_tv_state)
        TextView tvState;

        public ExperimentViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

    }

}
