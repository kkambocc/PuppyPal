package ca.on.conestogac.puppypal;
import java.sql.Time;
import java.util.Date;

public class ExerciseRecord {
    private long exerciseId;
    private long petId;
    private String type;
    private Time duration;
    private Date date;

    //Basic Constructor
    public ExerciseRecord()
    {

    }

    //Constructor for adding a new meal record to database
    public ExerciseRecord(String type, Time duration, Date date)
    {
        this.type = type;
        this.duration = duration;
        this.date = date;
    }
}
