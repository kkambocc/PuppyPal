package ca.on.conestogac.puppypal.activities;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.Pet;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AddPetActivity extends AppCompatActivity
{
    DBHandler database;
    String name;
    int age;
    int weight;
    String breed;
    int gender;
    boolean spayedNeutered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);
        database = new DBHandler(this);

    }

    public void Validate(View v) {
        name = ((EditText)findViewById(R.id.textName)).getText().toString();
        if (!nameValidation(name))
        {
            return;
        }

        age = Integer.parseInt(((EditText)findViewById(R.id.textAge)).getText().toString());
        if (!ageValidation(age))
        {
            return;
        }
        weight = Integer.parseInt(((EditText)findViewById(R.id.textWeight)).getText().toString());
        if (!weightValidation(weight))
        {
            return;
        }
        breed = ((EditText)findViewById(R.id.textBreed)).getText().toString();
        if (!breedValidation(breed))
        {
            return;
        }
        gender = ((RadioGroup) findViewById(R.id.genderGroup)).getCheckedRadioButtonId();
        spayedNeutered = ((Switch) findViewById(R.id.switchSpayedNeutered)).isChecked();
        //Do validation here then pass to add a pet if successful
                AddAPet();
    }
    public boolean breedValidation(String breed) {
        boolean breedBoolean;
        if (breed.isEmpty())
        {
            Toast.makeText(this, "Breed field is empty !", Toast.LENGTH_SHORT).show();
            breedBoolean = false;
        }
        else
        {
            breedBoolean = true;
        }
        if (!breed.matches("^[a-zA-Z]*$"))
        {
            Toast.makeText(this, "Breed should only contains alphabetic letters", Toast.LENGTH_SHORT).show();
            breedBoolean = false;
        }
        else
        {
            breedBoolean = true;
        }
        if (breed.length() >50)
        {
            Toast.makeText(this, "Breed should be no more than 50 characters long", Toast.LENGTH_SHORT).show();
            breedBoolean = false;
        }
        else
        {
            breedBoolean = true;
        }
        return breedBoolean;
    }

    public boolean weightValidation(int weight) {
        boolean weightBoolean;
        if (String.valueOf(weight).isEmpty())
        {
            Toast.makeText(this, "Weight field is empty !", Toast.LENGTH_SHORT).show();
            weightBoolean = false;
        }
        else
        {
            weightBoolean = true;
        }
        if (weight > 999)
        {
            Toast.makeText(this, "Weight in lbs should be in the range of 3 digits", Toast.LENGTH_SHORT).show();
            weightBoolean = false;
        }
        else
        {
            weightBoolean = true;
        }
        return weightBoolean;
    }

    public boolean ageValidation(int age) {
        boolean ageBoolean;
        if (String.valueOf(age).isEmpty())
        {
            Toast.makeText(this, "Age field is empty !", Toast.LENGTH_SHORT).show();
            ageBoolean = false;
        }
        else
        {
            ageBoolean = true;
        }
        if (age > 100)
        {
            Toast.makeText(this, "Age should be in the range of 3 digits", Toast.LENGTH_SHORT).show();
            ageBoolean = false;
        }
        else
        {
            ageBoolean = true;
        }
        return ageBoolean;
    }

    public boolean nameValidation(String name) {
        boolean nameBoolean;
        if (name.isEmpty())
        {
            Toast.makeText(this, "Name field is empty !", Toast.LENGTH_SHORT).show();
            nameBoolean = false;
        }
        else
        {
            nameBoolean= true;
        }
        if (!name.matches("^[a-zA-Z]*$"))
        {
            Toast.makeText(this, "Name should only contains alphabetic letters", Toast.LENGTH_SHORT).show();
            nameBoolean =false;
        }
        else
        {
            nameBoolean = true;
        }
        if (name.length() >50)
        {
            Toast.makeText(this, "Name should be no more than 50 characters long", Toast.LENGTH_SHORT).show();
            nameBoolean = false;
        }
        else
        {
            nameBoolean = true;
        }
        return nameBoolean;
    }

    public void AddAPet()
    {
        Pet pet = new Pet();

        //Get values for pet
        pet.setName(name);
        pet.setAge(age);
        pet.setWeight(weight);
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
        database.AddToTable(Pet.TABLE_NAME,pet.toArray());

        //Reset screen
        finish();
    }
}