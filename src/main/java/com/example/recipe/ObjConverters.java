package com.example.recipe;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjConverters {
    @TypeConverter
    public ArrayList<ExtendedIngredient> toExtendedIngredients (String value) {
        Type listType = new TypeToken<ArrayList<ExtendedIngredient>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public  String fromExtendedIngredients(ArrayList<ExtendedIngredient> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}