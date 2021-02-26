package ca.on.conestogac.puppypal.activities;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;


import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.Pet;

public class AddPetActivity extends AppCompatActivity
{
    DBHandler database;
    public String name;
    public String age ;
    public String weight;
    public String breed;
    public int gender;
    public boolean spayedNeutered;
    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);
        database = new DBHandler(this);
        pet = new Pet(this);

    }
    public void Validate(View v)
    {
        name = ((EditText) findViewById(R.id.textName)).getText().toString();
        age = ((EditText) findViewById(R.id.textAge)).getText().toString();
        weight = ((EditText) findViewById(R.id.textWeight)).getText().toString();
        breed = ((EditText) findViewById(R.id.textBreed)).getText().toString();
        gender = ((RadioGroup) findViewById(R.id.genderGroup)).getCheckedRadioButtonId();
        spayedNeutered = ((Switch) findViewById(R.id.switchSpayedNeutered)).isChecked();

        pet.validateAndAdd(name,age,weight,breed,gender,spayedNeutered);
    }

    public void AddAPet(String name,String age,String weight,String breed,int gender,boolean spayedNeutered, Context dbContext)
    {
        //Get values for pet
        Pet pet= new Pet();
        pet.setName(name);
        pet.setAge(Integer.parseInt(age));
        pet.setWeight(Float.parseFloat(weight));
        pet.setBreed(breed);
        if (gender == R.id.radioFemale)
        {
            pet.setGender(0);
        }
        else
        {
            pet.setGender(1);
        }

        if (spayedNeutered)
        {
            pet.setSpayedNeutered(1);
        }
        else
        {
            pet.setSpayedNeutered(0);
        }

        //Add to database
        DBHandler database = new DBHandler(dbContext);
        database.AddToTable(Pet.TABLE_NAME,pet.toArray());

        //Reset screen
        ((Activity)dbContext).finish();
    }
}
