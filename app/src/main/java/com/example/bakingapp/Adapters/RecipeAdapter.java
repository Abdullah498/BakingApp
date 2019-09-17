package com.example.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeDetails;
import com.example.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeAdapter.class.getSimpleName();

    ArrayList<Recipe> recipes;
    private Context mcontext;


    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        mcontext = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.recipe_item, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.recipeName.setText(recipe.getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext, RecipeDetails.class);
                intent.putExtra("name",recipe.getName());
                intent.putExtra("ingredients",recipe.getIngredients());
                intent.putExtra("shortDescription",recipe.getShortDescription());
                intent.putExtra("description",recipe.getDescription());
                intent.putExtra("videoURL",recipe.getVideoURL());
                intent.putExtra("thumbnailURL",recipe.getThumbnailURL());
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
            this.recipes= (ArrayList<Recipe>) recipes;
            notifyDataSetChanged();
            }


    class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        CardView cardView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_recipe_name);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
