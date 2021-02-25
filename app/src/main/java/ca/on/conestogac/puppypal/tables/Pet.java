package ca.on.conestogac.puppypal.tables;

import java.util.ArrayList;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class Pet {
    public static final String TABLE_NAME = "tbl_pet";

    private long petId;
    private String name;
    private int age;
    private float weight;
    private String breed;
    private int gender;
    private int spayedNeutered;

    //Basic Constructor
    public Pet()
    {
        this.petId = 10000;
        this.name = "";
        this.age = 0;
        this.weight = 0;
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
        this.weight = parseFloat(array.get(3));
        this.breed = array.get(4);
        this.gender = parseInt(array.get(5));
        this.spayedNeutered = parseInt(array.get(6));
    }

    public ArrayList<String> toArray()
    {
        ArrayList<String> array = new ArrayList<>();
        array.add(0,"" + this.petId);
        array.add(1,"" + this.name);
        array.add(2,"" + this.age);
        array.add(3,"" + this.weight);
        array.add(4,"" + this.breed);
        array.add(5,"" + this.gender);
        array.add(6,"" + this.spayedNeutered);
        return array;
    }

    public long getPetId() {return this.petId;}
    public void setPetId(long petId) {this.petId = petId;}

    public String getName() {return this.name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return this.age;}
    public void setAge(int age) {this.age = age;}

    public float getWeight() {return this.weight;}
    public void setWeight(float weight) {this.weight = weight;}

    public String getBreed() {return this.breed;}
    public void setBreed(String breed) {this.breed = breed;}

    public int getGender() {return this.gender;}
    public void setGender(int gender) {this.gender = gender;}

    public int getSpayedNeutered() {return this.spayedNeutered;}
    public void setSpayedNeutered(int spayedNeutered) {this.spayedNeutered = spayedNeutered;}

    public void UpdateWeight(WeightRecord weightRecord) {this.weight = weightRecord.GetWeight();}
}
