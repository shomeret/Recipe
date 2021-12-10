package com.example.recipe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.*;
import java.util.ArrayList;

import static android.text.TextUtils.indexOf;
import static android.text.TextUtils.substring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.titleClickListener, NetworkingService.NetworkingListener{

    ArrayList<Recipe> recipeTitles = new ArrayList<Recipe>();
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    NetworkingService networkingManager;
    JsonService jsonService;
    String searchFor;
    SearchView searchView;
    MenuItem searchViewMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingManager = ((myApp)getApplication()).getNetworkingService();
        jsonService = ((myApp)getApplication()).getJsonService();
        networkingManager.listener = this;
        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new RecipeAdapter(this,recipeTitles);
        recyclerView.setAdapter(adapter);
        setTitle("Enter ingredients, with commas");



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        searchViewMenuItem = menu.findItem(R.id.search_bar);
        searchView = (SearchView) searchViewMenuItem.getActionView();

        searchFor = searchView.getQuery().toString();
        if (!searchFor.isEmpty()) {
            searchView.setIconified(false);
            searchView.setQuery(searchFor, false);
        }

        searchView.setQueryHint("Search for recipes");
        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {// when the user clicks enter
                if (containsDigit(query)) {
                    // if string contains any digits, then TOAST shall pop up to try again.
                    Toast.makeText(getApplicationContext(), "Do not enter any digits", Toast.LENGTH_LONG).show();
                    return false;
                }

                //int index = query.indexOf(query,',');
                int indx = 0;
                String tmpStr ="";
                String tmpfinalStr ="";
                int maxQueryLen = query.length();

                // Count total commas
                int totCommas = 0;
                int tmpComma = 0;
                for(int i = 0; i < query.length(); i++) {
                    if(query.charAt(i) == ',') totCommas++;
                }

                // Manipulate the string
                for(int i = 0; i < query.length(); i++) {
                    if(query.charAt(i) == ',') {
                        tmpComma++;
                        if (totCommas == 1) {
                            tmpStr = tmpStr + query.substring(0, i) + ",+";
                            tmpfinalStr = query.substring(i+1, maxQueryLen);
                            tmpStr = tmpStr + tmpfinalStr;
                        } else if (tmpComma < totCommas){
                            tmpStr = tmpStr + query.substring(indx, i) + ",+";
                        } else {
                            tmpStr = tmpStr + query.substring(indx, i) + ",+";
                            tmpfinalStr = query.substring(i+1, maxQueryLen);
                            tmpStr = tmpStr + tmpfinalStr;
                        }
                        indx = i + 1;
                    }
                }

                if (totCommas == 0) {
                    tmpStr = query.substring(0, maxQueryLen);
                }

                networkingManager.searchTitles(tmpStr);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.showsaved:
                Intent intent = new Intent(this, SavedRecipeListActivity.class);
                startActivity(intent);
                break;
            case R.id.addnew:
                Intent intnt = new Intent(this, AddNewActivity.class);
                startActivity(intnt);
                break;
            case R.id.deleteall:
                RecipeDBClient client = new RecipeDBClient(MainActivity.this);
                client.DeleteAllRecipes();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean containsDigit(final String aString)
    {
        if (aString != null && !aString.isEmpty())
        {
            for (char c : aString.toCharArray())
            {
                if (Character.isDigit(c))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable("LIST", recipeTitles);
        outState.putString("search", searchFor);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        searchFor = savedInstanceState.getString("search");
        searchView.setQuery(searchFor, false);
        recipeTitles = (ArrayList<Recipe>) savedInstanceState.getSerializable("LIST");
    }

    @Override
    public void dataListener(String josnString) {
        recipeTitles =  jsonService.getRecipesFromJSON(josnString);
        adapter = new RecipeAdapter(this, recipeTitles);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void titleClicked(Recipe rec) {
        Intent intent = new Intent(this, RecipeActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("RECIPE",rec);
        intent.putExtra("BUNDLE", args);
        startActivity(intent);
    }


}


