package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // UI elements to access
    private TextView mSandwichOrigin;
    private TextView mSandwichAlsoKnown;
    private TextView mSandwichIngredients;
    private TextView mSandwichDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        // If compileSdk from app gradle is >= 26 it's not necessary to cast the views, otherwise
        // casting is mandatory
        mSandwichOrigin = findViewById(R.id.origin_tv);
        mSandwichAlsoKnown = findViewById(R.id.also_known_tv);
        mSandwichIngredients = findViewById(R.id.ingredients_tv);
        mSandwichDescription = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = -1;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * Populates the UI with the sandwich details
     *
     * @param sandwich Selected sandwich
     */
    private void populateUI(Sandwich sandwich) {

        // Instead of showing a blank label, a message for the user is displayed
        // We could use ternary operation to simplify the code.

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            mSandwichAlsoKnown.setText("N/A");
        } else {
            for (String otherName : sandwich.getAlsoKnownAs()) {
                mSandwichAlsoKnown.append(" - " + otherName + "\n");
            }
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mSandwichOrigin.setText("N/A");
        } else {
            mSandwichOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()) {
            mSandwichDescription.setText("N/A");
        } else {
            mSandwichDescription.setText(sandwich.getDescription());

        }

        if (sandwich.getIngredients().isEmpty()) {
            mSandwichIngredients.setText("N/A");
        } else {
            for (String ingredient : sandwich.getIngredients()) {
                mSandwichIngredients.append(" - " + ingredient + "\n");
            }
        }
    }
}
