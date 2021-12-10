package com.example.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SavedRecipeListActivity extends BaseMenuActivity implements RecipeDBClient.DBActionListener, RecipeDBAdapter.titleClickListener {
    //ArrayList<RecipeDetails> listfromDB;
    RecipeDBAdapter adapter;
    //List<RecipeDetails> recDetailList;
    RecyclerView recyclerView;
    private RecipeDBClient recipeDBClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_main_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.saved_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RecipeAdapter(this,recipeTitles);
         recipeDBClient = new RecipeDBClient(SavedRecipeListActivity.this);

        recipeDBClient.listener = this;
       

    }

    @Override
    protected void onStart() {
        super.onStart();
        recipeDBClient.getAllRecipes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dbAllRecipesReturnWithList(List<RecipeDetails> recipeList) {
        adapter = new RecipeDBAdapter(this, recipeList);
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
    }


    @Override
    public void titleClicked(RecipeDetails rec) {
        Intent intent = new Intent(this, SavedRecipeActivity.class);

        intent.putExtra("BUNDLE_S", rec);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
