package ca.on.conestogac.puppypal;
import java.util.Date;

public class MealRecord {
    private long mealId;
    private long petId;
    private float amount;
    private Date date;

    //Basic Constructor
    public MealRecord()
    {

    }

    //Constructor for adding a new meal record to database
    public MealRecord(float amount, Date date)
    {
        this.amount = amount;
        this.date = date;
    }
}
