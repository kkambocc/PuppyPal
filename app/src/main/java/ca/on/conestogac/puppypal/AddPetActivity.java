package ca.on.conestogac.puppypal;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class AddPetActivity extends AppCompatActivity
{
    DBHandler database;
    String name;
    String age;
    String weight;
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

    public void Validate(View v)
    {
        name = ((EditText) findViewById(R.id.textName)).getText().toString();
        age = ((EditText) findViewById(R.id.textAge)).getText().toString();
        weight = ((EditText) findViewById(R.id.textWeight)).getText().toString();
        breed = ((EditText) findViewById(R.id.textBreed)).getText().toString();
        gender = ((RadioGroup) findViewById(R.id.genderGroup)).getCheckedRadioButtonId();
        spayedNeutered = ((Switch) findViewById(R.id.switchSpayedNeutered)).isChecked();
        //Do validation here then pass to add a pet if successful
        AddAPet();
    }

    public void AddAPet()
    {
        Pet pet = new Pet();

        //Get values for pet
        pet.setName(name);
        pet.setAge(parseInt(age));
        pet.setWeight(parseFloat(weight));
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
        database.AddToPetTable(pet);

        //Reset screen
        finish();
    }
}
