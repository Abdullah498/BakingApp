package com.example.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeDetails extends AppCompatActivity {

    private Button ingredientsBtn;
    private NestedScrollView nestedScrollView;
    private TextView ingredientsTxtView;
    private boolean shown = false;

    int mPosition;
    Intent intent;

    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);


        if(findViewById(R.id.linear_layout_tablet) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;


            if(savedInstanceState == null) {

                StepsFragment stepsFragment=new StepsFragment(this,intent);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.fragment_layout_steps, stepsFragment)
                        .commit();
            }
        } else {
            // We're in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }


        ingredientsBtn = findViewById(R.id.btn_ingredient);
        nestedScrollView = findViewById(R.id.scrollView_ingredients);
        ingredientsTxtView = findViewById(R.id.tv_ingredients);

        ArrayList<String> shortDescriptionList ;
        final ArrayList<String> descriptionList ;
        final ArrayList<String> videoURLList ;
        final ArrayList<String> thumbnailURLList ;

        shortDescriptionList = getIntent().getStringArrayListExtra("shortDescription");
        descriptionList = getIntent().getStringArrayListExtra("description");
        videoURLList = getIntent().getStringArrayListExtra("videoURL");
        thumbnailURLList = getIntent().getStringArrayListExtra("thumbnailURL");

        ListView detailsList = findViewById(R.id.lv_details_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,shortDescriptionList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);

                // Return the view
                return view;
            }
        };
        detailsList.setAdapter(arrayAdapter);

        detailsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(RecipeDetails.this , StepsActivity.class);

                mPosition=position;
                intent.putExtra("description",descriptionList.get(position));
                intent.putExtra("videoURL",videoURLList.get(position));
                intent.putExtra("thumbnailURL",thumbnailURLList.get(position));

                startActivity(intent);
            }
        });

        ingredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(RecipeDetails.this,getIntent().getStringExtra("ingredients"),Toast.LENGTH_SHORT).show();
                if(!shown){
                    ingredientsTxtView.setText(getIntent().getStringExtra("ingredients"));
                    nestedScrollView.setVisibility(View.VISIBLE);
                    shown = true;
                }else {
                    nestedScrollView.setVisibility(View.GONE);
                    shown = false;
                }
            }
        });
    }
}
