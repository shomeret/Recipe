package com.example.recipe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends BaseMenuActivity implements NetworkingService.NetworkingListener {
    NetworkingService networkingManager;
    JsonService jsonService;
    RecyclerView recyclerView;
    IngredientsAdapter Ingadapter;
    ImageView image;
    int recId = 0;
    TextView score, summary, title;
    Button saveToDbBtn;
    private RecipeDetails recipeDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        Recipe recipe = (Recipe) args.getSerializable("RECIPE");
        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        score = findViewById(R.id.recipe_score);
        networkingManager.listener = this;
        recId = recipe.getId();
        title = findViewById(R.id.recipe_name);
        String workingId = Integer.toString(recId);
        recyclerView = findViewById(R.id.ingredients_recyclerview);
        image = findViewById(R.id.recipe_image);
        summary = findViewById(R.id.summary);
        networkingManager.searchRecipes(workingId);
        saveToDbBtn = findViewById(R.id.save_to_db_btn);

        saveToDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecipeDBClient recipeDBClient = new RecipeDBClient(RecipeActivity.this);
                recipeDBClient.insertNewRecipe(recipeDetails);

                //recipeDBClient.getAllRecipes();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dataListener(String josnString) {
         recipeDetails = new Gson().fromJson(josnString,RecipeDetails.class);
        List<ExtendedIngredient> list =recipeDetails.getExtendedIngredients();
        title.setText(recipeDetails.getTitle()+"");
        score.setText(recipeDetails.getHealthScore()+"");
        Ingadapter = new IngredientsAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Ingadapter);
        Glide.with(image).load(recipeDetails.getImage()).into(image);
        //summary.setText(recipeDetails.getSummary()+"");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            summary.setText(Html.fromHtml(recipeDetails.getSummary()+"", Html.FROM_HTML_MODE_COMPACT));
        } else {
            summary.setText(Html.fromHtml(recipeDetails.getSummary()+""));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
