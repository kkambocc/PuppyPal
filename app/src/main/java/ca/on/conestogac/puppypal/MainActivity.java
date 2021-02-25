package ca.on.conestogac.puppypal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ca.on.conestogac.puppypal.activities.AddPetActivity;
import ca.on.conestogac.puppypal.activities.AddRecordActivity;
import ca.on.conestogac.puppypal.activities.EditPetActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}