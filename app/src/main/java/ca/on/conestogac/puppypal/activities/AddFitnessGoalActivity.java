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

    public static final String SELECT_RADIO_BUTTON_WEIGHT = "WEIGHT";
    public static final String SELECT_RADIO_BUTTON_ENERGY = "ENERGY";
    public static final String SELECT_RADIO_BUTTON_EXERCISE = "EXERCISE";

    public String radiobuttonSelection;
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

        radiobuttonSelection = SELECT_RADIO_BUTTON_EXERCISE;

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

        Button buttonAddFitnessGoal = findViewById(R.id.buttonAddFitnessGoal);

        buttonAddFitnessGoal.setOnClickListener(v -> {
            weight = editTextTargetWeight.getText().toString();
            energy = editTextTargetEnergy.getText().toString();
            exerciseType = editTextTargetExerciseType.getText().toString();
            exerciseDuration = editTextTargetExerciseDuration.getText().toString();

            addFitnessGoal();
        });
    }

    //modify activity for weight goal
    public void onClickRadioButtonWeight(View v)
    {
        radiobuttonSelection = SELECT_RADIO_BUTTON_WEIGHT;

        textViewTargetWeightLabel.setVisibility(View.VISIBLE);
        editTextTargetWeight.setVisibility(View.VISIBLE);

        textViewTargetEnergyLabel.setVisibility(View.GONE);
        editTextTargetEnergy.setVisibility(View.GONE);
        textViewTargetExerciseTypeLabel.setVisibility(View.GONE);
        editTextTargetExerciseType.setVisibility(View.GONE);
        textViewTargetExerciseDurationLabel.setVisibility(View.GONE);
        editTextTargetExerciseDuration.setVisibility(View.GONE);
    }

    //modify activity for energy goal
    public void onClickRadioButtonEnergy(View v)
    {
        radiobuttonSelection = SELECT_RADIO_BUTTON_ENERGY;

        textViewTargetEnergyLabel.setVisibility(View.VISIBLE);
        editTextTargetEnergy.setVisibility(View.VISIBLE);

        textViewTargetWeightLabel.setVisibility(View.GONE);
        editTextTargetWeight.setVisibility(View.GONE);
        textViewTargetExerciseTypeLabel.setVisibility(View.GONE);
        editTextTargetExerciseType.setVisibility(View.GONE);
        textViewTargetExerciseDurationLabel.setVisibility(View.GONE);
        editTextTargetExerciseDuration.setVisibility(View.GONE);
    }

    //modify activity for exercise goal
    public void onClickRadioButtonExercise(View v)
    {
        radiobuttonSelection = SELECT_RADIO_BUTTON_EXERCISE;

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
        switch (radiobuttonSelection)
        {
            case "WEIGHT":
                //String thisWeight = ((EditText) findViewById(R.id.editTextTargetWeight)).getText().toString();
                validEnergy = WeightValidation(weight);

                if (validWeight)
                {
                    //dbHandler.addWeightFitnessGoal(Double.parseDouble(editTextTargetWeight.getText().toString()));
                    dbHandler.addWeightFitnessGoal(Double.parseDouble(weight));
                }

                break;
            case "ENERGY":
                //String thisEnergy = ((EditText) findViewById(R.id.editTextTargetEnergy)).getText().toString();
                validEnergy = EnergyValidation(energy);

                if (validEnergy)
                {
                    // needs to be int not string
                    //dbHandler.addEnergyFitnessGoal(Integer.parseInt(editTextTargetEnergy.getText().toString()));
                    dbHandler.addEnergyFitnessGoal(Integer.parseInt(energy));
                }

                break;
            case "EXERCISE":
                //String thisExerciseType = ((EditText) findViewById(R.id.editTextTargetExerciseType)).getText().toString();
                //String thisExerciseDuration = ((EditText) findViewById(R.id.editTextTargetExerciseDuration)).getText().toString();
                validEnergy = ExerciseValidation(exerciseType, exerciseDuration);

                if (validExercise)
                {
                    dbHandler.addExerciseFitnessGoal(exerciseType, Long.parseLong(exerciseDuration));
                }

                break;
            default:
                // no-op
        }

        //editTextTargetWeight.setText(null);
        //editTextTargetEnergy.setText(null);
        //editTextTargetExerciseType.setText(null);
        //editTextTargetExerciseDuration.setText(null);

        //Toast.makeText(this, "Fitness goal has been created", Toast.LENGTH_SHORT).show();
    }

    public boolean WeightValidation(String _weight)
    {
        validWeight = true;
        validateWeight = _weight;

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

    public boolean EnergyValidation(String _energy)
    {
        validEnergy = true;
        validateEnergy = _energy;

        if (validateEnergy.isEmpty())
        {
            Toast.makeText(this, "Target energy field is empty!", Toast.LENGTH_SHORT).show();
            validEnergy = false;
        }

        return validEnergy;
    }

    public boolean ExerciseValidation(String _exerciseType, String _exerciseDuration)
    {
        validExercise = true;

        validateExerciseType = _exerciseType;
        validateExerciseDuration = _exerciseDuration;

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

        /*
        if (!validateExerciseDuration.matches("^\\d\\d:\\d\\d$"))
        {
            if (validateExerciseDuration.matches("^\\d:\\d\\d$"))
            {
                exerciseDuration = "0" + exerciseDuration;

                //editTextTargetExerciseDuration.setText(exerciseDuration);
            }
            else
            {
                Toast.makeText(this, "Incorrect format of target duration! Use HH:MM", Toast.LENGTH_SHORT).show();
                validExercise = false;
            }
        }
        */

        return validExercise;
    }
}