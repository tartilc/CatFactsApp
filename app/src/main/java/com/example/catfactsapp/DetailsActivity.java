package com.example.catfactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    TextView nameText;
    TextView descText;
    TextView tempText;
    TextView wikiText;
    TextView countryText;
    ImageView imageView;
    String name,image,description,country,country_code,temperament,wiki_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        startUI();
        Intent prevIntent = getIntent();
        name = prevIntent.getStringExtra("name");
        image = prevIntent.getStringExtra("image_url");
        description = prevIntent.getStringExtra("description");
        country = prevIntent.getStringExtra("country");
        country_code = prevIntent.getStringExtra("country_code");
        temperament = prevIntent.getStringExtra("temperament");
        wiki_link = prevIntent.getStringExtra("wiki_link");

        setValues();

    }

    private void startUI() {
        nameText = findViewById(R.id.name_text2);
        descText = findViewById(R.id.description_text2);
        tempText = findViewById(R.id.temperament_text2);
        countryText = findViewById(R.id.country_text2);
        imageView = findViewById(R.id.image_cat2);
    }

    private void setValues() {
        nameText.setText(name);
        descText.setText(description);
        tempText.setText(temperament);

        String country_info = country +" (" + country_code + ")";
        countryText.setText(country_info);

        if (image.contains("http"))
            Picasso.with(this).load(image).into(imageView);
        else {
            CatURLHandler okHttpHandlerURL= new CatURLHandler(image,this,this);
            okHttpHandlerURL.execute();
        }

    }
}
