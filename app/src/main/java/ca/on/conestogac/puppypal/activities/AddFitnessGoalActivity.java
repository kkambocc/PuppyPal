package ca.on.conestogac.puppypal.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;

public class AddFitnessGoalActivity extends AppCompatActivity
{
    DBHandler dbHandler = new DBHandler(this);

    RadioButton radioButtonWeight;
    RadioButton radioButtonEnergy;
    RadioButton radioButtonExercise;

    TextView textViewTargetWeightLabel;
    TextView textViewTargetEnergyLabel;
    TextView textViewTargetExerciseTypeLabel;
    TextView textViewTargetExerciseDurationLabel;

    EditText editTextTargetWeight;
    EditText editTextTargetEnergy;
    EditText editTextTargetExerciseType;
    EditText editTextTargetExerciseDuration;

    private Button buttonAddFitnessGoal;

    public static final String SELECT_RADIO_BUTTON_WEIGHT = "WEIGHT";
    public static final String SELECT_RADIO_BUTTON_ENERGY = "ENERGY";
    public static final String SELECT_RADIO_BUTTON_EXERCISE = "EXERCISE";

    public String radiobutton;
    public String weight;
    public String energy;
    public String exerciseType;
    public String exerciseDuration;
    public String validateWeight;
    public String validateEnergy;
    public String validateExerciseType;
    public String validateExerciseDuration;

