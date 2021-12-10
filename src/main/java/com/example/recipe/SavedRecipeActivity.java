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

import java.util.List;

public class SavedRecipeActivity extends BaseMenuActivity {
    RecyclerView recyclerView;
    IngredientsAdapter Ingadapter;
    ImageView image;
    long recId = 0;
    Button deleteBut;
    TextView score, summary, title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.saved_ingredients_recyclerview);
        deleteBut = findViewById(R.id.delete_btn);
        //Intent intent = getIntent();
        //Bundle args = intent.getBundleExtra("BUNDLE_S");
        RecipeDetails reciped = (RecipeDetails) getIntent().getSerializableExtra("BUNDLE_S");

        deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RecipeDBClient recipeDBClient = new RecipeDBClient(SavedRecipeActivity.this);
                recipeDBClient.deleteRecipe(reciped);
                finish();


            }
        });

        score = findViewById(R.id.saved_recipe_score);
        recId = reciped.getId();
        String workingId = Long.toString(recId);
        image = findViewById(R.id.saved_recipe_image);
        summary = findViewById(R.id.saved_summary);
        title = findViewById(R.id.saved_recipe_name);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RecipeAdapter(this,recipeTitles);
        List<ExtendedIngredient> list = reciped.getExtendedIngredients();
        Ingadapter = new IngredientsAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(Ingadapter);
        Glide.with(image).load(reciped.getImage()).into(image);
        title.setText(reciped.getTitle()+"");
        score.setText(reciped.getHealthScore()+"");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            summary.setText(Html.fromHtml(reciped.getSummary()+"", Html.FROM_HTML_MODE_COMPACT));
        } else {
            summary.setText(Html.fromHtml(reciped.getSummary()+""));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    }



