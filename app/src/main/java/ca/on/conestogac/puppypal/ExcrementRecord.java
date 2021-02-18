package ca.on.conestogac.puppypal;
import java.util.Date;

public class ExcrementRecord {
    private long excrementId;
    private long petId;
    private float amount;
    private Date dateTime;
    private String abnormalities;

    //Basic Constructor
    public ExcrementRecord()
    {

    }

    //Constructor for adding a new meal record to database
    public ExcrementRecord(float amount, Date date, String abnormalities)
    {
        this.amount = amount;
        this.dateTime = date;
        this.abnormalities = abnormalities;
    }
}
