package com.example.recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonService {
    public ArrayList<Recipe> getRecipesFromJSON(String json)  {
        ArrayList<Recipe> arrayList = new ArrayList<>(0);
                try {
                    JSONArray jsonArray = new JSONArray(json);
                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String title = obj.getString("title");
                        int id = obj.getInt("id");

                        Recipe r = new Recipe(title,id);
                        arrayList.add(r);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

        return arrayList;
    }



}
