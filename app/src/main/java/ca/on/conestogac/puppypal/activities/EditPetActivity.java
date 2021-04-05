package ca.on.conestogac.puppypal.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.MainActivity;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExcrementRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

import static java.lang.Integer.parseInt;

public class EditPetActivity extends AppCompatActivity
{
    private Pet pet;
    private DBHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);
        database = new DBHandler(this);

        ShowPet(Long.parseLong(getIntent().getStringExtra(Pet.PRIMARY_KEY)));
    }

    /*  This method changes the page to the add_record page and fills in the form and fills in the form with the selected pets information.
     *
     */
    private void ShowPet(Long petId)
    {
        pet = new Pet(database.ReadSingleEntry(petId.toString(), Pet.TABLE_NAME));

        //name
        ((EditText) findViewById(R.id.textName)).setText(pet.getName());
        findViewById(R.id.textName).setEnabled(false);

        //age
        ((EditText) findViewById(R.id.textAge)).setText("" + pet.getAge());

        //Weight
        String mostRecentWeight = database.ReadSingleEntry(Pet.PRIMARY_KEY, pet.getPetId().toString(), WeightRecord.TABLE_NAME,
                WeightRecord.COLUMN_NAMES[0] + " DESC").get(3);
        ((EditText) findViewById(R.id.textWeight)).setText(mostRecentWeight);
        findViewById(R.id.textWeight).setEnabled(false);

        //breed
        ((EditText) findViewById(R.id.textBreed)).setText(pet.getBreed());
        findViewById(R.id.textBreed).setEnabled(false);

        //gender
        if (pet.getGender() == 0)
        {
            ((RadioButton) findViewById(R.id.radioFemale)).setChecked(true);
        }
        else
        {
            ((RadioButton) findViewById(R.id.radioMale)).setChecked(true);
        }
        findViewById(R.id.radioMale).setEnabled(false);
        findViewById(R.id.radioFemale).setEnabled(false);

        //spayed or neutered
        if (pet.getSpayedNeutered() == 1)
        {
            ((Switch) findViewById(R.id.switchSpayedNeutered)).setChecked(true);
        }

        //add pet button
        ((Button) findViewById(R.id.addPetToDatabase)).setText("Change Pet Details");
        findViewById(R.id.addPetToDatabase).setOnClickListener(this::UpdatePet);

        //delete button
        findViewById(R.id.deletePetFromDatabase).setVisibility(View.VISIBLE);
        findViewById(R.id.deletePetFromDatabase).setEnabled(true);
        findViewById(R.id.deletePetFromDatabase).setOnClickListener(this::DeletePet);
    }

    private boolean Validate()
    {
        String age = ((EditText) findViewById(R.id.textAge)).getText().toString();
        boolean spayedNeutered = ((Switch) findViewById(R.id.switchSpayedNeutered)).isChecked();

        pet.setAge(parseInt(age));
        if (spayedNeutered)
        {
            pet.setSpayedNeutered(1);
        }
        else
        {
            pet.setSpayedNeutered(0);
        }
        return true;//for now
    }


    public void UpdatePet(View v)
    {
        if (Validate())
        {
            database.UpdateTable(Pet.TABLE_NAME, pet.toArray(), Pet.PRIMARY_KEY, pet.getPetId().toString());
            finish();
        }
    }

    public void DeletePet(View v)
    {
        database.DeleteFromTable(Pet.TABLE_NAME, Pet.PRIMARY_KEY, pet.getPetId());
        database.DeleteFromTable(WeightRecord.TABLE_NAME, Pet.PRIMARY_KEY, pet.getPetId());
        database.DeleteFromTable(EnergyRecord.TABLE_NAME, Pet.PRIMARY_KEY, pet.getPetId());
        database.DeleteFromTable(ExcrementRecord.TABLE_NAME, Pet.PRIMARY_KEY, pet.getPetId());
        database.DeleteFromTable(MealRecord.TABLE_NAME, Pet.PRIMARY_KEY, pet.getPetId());
        database.DeleteFromTable(ExerciseRecord.TABLE_NAME, Pet.PRIMARY_KEY, pet.getPetId());
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}