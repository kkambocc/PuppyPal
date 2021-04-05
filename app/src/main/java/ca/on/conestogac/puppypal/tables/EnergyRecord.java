package ca.on.conestogac.puppypal.tables;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.Long.parseLong;

public class EnergyRecord
{
    public static final String TABLE_NAME = "tbl_energy";
    public static final String PRIMARY_KEY = "energy_level_id";
    public static final String[] COLUMN_NAMES = {"date", "energy_level"};


    private long energyId;
    private long petId;
    private final Date date;
    private final int energyLevel;

    public EnergyRecord(ArrayList<String> array)
    {
        this.energyId = parseLong(array.get(0));
        this.petId = parseLong(array.get(1));
        this.date = new Date(parseLong(array.get(2)));
        this.energyLevel = Integer.parseInt(array.get(3));
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
        array.add(0, ((Long) this.energyId).toString());
        array.add(1, ((Long) this.petId).toString());
        array.add(2, this.date.toString());
        array.add(3, ((Integer) this.energyLevel).toString());
        return array;
    }

    public float GetEnergyLevel()
    {
        return this.energyLevel;
    }

    public Date GetDate()
    {
        return this.date;
    }
}