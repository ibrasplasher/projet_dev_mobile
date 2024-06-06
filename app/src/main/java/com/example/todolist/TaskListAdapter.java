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
        private Context context;

        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder)
         */
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView taskNameTv, taskStatusTv;
            private ImageView cercleIv;
            private MaterialCardView taskCv;



            public ViewHolder(View view, RecyclerViewInterface recyclerViewInterface) {
                super(view);
                // Define click listener for the ViewHolder's View

                taskNameTv = (TextView) view.findViewById(R.id.taskNameTv);
                taskStatusTv = (TextView) view.findViewById(R.id.taskStatusTv);
                cercleIv = (ImageView)view.findViewById(R.id.cercleIv);
                taskCv = (MaterialCardView) view.findViewById(R.id.taskCv);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (recyclerViewInterface != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                recyclerViewInterface.onItemClick(position);
                            }
                        }
                    }

                    });

                }


        }


        public TaskListAdapter(Context context, ArrayList<TaskModel> taskDataset, RecyclerViewInterface recyclerViewInterface ) {
            this.taskDataset = taskDataset;
            this.context = context;

            this.recyclerViewInterface = recyclerViewInterface;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_task, viewGroup, false);

            return new ViewHolder(view, recyclerViewInterface);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {



            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.taskNameTv.setText(taskDataset.get(position).getTaskName());
            viewHolder.taskStatusTv.setText(taskDataset.get(position).getTaskstatus());


            String status = taskDataset.get(position).getTaskstatus();

            if (status != null) {
                String statusLowerCase = status.toLowerCase().replace(" ", "");
                viewHolder.taskStatusTv.setText(statusLowerCase);


            if (status.toLowerCase().replace(" ", "").equals("2")) {
                viewHolder.cercleIv.setImageResource(R.drawable.gros_cercle);
                viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.gris2));


            } else if (status.toLowerCase().replace(" ", "").equals("1")){

                viewHolder.cercleIv.setImageResource(R.drawable.task_done);
                viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.vert));

            }else if (status.toLowerCase().replace(" ", "").equals("0")){
                viewHolder.cercleIv.setImageResource(R.drawable.task_in_progress);
                viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.bleu));

            }else if (status.toLowerCase().replace(" ", "").equals("3")){
                viewHolder.cercleIv.setImageResource(R.drawable.task_bug);
                viewHolder.taskCv.setStrokeColor(ContextCompat.getColor(context, R.color.rouge));

            }
            }
        }



        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return taskDataset.size();
        }
    }


