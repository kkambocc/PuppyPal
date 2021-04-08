package ca.on.conestogac.puppypal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;

public class FitnessGoalActivity extends AppCompatActivity {
    DBHandler dbHandler;
    LinearLayout fitnessGoalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_goal);

        updateTheList();
    }

    public void updateTheList()
    {
        fitnessGoalList = findViewById(R.id.fitnessGoalList);
        dbHandler = new DBHandler(this);

        ArrayList<String> ids = dbHandler.ReadSingleColumn("fitness_goal_id", "tbl_fitness_goal");

        for (String id : ids)
        {
            ArrayList<String> fitnessGoal = dbHandler.ReadSingleEntry(id, "tbl_fitness_goal");
            viewCreator(Integer.parseInt(fitnessGoal.get(0)));
        }
    }

    public void viewCreator(int viewID)
    {
        Button listButton = new Button(this);
        listButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listButton.setId(viewID);
        listButton.setText("Fitness Goal " + viewID);
        listButton.setOnClickListener(view -> {
            System.out.println("ViewID: " + view.getId());
            Intent intent = new Intent(view.getContext(), AddFitnessGoalActivity.class);
            intent.putExtra("id", view.getId());
            startActivity(intent);
        });
        fitnessGoalList.addView(listButton);
    }

    public void AddFitnessGoalButton(View v)
    {
        Intent intent = new Intent(this, AddFitnessGoalActivity.class);
        startActivity(intent);
    }
}