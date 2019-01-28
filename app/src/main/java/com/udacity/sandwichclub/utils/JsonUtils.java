package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)  {

        try {
            JSONObject jsonSandwich = new JSONObject(json);

            // Name object of the sandwich
            JSONObject jsonName = jsonSandwich.getJSONObject("name");

            String mainName = jsonName.getString("mainName");

            // Equivalent names of the sandwich
            JSONArray jsonOtherNames = jsonName.getJSONArray("alsoKnownAs");

            List<String> otherNames = new ArrayList<>();

            // Getting each name from JSON Array
            for (int i = 0; i < jsonOtherNames.length(); i++) {
                String ingredient = jsonOtherNames.getString(i);
                otherNames.add(ingredient);
            }

            // Place of the origin of the sandwich
            String placeOfOrigin = jsonSandwich.getString("placeOfOrigin");

            // Description of the sandwich
            String description = jsonSandwich.getString("description");

            // Image URL of the sandwich
            String imageUrl = jsonSandwich.getString("image");

            // Ingredients JSON of the sandwich
            JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");

            List<String> ingredients = new ArrayList<>();

            // Getting each ingredients from JSON Array
            for (int i = 0; i < jsonIngredients.length(); i++) {
                String ingredient = jsonIngredients.getString(i);
                ingredients.add(ingredient);
            }

            Sandwich sandwich = new Sandwich(mainName, otherNames, placeOfOrigin, description,
                    imageUrl, ingredients);

            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
