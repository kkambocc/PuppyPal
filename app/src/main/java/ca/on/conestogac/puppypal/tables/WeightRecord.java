package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Float.parseFloat;
import static java.lang.Long.parseLong;

public class WeightRecord
{
    public static final String TABLE_NAME = "tbl_weight";
    public static final String PRIMARY_KEY = "weight_id";
    public static final String[] COLUMN_NAMES = {"date", "weight"};
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

    public WeightRecord(ArrayList<String> array)
    {
        this.weightId = parseLong(array.get(0));
        this.petId = parseLong(array.get(1));
        this.date = new Date(parseLong(array.get(2)));
        this.weight = parseFloat(array.get(3));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, ((Long) this.weightId).toString());
        array.add(1, ((Long) this.petId).toString());
        array.add(2, this.date.toString());
        array.add(3, ((Float) this.weight).toString());
        return array;
    }

    public float GetWeight()
    {
        return this.weight;
    }

    public Date GetDate()
    {
        return this.date;
    }

    public long GetPetId()
    {
        return this.petId;
    }
}