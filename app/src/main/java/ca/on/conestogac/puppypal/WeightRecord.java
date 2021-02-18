package ca.on.conestogac.puppypal;

import java.util.Calendar;
import java.util.Date;

public class WeightRecord
{
    private long weightId;
    private long petId;
    private float weight;
    private Date date;

    //basic constructor
    public WeightRecord()
    {
        this.weightId = 0;
        this.weight = 0;
        this.date = Calendar.getInstance().getTime();
    }

    //Constructor for new wight record to add to database
    public WeightRecord(float weight, Date date, long petId)
    {
        //generate record Id from database
        this.petId = petId;
        this.weight = weight;
        this.date = date;
    }

    public float GetWeight()
    {
        return this.weight;
    }
}
