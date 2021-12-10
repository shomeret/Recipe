package com.example.recipe;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseMenuActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_menu, menu);
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
                RecipeDBClient client = new RecipeDBClient(BaseMenuActivity.this);
                client.DeleteAllRecipes();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
