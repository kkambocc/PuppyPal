package ca.on.conestogac.puppypal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ca.on.conestogac.puppypal.activities.AddAssistantActivity;
import ca.on.conestogac.puppypal.activities.AddFitnessGoalActivity;
import ca.on.conestogac.puppypal.activities.AddPetActivity;
import ca.on.conestogac.puppypal.activities.AddRecordActivity;
import ca.on.conestogac.puppypal.activities.EditAssistantActivity;
import ca.on.conestogac.puppypal.activities.EditPetActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void AddPetButton(View v)
    {
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }
    public void EditPetButton(View v)
    {
        Intent intent = new Intent(this, EditPetActivity.class);
        startActivity(intent);
    }
    public void AddRecordButton(View v)
    {
        Intent intent = new Intent(this, AddRecordActivity.class);
        startActivity(intent);
    }
    public void AddAssistantButton(View v)
    {
        Intent intent = new Intent(this, AddAssistantActivity.class);
        startActivity(intent);
    }
    public void EditAssistantButton(View v)
    {
        Intent intent = new Intent(this, EditAssistantActivity.class);
        startActivityForResult(intent,0);
    }
    public void AddFitnessGoalButton(View v)
    {
        Intent intent = new Intent(this, AddFitnessGoalActivity.class);
        startActivity(intent);
    }
}