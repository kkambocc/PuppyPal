package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Float.parseFloat;
import static java.lang.Long.parseLong;

public class MealRecord {
    public static final String TABLE_NAME = "tbl_meal";

    private long mealId;
    private long petId;
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
        this.date = new Date(array.get(2));
        this.amount = parseFloat(array.get(3));
    }

    //Constructor for adding a new meal record to database
    public MealRecord(float amount, Date date)
    {
        this.amount = amount;
        this.date = date;
    }
    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0,((Long) this.mealId).toString());
        array.add(1,((Long) this.petId).toString());
        array.add(2,this.date.toString());
        array.add(3,((Float) this.amount).toString());
        return array;
    }
}
