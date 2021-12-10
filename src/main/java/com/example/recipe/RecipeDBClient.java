package com.example.recipe;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecipeDBClient {

    static RecipeDB dbClient;
    static Context db_context;

    public interface DBActionListener {
        public void dbAllRecipesReturnWithList(List<RecipeDetails> recipeList);
    }

    DBActionListener listener;

    public static ExecutorService dbExecutorService = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper());
    RecipeDBClient(Context context) {
        db_context = context;
        dbClient = Room.databaseBuilder(context,
                RecipeDB.class, "database-recipes")
                .build();


    }

    public static RecipeDB getDBClient() {
        if (dbClient == null) {
            dbClient = new RecipeDBClient(db_context).dbClient;
        }
            return dbClient;
    }

    public void insertNewRecipe(RecipeDetails newRec) {
        dbExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getRecipeDao().insert(newRec);
            }
        });
    }

    public void DeleteAllRecipes() {
        dbExecutorService.execute(new Runnable() {
            @Override
            public void run() {
               dbClient.getRecipeDao().DeleteAllRecipes();
            }
        });
    }


    public void getAllRecipes() {
        dbExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                List<RecipeDetails> listFromDB = dbClient.getRecipeDao().getAllRecipes();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.dbAllRecipesReturnWithList(listFromDB);
                    }
                });
            }

        });

    }

    public void deleteRecipe(RecipeDetails newRec) {
        dbExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getRecipeDao().delete(newRec);
            }
        });
    }

}
