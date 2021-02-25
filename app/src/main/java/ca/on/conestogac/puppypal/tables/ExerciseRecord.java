package ca.on.conestogac.puppypal.tables;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class ExerciseRecord {
    public static final String TABLE_NAME = "tbl_exercise";
    private long exerciseId;
    private long petId;
    private Date date;
    private String type;
    private Time duration;

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
    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0,((Long) this.exerciseId).toString());
        array.add(1,((Long) this.petId).toString());
        array.add(2,this.date.toString());
        array.add(3,this.type);
        array.add(4,this.duration.toString());
        return array;
    }
}
