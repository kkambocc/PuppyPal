package ca.on.conestogac.puppypal.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Max;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

public class AddPetActivity extends AppCompatActivity implements Validator.ValidationListener
{
    DBHandler database;

    @NotEmpty
    @Length(min = 3, max = 20)
    @Pattern(regex = "^[a-zA-Z]*$")
    private EditText textName;

    @NotEmpty
    @Max(value = 99)
    private EditText textAge;

    @NotEmpty
    @Length(min = 3, max = 20)
    @Pattern(regex = "^[a-zA-Z ]*$")
    private EditText textBreed;

    @NotEmpty
    @Max(value = 900)
    private EditText textWeight;

    private Validator validator;

    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet);
        database = new DBHandler(this);
        pet = new Pet();

        textName = findViewById(R.id.textName);
        textAge = findViewById(R.id.textAge);
        textWeight = findViewById(R.id.textWeight);
        textBreed = findViewById(R.id.textBreed);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }


    public void Validate(View v)
    {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded()
    {
        //Get values for pet
        Pet pet = new Pet();
        pet.setName(textName.getText().toString());
        pet.setAge(Integer.parseInt(textAge.getText().toString()));
        pet.setBreed(textBreed.getText().toString());

        Float weight = Float.parseFloat(textWeight.getText().toString());
        int gender = ((RadioGroup) findViewById(R.id.genderGroup)).getCheckedRadioButtonId();
        boolean spayedNeutered = ((Switch) findViewById(R.id.switchSpayedNeutered)).isChecked();

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
        DBHandler database = new DBHandler(this);
        database.AddToTable(Pet.TABLE_NAME, pet.toArray());

        //Add weight to tbl_weight
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.ZONE_OFFSET, 0);
        calendar.set(Calendar.DST_OFFSET, 0);

        ArrayList<String> petId = database.ReadSingleColumn(Pet.PRIMARY_KEY, Pet.TABLE_NAME, Pet.PRIMARY_KEY + " DESC", "1");
        WeightRecord weightRecord = new WeightRecord(weight, calendar.getTime(), Long.parseLong(petId.get(0)));
        database.AddToTable(WeightRecord.TABLE_NAME, weightRecord.toArray());

        //Reset screen
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}