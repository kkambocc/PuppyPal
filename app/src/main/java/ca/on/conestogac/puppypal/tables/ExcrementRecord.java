package ca.on.conestogac.puppypal.tables;
import java.util.ArrayList;
import java.util.Date;

public class ExcrementRecord
{
    public static final String TABLE_NAME = "tbl_excrement";
    public static final String PRIMARY_KEY = "excrement_id";
    public static final String[] COLUMN_NAMES = {"date", "abnormalities"};
    private long excrementId;
    private long petId;
    private Date date;
    private String abnormalities;

    //Basic Constructor
    public ExcrementRecord()
    {

    }

    //Constructor for adding a new meal record to database
    public ExcrementRecord(float amount, Date date, String abnormalities)
    {
        this.date = date;
        this.abnormalities = abnormalities;
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, ((Long) this.excrementId).toString());
        array.add(1, ((Long) this.petId).toString());
        array.add(2, this.date.toString());
        array.add(3, this.abnormalities);
        return array;
    }
}