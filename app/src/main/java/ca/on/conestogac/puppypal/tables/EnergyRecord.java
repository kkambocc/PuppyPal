package ca.on.conestogac.puppypal.tables;
import java.util.ArrayList;
import java.util.Date;

public class EnergyRecord
{
    public static final String TABLE_NAME = "tbl_energy";
    private long energyId;
    private long petId;
    private Date date;
    private int energyLevel;

    //Basic Constructor
    public EnergyRecord()
    {

    }

    //Constructor for adding a new meal record to database
    public EnergyRecord(int energyLevel, Date date)
    {
        this.energyLevel = energyLevel;
        this.date = date;
    }
    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0,((Long) this.energyId).toString());
        array.add(1,((Long) this.petId).toString());
        array.add(2,this.date.toString());
        array.add(3,((Integer) this.energyLevel).toString());
        return array;
    }
}
