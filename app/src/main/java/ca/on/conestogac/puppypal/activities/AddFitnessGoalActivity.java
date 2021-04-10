package ca.on.conestogac.puppypal.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

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
    public boolean isUpdate;

    private Integer fitnessGoalId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fitness_goal);

        fitnessGoalId  = getIntent().getIntExtra("id", -1);

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
        Button buttonDeleteFitnessGoal = findViewById(R.id.buttonDeleteFitnessGoal);

        if (fitnessGoalId != -1)
        {
            buttonAddFitnessGoal.setText(R.string.update_fitness_goal);
            buttonDeleteFitnessGoal.setVisibility(View.VISIBLE);
            buttonDeleteFitnessGoal.setEnabled(true);
            buttonDeleteFitnessGoal.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddFitnessGoalActivity.this);
                builder.setTitle("Delete Alert");
                builder.setMessage("Would you like to delete this fitness goal?");
                builder.setPositiveButton("YES", (dialog, which) -> {
                    System.out.println("Delete method Called");
                    deleteFitnessGoal(fitnessGoalId);
                });
                builder.setNegativeButton("NO", (dialog, which) -> {

                });
                builder.show();

            });
            System.out.println("Success: Clicked Id is " + fitnessGoalId);

            ArrayList<String> fitnessGoal = dbHandler.ReadSingleEntry(Integer.toString(fitnessGoalId), "tbl_fitness_goal");
            editTextTargetWeight.setText(fitnessGoal.get(1));
            editTextTargetEnergy.setText(fitnessGoal.get(2));
            editTextTargetExerciseType.setText(fitnessGoal.get(3));
            editTextTargetExerciseDuration.setText(fitnessGoal.get(4));
        }

        buttonAddFitnessGoal.setOnClickListener(view -> {
            weight = editTextTargetWeight.getText().toString();
            energy = editTextTargetEnergy.getText().toString();
            exerciseType = editTextTargetExerciseType.getText().toString();
            exerciseDuration = editTextTargetExerciseDuration.getText().toString();

            if (fitnessGoalId != -1)
            {
                isUpdate = true;

                System.out.println("Update Method Called");
                addFitnessGoal();
            }
            else
            {
                isUpdate = false;

                System.out.println("Add Method Called");
                addFitnessGoal();
            }
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
                validEnergy = WeightValidation(weight);

                if (validWeight)
                {
                    energy = "0";
                    exerciseType = "";
                    exerciseDuration = "0";
                    dbHandler.addFitnessGoal(Double.parseDouble(weight), Integer.parseInt(energy), exerciseType, Long.parseLong(exerciseDuration), isUpdate, fitnessGoalId);
                }

                break;
            case "ENERGY":
                validEnergy = EnergyValidation(energy);

                if (validEnergy)
                {
                    weight = "0";
                    exerciseType = "";
                    exerciseDuration = "0";
                    dbHandler.addFitnessGoal(Double.parseDouble(weight), Integer.parseInt(energy), exerciseType, Long.parseLong(exerciseDuration), isUpdate, fitnessGoalId);
                }

                break;
            case "EXERCISE":
                validEnergy = ExerciseValidation(exerciseType, exerciseDuration);

                if (validExercise)
                {
                    weight = "0";
                    energy = "0";
                    dbHandler.addFitnessGoal(Double.parseDouble(weight), Integer.parseInt(energy), exerciseType, Long.parseLong(exerciseDuration), isUpdate, fitnessGoalId);
                }

                break;
            default:
                // no-op
        }

        if (!isUpdate)
        {
            Toast.makeText(this, "Fitness goal has been added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Fitness goal has been updated", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, FitnessGoalActivity.class);
        startActivity(intent);
        finish();
    }

    public void deleteFitnessGoal(int deleteID)
    {
        dbHandler.deleteFitnessGoal(deleteID);
        Toast.makeText(this, "Fitness goal has been deleted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FitnessGoalActivity.class);
        startActivity(intent);
        finish();
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

        return validExercise;
    }
}