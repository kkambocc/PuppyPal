package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;

import static java.lang.Long.parseLong;

public class Assistant
{
    public static final String TABLE_NAME = "tbl_assistant";
    public static final String PRIMARY_KEY = "assistant_id";
    public static final String[] COLUMN_NAMES = {"name", "phone_number", "address", "title", "general_description"};

    private final long assistantId;
    private String name;
    private String phoneNumber;
    private String address;
    private String title;
    private String generalDescription;

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
