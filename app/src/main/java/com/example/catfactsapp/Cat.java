package com.example.catfactsapp;

import org.json.JSONException;
import org.json.JSONObject;

public class Cat {

    private String name;
    private String description;
    private String imageID;
    private String temperament;
    private String country;
    private String breed;
    private String breed_id;
    private Boolean downloaded = false;

    Boolean getDownloaded() {
        return downloaded;
    }

    void setDownloaded() {
        this.downloaded = true;
    }

    Cat(JSONObject jsonObject){
        try {
            if(jsonObject.has("name")){
              name = jsonObject.get("name").toString();
            }else name = "";

            if(jsonObject.has("description")){
                description = jsonObject.get("description").toString();
            }else description = "";

            if(jsonObject.has("id")){
                imageID = jsonObject.get("id").toString();
            }else imageID = "";

            if(jsonObject.has("origin")){
                country = jsonObject.get("origin").toString();
            }else country = "";

            if(jsonObject.has("name")){
                breed = jsonObject.get("name").toString();
            }else breed = "";

            if(jsonObject.has("id")){
                breed_id = jsonObject.get("id").toString();
            }else breed_id = "";

            if(jsonObject.has("temperament")){
                temperament = jsonObject.get("temperament").toString();
            }else temperament = "";

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    String getImageID() {
        return imageID;
    }

    void setImageID(String imageID) {
        this.imageID = imageID;
    }

    String getTemperament() {
        return temperament;
    }

    String getBreed() {
        return breed;
    }

    String getCountry() {
        return country;
    }

    String getBreed_id() {
        return breed_id;
    }

    public String getName() {
        return name;
    }

    String getDescription() {
        return description;
    }

}
