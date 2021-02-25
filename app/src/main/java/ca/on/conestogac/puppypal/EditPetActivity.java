package ca.on.conestogac.puppypal;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class EditPetActivity extends AppCompatActivity
{
    private long petId;
    private Pet pet;
    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pet);
        database = new DBHandler(this);

        Long[] ids = database.GetPetIdList();
        LinearLayout list = findViewById(R.id.petList);

        for (Long id : ids)
        {
            pet = database.ReadPetFromTable(id);
            Button b = new Button(this);
            b.setText(pet.getName());
            SetOnClick(b,pet.getPetId());
            list.addView(b);
        }
    }

    public void SetOnClick(Button btn, long id)
    {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petId = id;
                ShowPet();
            }
        });
    }

    public void ShowPet()
    {
        setContentView(R.layout.add_pet);
        pet = database.ReadPetFromTable(petId);

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

    public void UpdatePet(View v)
    {
        database.DeletePetFromTable(pet);
        finish();
    }

    public void DeletePet(View v)
    {
        database.DeletePetFromTable(pet);
        finish();
    }
}