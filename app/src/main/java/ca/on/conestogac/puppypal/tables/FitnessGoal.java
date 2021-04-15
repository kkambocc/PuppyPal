package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.App;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class FitnessGoal
{
    public static final String TABLE_NAME = App.getContext().getString(R.string.fitness_table_name);
    public static final String PRIMARY_KEY = App.getContext().getString(R.string.fitness_pk);
    public static final String[] COLUMN_NAMES = App.getContext().getResources().getStringArray(R.array.fitness_columns);

    private final long fitnessGoalId;
    private final long petId;
    private final double targetWeight;
    private final int targetEnergyLevel;
    private final String targetExerciseType;
    private final long targetExerciseDuration;

    //Basic Constructor
    public FitnessGoal()
    {
        this.fitnessGoalId = 0;
        this.petId = 0;
        this.targetWeight = 0;
        this.targetEnergyLevel = 0;
        this.targetExerciseType = "";
        this.targetExerciseDuration = 0;
    }

    //constructing from database provided arraylist
    public FitnessGoal(ArrayList<String> array)
    {
        this.fitnessGoalId = parseLong(array.get(0));
        this.petId = parseLong(array.get(1));
        this.targetWeight = Double.parseDouble(array.get(2));
        this.targetEnergyLevel = parseInt(array.get(3));
        this.targetExerciseType = array.get(4);
        this.targetExerciseDuration = parseLong(array.get(5));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, "" + this.fitnessGoalId);
        array.add(1, "" + this.petId);
        array.add(2, "" + this.targetWeight);
        array.add(3, "" + this.targetEnergyLevel);
        array.add(4, "" + this.targetExerciseType);
        array.add(5, "" + this.targetExerciseDuration);
        return array;
    }
}
