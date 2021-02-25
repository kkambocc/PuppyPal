package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeightRecord
{
    public static final String TABLE_NAME = "tbl_weight";
    private long weightId;
    private long petId;
    private final Date date;
    private final float weight;

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
    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0,((Long) this.weightId).toString());
        array.add(1,((Long) this.petId).toString());
        array.add(2,this.date.toString());
        array.add(3,((Float) this.weight).toString());
        return array;
    }

    public float GetWeight()
    {
        return this.weight;
    }
}
