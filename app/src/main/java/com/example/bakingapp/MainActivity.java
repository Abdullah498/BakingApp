package com.example.bakingapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.bakingapp.Adapters.RecipeAdapter;
import com.example.bakingapp.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String MainURL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private ArrayList<Recipe> recipes=new ArrayList<>();;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.progress_bar);


        recyclerView=findViewById(R.id.rv_recipes);
        //recipeAdapter=new RecipeAdapter(this,recipes);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,numberOfColumns());
        recyclerView.setLayoutManager(gridLayoutManager);

        new RecipeAsyncTask().execute(MainURL);
    }

    //Here you can dynamically calculate the number of columns and the layout will adapt to the screen size and orientation
    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the item
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        return nColumns;
    }

    class RecipeAsyncTask extends AsyncTask<String,Void,ArrayList<Recipe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(String... siteUrl) {
            String text;

            if (recipes.isEmpty()){
                try {

                    URL url = new URL(siteUrl[0]);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    text = stream2String(inputStream);

                    recipes = extractFromJson(text);

                    return recipes;


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                return recipes;
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> Recipe) {

            progressBar.setVisibility(View.INVISIBLE);

            recipeAdapter=new RecipeAdapter(MainActivity.this,Recipe);
            recyclerView.setAdapter(recipeAdapter);
            recipeAdapter.notifyDataSetChanged();
        }
    }
    public ArrayList<Recipe> extractFromJson(String json){
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {

            JSONArray root=new JSONArray(json);

            for (int i=0; i<root.length(); i++){

                JSONObject currentRecipe = root.getJSONObject(i);

                String name = currentRecipe.getString("name");

                String ingredient="";
                ArrayList<String> shortDescription=new ArrayList<>() ;
                ArrayList<String> description= new ArrayList<>();
                ArrayList<String> videoURL= new ArrayList<>();
                ArrayList<String> thumbnailURL= new ArrayList<>();

               JSONArray ingredients=currentRecipe.getJSONArray("ingredients");
                for (int j=0;j<ingredients.length();j++){
                    JSONObject currentIngredient=ingredients.getJSONObject(j);
                    ingredient+=currentIngredient.getString("quantity")+"\t"+
                            currentIngredient.getString("measure")+"\t"+currentIngredient.getString("ingredient")+"\n";
                }
                JSONArray steps=currentRecipe.getJSONArray("steps");
                for (int j=0;j<steps.length();j++){

                    JSONObject currentStep=steps.getJSONObject(j);
                    shortDescription.add(currentStep.getString("shortDescription"));
                    description.add(currentStep.getString("description"));
                    videoURL.add(currentStep.getString("videoURL"));
                    thumbnailURL.add(currentStep.getString("thumbnailURL"));
                }


                recipes.add(new Recipe(name, ingredient, shortDescription, description, videoURL, thumbnailURL));
            }

            return recipes;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;


    }
    public String stream2String(InputStream inputStream){

        String line;
        StringBuilder text = new StringBuilder();

        BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream));

        try{
            while((line = reader.readLine()) != null){
                text.append(line);
            }
        }catch (IOException e){}

        return text.toString();
    }
}
