package ca.on.conestogac.puppypal;
import java.util.Date;

public class EnergyRecord {
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
}
