package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;
import java.util.Date;

import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.App;

import static java.lang.Float.parseFloat;
import static java.lang.Long.parseLong;

public class MealRecord
{
    public static final String TABLE_NAME = App.getContext().getString(R.string.meal_table_name);
    public static final String PRIMARY_KEY = App.getContext().getString(R.string.meal_pk);
    public static final String[] COLUMN_NAMES = App.getContext().getResources().getStringArray(R.array.meal_columns);

    private final long mealId;
    private final long petId;
    private final Date date;
    private final float amount;

    //Basic Constructor
    public MealRecord()
    {
        this.mealId = 0;
        this.petId = 0;
        this.date = new Date();
        this.amount = 0;
    }

    //constructing from database provided arraylist
    public MealRecord(ArrayList<String> array)
    {
        this.mealId = parseLong(array.get(0));
        this.petId = parseLong(array.get(1));
        this.date = new Date(parseLong(array.get(2)));
        this.amount = parseFloat(array.get(3));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, ((Long) this.mealId).toString());
        array.add(1, ((Long) this.petId).toString());
        array.add(2, this.date.toString());
        array.add(3, ((Float) this.amount).toString());
        return array;
    }

    public float GetAmount()
    {
        return this.amount;
    }

    public Date GetDate()
    {
        return this.date;
    }
}