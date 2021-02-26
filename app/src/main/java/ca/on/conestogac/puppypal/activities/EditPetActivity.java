package ca.on.conestogac.puppypal.activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.Pet;

import static java.lang.Integer.parseInt;

public class EditPetActivity extends AppCompatActivity
{
    private Long petId;
    private Pet pet;
    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pet);
        database = new DBHandler(this);

        ArrayList<String> ids = (database.ReadSingleColumn("pet_id",Pet.TABLE_NAME));
        LinearLayout list = findViewById(R.id.petList);

        for (String id : ids)
        {
            pet = new Pet(database.ReadSingleEntry(id,Pet.TABLE_NAME));
            Button b = new Button(this);
            b.setText(pet.getName());
            SetOnClick(b,pet.getPetId());
            list.addView(b);
        }
    }

    private void SetOnClick(Button btn, long id)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petId = id;
                ShowPet();
            }
        });
    }

    /*  This method changes the page to the add_record page and fills in the form and fills in the form with the selected pets information.
     *
     */
    private void ShowPet()
    {
        setContentView(R.layout.add_pet);
        pet = new Pet(database.ReadSingleEntry(petId.toString(),Pet.TABLE_NAME));

        //name
        ((EditText) findViewById(R.id.textName)).setText(pet.getName());
        findViewById(R.id.textName).setEnabled(false);

        //age
        ((EditText) findViewById(R.id.textAge)).setText("" + pet.getAge());

        //Weight
        ((EditText) findViewById(R.id.textWeight)).setText("" + pet.getWeight());
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
        } else
        {
            pet.setSpayedNeutered(0);
        }
        return true;//for now
    }


    public void UpdatePet(View v)
    {
        if (Validate())
        {

            database.DeletePetFromTable(pet);
            database.AddToTable(Pet.TABLE_NAME, pet.toArray());
            finish();
            Intent intent = new Intent(this, EditPetActivity.class);
            startActivity(intent);
        }
    }

    public void DeletePet(View v)
    {
        database.DeletePetFromTable(pet);
        finish();
        Intent intent = new Intent(this, EditPetActivity.class);
        startActivity(intent);
    }
}