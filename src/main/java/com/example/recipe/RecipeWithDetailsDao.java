package com.example.recipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.*;
import java.util.List;

@Dao
public interface RecipeWithDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RecipeDetails recipeWithDetails);

    @Delete
    void delete(RecipeDetails recipeWithDetails);

    @Query("Select * from recipedetails")
    List<RecipeDetails> getAllRecipes();


    @Query("Delete from recipedetails")
    void DeleteAllRecipes();


    @Query("Select * from recipedetails where healthScore >=:scr")
    List<RecipeDetails> getAllRecipeByScore(Double scr);

    @Update
    void updateRecipe(RecipeDetails updatedRecipe);
}
