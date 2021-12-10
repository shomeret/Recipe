package com.example.recipe;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddNewActivity extends BaseMenuActivity {
    Button addBut;
    EditText score, summary, add_ingredients, add_img_URL, title;
    ImageView img;
    private RecipeDetails recipeDetails;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add_img_URL = findViewById(R.id.add_img_URL);
        title = findViewById(R.id.addnew_title);
        img = findViewById(R.id.recipe_image);
        summary = findViewById(R.id.addnew_summary);
        score = findViewById(R.id.addnew_recipe_score);
        addBut = findViewById(R.id.addnew_button);
        add_ingredients = findViewById(R.id.add_ingredients);
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ExtendedIngredient> list = new ArrayList<>();
                String [] arr = add_ingredients.getText().toString().split(",");
                for(int i=0;i<arr.length;i++){
                    list.add(new ExtendedIngredient((long)i,arr[i]));
                }
                recipeDetails =  new RecipeDetails();
                recipeDetails.setExtendedIngredients(list);
                recipeDetails.setHealthScore(Double.parseDouble(score.getText().toString()));
                recipeDetails.setSummary(summary.getText().toString());
                recipeDetails.setImage(add_img_URL.getText().toString());
                recipeDetails.setTitle(title.getText().toString());
                RecipeDBClient recipeDBClient = new RecipeDBClient(AddNewActivity.this);
                recipeDBClient.insertNewRecipe(recipeDetails);

                Intent intent =new Intent(AddNewActivity.this, SavedRecipeListActivity.class);
                startActivity(intent);

            }
        });



    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
