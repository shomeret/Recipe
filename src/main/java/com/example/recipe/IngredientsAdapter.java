package com.example.recipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.TasksViewHolder>{

    private Context mCtx;

    public IngredientsAdapter(Context mCtx, List<ExtendedIngredient> ingredientsList) {
        this.mCtx = mCtx;
        this.ingredientsList = ingredientsList;
    }

    private List<ExtendedIngredient> ingredientsList ;


    @NonNull
    @Override
    public IngredientsAdapter.TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.main_rec_view_list_row_item, parent, false);
        return new IngredientsAdapter.TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.TasksViewHolder holder, int position) {
        ExtendedIngredient ei = ingredientsList.get(position);
        holder.titleTextView.setText(ei.getOriginalString());
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }
    class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_row);
        }

    }
}
