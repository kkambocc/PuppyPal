package ca.on.conestogac.puppypal.tables;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.App;

import static java.lang.Long.parseLong;

public class ExerciseRecord
{
    public static final String TABLE_NAME = App.getContext().getString(R.string.exercise_table_name);
    public static final String PRIMARY_KEY = App.getContext().getString(R.string.exercise_pk);
    public static final String[] COLUMN_NAMES = App.getContext().getResources().getStringArray(R.array.exercise_columns);
    
    private long exerciseId;
    private long petId;
    private Date date;
    private String type;
    private Time duration;

    //Basic Constructor
    public ExerciseRecord()
    {

    }

    public ExerciseRecord(ArrayList<String> array)
    {
        this.exerciseId = parseLong(array.get(0));
        this.petId = parseLong(array.get(1));
        this.date = new Date(parseLong(array.get(2)));
        this.type = array.get(3);
        this.duration = new Time(parseLong(array.get(4)));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, ((Long) this.exerciseId).toString());
        array.add(1, ((Long) this.petId).toString());
        array.add(2, this.date.toString());
        array.add(3, this.type);
        array.add(4, this.duration.toString());
        return array;
    }

    public Time GetDuration()
    {
        return this.duration;
    }

    public Date GetDate()
    {
        return this.date;
    }
}