package com.example.recipe;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {RecipeDetails.class}, version=1)
@TypeConverters({ObjConverters.class})
public abstract class RecipeDB extends RoomDatabase {
    public abstract RecipeWithDetailsDao getRecipeDao();

}
