package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.App;

import static java.lang.Long.parseLong;

public class Assistant
{

    public static final String TABLE_NAME = App.getContext().getString(R.string.assistant_table_name);
    public static final String PRIMARY_KEY = App.getContext().getString(R.string.assistant_table_pk);
    public static final String[] COLUMN_NAMES = App.getContext().getResources().getStringArray(R.array.assistant_columns);

    private final long assistantId;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String title;
    private final String generalDescription;

    //Basic Constructor
    public Assistant()
    {
        this.assistantId = 0;
        this.name = "";
        this.phoneNumber = "";
        this.address = "";
        this.title = "";
        this.generalDescription = "";
    }

    //constructing from database provided arraylist
    public Assistant(ArrayList<String> array)
    {
        this.assistantId = parseLong(array.get(0));
        this.name = array.get(1);
        this.phoneNumber = array.get(2);
        this.address = array.get(3);
        this.title = array.get(4);
        this.generalDescription = array.get(5);
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, "" + this.assistantId);
        array.add(1, "" + this.name);
        array.add(2, "" + this.phoneNumber);
        array.add(3, "" + this.address);
        array.add(4, "" + this.title);
        array.add(5, "" + this.generalDescription);
        return array;
    }
}