    public boolean validWeight;
    public boolean validEnergy;
    public boolean validExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness_goal);

        radiobutton = SELECT_RADIO_BUTTON_EXERCISE;

        radioButtonWeight = findViewById(R.id.radioButtonWeight);
        radioButtonEnergy = findViewById(R.id.radioButtonEnergy);
        radioButtonExercise = findViewById(R.id.radioButtonExercise);

        textViewTargetWeightLabel = findViewById(R.id.textViewTargetWeightLabel);
        textViewTargetEnergyLabel = findViewById(R.id.textViewTargetEnergyLabel);
        textViewTargetExerciseTypeLabel = findViewById(R.id.textViewTargetExerciseTypeLabel);
        textViewTargetExerciseDurationLabel = findViewById(R.id.textViewTargetExerciseDurationLabel);

        editTextTargetWeight = findViewById(R.id.editTextTargetWeight);
        editTextTargetEnergy = findViewById(R.id.editTextTargetEnergy);
        editTextTargetExerciseType = findViewById(R.id.editTextTargetExerciseType);
        editTextTargetExerciseDuration = findViewById(R.id.editTextTargetExerciseDuration);

        buttonAddFitnessGoal = findViewById(R.id.buttonAddFitnessGoal);

        buttonAddFitnessGoal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                weight = ((EditText) findViewById(R.id.editTextTargetWeight)).getText().toString();
                energy = ((EditText) findViewById(R.id.editTextTargetEnergy)).getText().toString();
                exerciseType = ((EditText) findViewById(R.id.editTextTargetExerciseType)).getText().toString();
                exerciseDuration = ((EditText) findViewById(R.id.editTextTargetExerciseDuration)).getText().toString();

                addFitnessGoal();
            }
        });
    }

    public void onClickRadioButtonWeight(View v)
    {
        radiobutton = SELECT_RADIO_BUTTON_WEIGHT;

        textViewTargetWeightLabel.setVisibility(View.VISIBLE);
        editTextTargetWeight.setVisibility(View.VISIBLE);

        textViewTargetEnergyLabel.setVisibility(View.GONE);
        editTextTargetEnergy.setVisibility(View.GONE);
        textViewTargetExerciseTypeLabel.setVisibility(View.GONE);
        editTextTargetExerciseType.setVisibility(View.GONE);
        textViewTargetExerciseDurationLabel.setVisibility(View.GONE);
        editTextTargetExerciseDuration.setVisibility(View.GONE);
    }

    public void onClickRadioButtonEnergy(View v)
    {
        radiobutton = SELECT_RADIO_BUTTON_ENERGY;

        textViewTargetEnergyLabel.setVisibility(View.VISIBLE);
        editTextTargetEnergy.setVisibility(View.VISIBLE);

        textViewTargetWeightLabel.setVisibility(View.GONE);
        editTextTargetWeight.setVisibility(View.GONE);
        textViewTargetExerciseTypeLabel.setVisibility(View.GONE);
        editTextTargetExerciseType.setVisibility(View.GONE);
        textViewTargetExerciseDurationLabel.setVisibility(View.GONE);
        editTextTargetExerciseDuration.setVisibility(View.GONE);
    }

    public void onClickRadioButtonExercise(View v)
    {
        radiobutton = SELECT_RADIO_BUTTON_EXERCISE;

        textViewTargetExerciseTypeLabel.setVisibility(View.VISIBLE);
        editTextTargetExerciseType.setVisibility(View.VISIBLE);
        textViewTargetExerciseDurationLabel.setVisibility(View.VISIBLE);
        editTextTargetExerciseDuration.setVisibility(View.VISIBLE);

        textViewTargetWeightLabel.setVisibility(View.GONE);
        editTextTargetWeight.setVisibility(View.GONE);
        textViewTargetEnergyLabel.setVisibility(View.GONE);
        editTextTargetEnergy.setVisibility(View.GONE);
    }

    public void addFitnessGoal()
    {
        switch (radiobutton)
        {
            case "WEIGHT":
                validEnergy = WeightValidation();

                if (validWeight == true)
                {
                    dbHandler.addWeightFitnessGoal(Double.parseDouble(editTextTargetWeight.getText().toString()));
                }

                break;
            case "ENERGY":
                validEnergy = EnergyValidation();

                if (validEnergy == true)
                {
                    // needs to be int not string
                    dbHandler.addEnergyFitnessGoal(Integer.parseInt(editTextTargetEnergy.getText().toString()));
                }

                break;
            case "EXERCISE":
                validEnergy = ExerciseValidation();

                if (validExercise == true)
                {
                    dbHandler.addExerciseFitnessGoal(exerciseType, exerciseDuration);
                }

                break;
            default:
                // no-op
        }

        editTextTargetWeight.setText(null);
        editTextTargetEnergy.setText(null);
        editTextTargetExerciseType.setText(null);
        editTextTargetExerciseDuration.setText(null);

        //Toast.makeText(this, "Fitness goal has been created", Toast.LENGTH_SHORT).show();
    }

    public boolean WeightValidation()
    {
        validWeight = true;
        validateWeight = ((EditText) findViewById(R.id.editTextTargetWeight)).getText().toString();

        if (validateWeight.isEmpty())
        {
            Toast.makeText(this, "Target weight field is empty!", Toast.LENGTH_SHORT).show();
            validWeight = false;
        }

        if (!validateWeight.isEmpty())
        {
            if (Integer.parseInt(validateWeight) > 999)
            {
                Toast.makeText(this, "Weight in lbs should be in the range of 3 digits", Toast.LENGTH_SHORT).show();
                validWeight = false;
            }
        }

        return validWeight;
    }

    public boolean EnergyValidation()
    {
        validEnergy = true;
        validateEnergy = ((EditText) findViewById(R.id.editTextTargetEnergy)).getText().toString();

        if (validateEnergy.isEmpty())
        {
            Toast.makeText(this, "Target energy field is empty!", Toast.LENGTH_SHORT).show();
            validEnergy = false;
        }

        return validEnergy;
    }

    public boolean ExerciseValidation()
    {
        validExercise = true;
        validateExerciseType = ((EditText) findViewById(R.id.editTextTargetExerciseType)).getText().toString();
        validateExerciseDuration = ((EditText) findViewById(R.id.editTextTargetExerciseDuration)).getText().toString();

        if (validateExerciseType.isEmpty())
        {
            Toast.makeText(this, "Target exercise type field is empty!", Toast.LENGTH_SHORT).show();
            validExercise = false;
        }

        if (validateExerciseDuration.isEmpty())
        {
            Toast.makeText(this, "Target duration field is empty!", Toast.LENGTH_SHORT).show();
            validExercise = false;
        }

        if (!validateExerciseDuration.matches("^\\d\\d:\\d\\d$"))
        {
            Toast.makeText(this, "Incorrect format of target duration! Use HH:MM", Toast.LENGTH_SHORT).show();
            validExercise = false;
        }

        return validExercise;
    }
}