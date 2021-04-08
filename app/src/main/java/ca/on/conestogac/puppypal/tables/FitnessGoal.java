package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class FitnessGoal
{
    public static final String TABLE_NAME = "tbl_fitness_goal";
    public static final String PRIMARY_KEY = "fitness_goal_id";
    public static final String[] COLUMN_NAMES = {"target_weight", "target_energy_level", "target_exercise_type", "target_exercise_duration"};

    private final long fitnessGoalId;
    private double targetWeight;
    private int targetEnergyLevel;
    private String targetExerciseType;
    private long targetExerciseDuration;

    //Basic Constructor
    public FitnessGoal()
    {
        this.fitnessGoalId = 0;
        this.targetWeight = 0;
        this.targetEnergyLevel = 0;
        this.targetExerciseType = "";
        this.targetExerciseDuration = 0;
    }

    //constructing from database provided arraylist
    public FitnessGoal(ArrayList<String> array)
    {
        this.fitnessGoalId = parseLong(array.get(0));
        this.targetWeight = Double.parseDouble(array.get(1));
        this.targetEnergyLevel = parseInt(array.get(2));
        this.targetExerciseType = array.get(3);
        this.targetExerciseDuration = parseLong(array.get(4));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, "" + this.fitnessGoalId);
        array.add(1, "" + this.targetWeight);
        array.add(2, "" + this.targetEnergyLevel);
        array.add(3, "" + this.targetExerciseType);
        array.add(4, "" + this.targetExerciseDuration);
        return array;
    }
}
