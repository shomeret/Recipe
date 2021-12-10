package com.example.recipe;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;

public class RecipeDBAdapter extends RecyclerView.Adapter<RecipeDBAdapter.TasksViewHolder>{

    interface titleClickListener {
        public void titleClicked(RecipeDetails title);
    }
    private Context mCtx;
    private List<RecipeDetails> recipeList;
    titleClickListener listener;

    public RecipeDBAdapter(Context mCtx, List<RecipeDetails> recipeList) {
        this.mCtx = mCtx;
        this.recipeList = recipeList;
        listener = (titleClickListener)mCtx;


    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.saved_view_list_row_item, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        RecipeDetails rd = recipeList.get(position);
        holder.titleTextView.setText(rd.getTitle());
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;

        public TasksViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.saved_title_row);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            RecipeDetails recipe = recipeList.get(getAdapterPosition());
            listener.titleClicked(recipe);

        }
    }

}
