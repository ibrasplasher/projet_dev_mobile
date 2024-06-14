package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.model.TaskModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;



public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private RecyclerViewInterface recyclerViewInterface;
    private ArrayList<TaskModel> taskDataset;
    private ArrayList<TaskModel> completeDataList; // Liste complète des tâches
    private Context context;
    private String statutSelectionne;
    public TaskModel getTaskAt(int position) {
        return taskDataset.get(position);
    }

    public TaskListAdapter(Context context, ArrayList<TaskModel> taskDataset, ArrayList<TaskModel> completeDataList, RecyclerViewInterface recyclerViewInterface) {
        this.taskDataset = taskDataset;
        this.completeDataList = new ArrayList<>(completeDataList); // Initialisation de completeDataList
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
        this.statutSelectionne = "";
    }

    public void setStatutSelectionne(String statut) {
        this.statutSelectionne = statut;
        notifyDataSetChanged();
    }

    public void setDataList(ArrayList<TaskModel> newDataList) {
        this.taskDataset = new ArrayList<>(newDataList);
        this.completeDataList = new ArrayList<>(newDataList); // Mettez également à jour completeDataList
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskNameTv, taskStatusTv;
        private ImageView cercleIv;
        private MaterialCardView taskCv;

        public ViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            taskNameTv = view.findViewById(R.id.taskNameTv);
            taskStatusTv = view.findViewById(R.id.taskStatusTv);
            cercleIv = view.findViewById(R.id.cercleIv);
            taskCv = view.findViewById(R.id.taskCv);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_task, viewGroup, false);
        return new ViewHolder(view, recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        TaskModel task = taskDataset.get(position);
        viewHolder.taskNameTv.setText(task.getTaskName());
        viewHolder.taskStatusTv.setText(task.getTaskstatus());

        String status = task.getTaskstatus();

        if (status != null) {
            String statusLowerCase = status.toLowerCase().replace(" ", "");
            viewHolder.taskStatusTv.setText(statusLowerCase);

            switch (statusLowerCase) {
                case "2":
                    viewHolder.cercleIv.setImageResource(R.drawable.gros_cercle);
                    viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.gris2));
                    break;
                case "1":
                    viewHolder.cercleIv.setImageResource(R.drawable.task_done);
                    viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.vert));
                    break;
                case "0":
                    viewHolder.cercleIv.setImageResource(R.drawable.task_in_progress);
                    viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.bleu));
                    break;
                case "3":
                    viewHolder.cercleIv.setImageResource(R.drawable.task_bug);
                    viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.rouge));
                    break;
            }
        }

        // Gestion du clic sur un élément
        viewHolder.itemView.setOnClickListener(v -> {
            if (recyclerViewInterface != null) {
                recyclerViewInterface.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskDataset.size();
    }

    public void filtrerParStatut(String statut) {
        ArrayList<TaskModel> filteredList = new ArrayList<>();
        if (statut.isEmpty()) {
            filteredList.addAll(completeDataList); // Restaurer la liste complète
        } else {
            for (TaskModel task : completeDataList) {
                if (statut.equals(task.getTaskstatus())) {
                    filteredList.add(task);
                }
            }
        }
        setDataList(filteredList);
    }
    public void updateTask(int position, TaskModel updatedTask) {
        if (position >= 0 && position < taskDataset.size()) {
            taskDataset.set(position, updatedTask);
            notifyItemChanged(position);
        }
    }
}
