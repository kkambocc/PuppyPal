package ca.on.conestogac.puppypal;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new DBHandler(this);

    }

    public void AddPetButton(View v)
    {
        setContentView(R.layout.add_pet);
    }

    public void AddAPet(View v)
    {
        Pet pet = new Pet();

        //Get values for pet
        pet.setName(((EditText) findViewById(R.id.textName)).getText().toString());
        pet.setAge(parseInt(((EditText) findViewById(R.id.textAge)).getText().toString()));
        pet.setWeight(parseFloat(((EditText) findViewById(R.id.textWeight)).getText().toString()));
        pet.setBreed(((EditText) findViewById(R.id.textBreed)).getText().toString());
        int selectedGenderId = ((RadioGroup) findViewById(R.id.genderGroup)).getCheckedRadioButtonId();
        if (selectedGenderId == R.id.radioFemale)
        {
            pet.setGender(0);
        }
        else if (selectedGenderId == R.id.radioMale)
        {
            pet.setGender(1);
        }

        if (((Switch) findViewById(R.id.switchSpayedNeutered)).isChecked())
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
        setContentView(R.layout.activity_main);
        /*
        ((EditText) findViewById(R.id.textName)).setText("");
        ((EditText) findViewById(R.id.textAge)).setText("");
        ((EditText) findViewById(R.id.textWeight)).setText("");
        ((EditText) findViewById(R.id.textBreed)).setText("");
        ((RadioButton) findViewById(R.id.radioFemale)).setChecked(false);
        ((RadioButton) findViewById(R.id.radioMale)).setChecked(false);
        ((Switch) findViewById(R.id.switchSpayedNeutered)).setChecked(false);


         */
    }
}