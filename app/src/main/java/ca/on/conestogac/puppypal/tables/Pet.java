package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.App;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class Pet
{
    public static final String TABLE_NAME = App.getContext().getString(R.string.pet_table_name);
    public static final String PRIMARY_KEY = App.getContext().getString(R.string.pet_pk);
    public static final String[] COLUMN_NAMES = App.getContext().getResources().getStringArray(R.array.pet_columns);

    private final long petId;
    private String name;
    private int age;
    private String breed;
    private int gender;
    private int spayedNeutered;

    //Basic Constructor
    public Pet()
    {
        this.petId = 0;
        this.name = "";
        this.age = 0;
        this.breed = "";
        this.gender = 0;
        this.spayedNeutered = 0;

    }

    //constructing from database provided arraylist
    public Pet(ArrayList<String> array)
    {
        this.petId = parseLong(array.get(0));
        this.name = array.get(1);
        this.age = parseInt(array.get(2));
        this.breed = array.get(3);
        this.gender = parseInt(array.get(4));
        this.spayedNeutered = parseInt(array.get(5));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0, "" + this.petId);
        array.add(1, "" + this.name);
        array.add(2, "" + this.age);
        array.add(3, "" + this.breed);
        array.add(4, "" + this.gender);
        array.add(5, "" + this.spayedNeutered);
        return array;
    }

    public Long getPetId() {return this.petId;}

    public String getName() {return this.name;}

    public void setName(String name) {this.name = name;}

    public Integer getAge() {return this.age;}

    public void setAge(int age) {this.age = age;}

    public String getBreed() {return this.breed;}

    public void setBreed(String breed) {this.breed = breed;}

    public Integer getGender() {return this.gender;}

    public void setGender(int gender) {this.gender = gender;}

    public Integer getSpayedNeutered() {return this.spayedNeutered;}

    public void setSpayedNeutered(int spayedNeutered) {this.spayedNeutered = spayedNeutered;}
}