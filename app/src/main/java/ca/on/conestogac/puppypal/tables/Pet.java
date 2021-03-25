package ca.on.conestogac.puppypal.tables;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.activities.AddPetActivity;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

public class Pet  {
    public static final String TABLE_NAME = "tbl_pet";
    public static final String PRIMARY_KEY = "pet_id";

    private long petId;
    private String name;
    private int age;
    private float weight;
    private String breed;
    private int gender;
    private int spayedNeutered;

    Context addPetActivityContext;
    //Basic Constructor
    public Pet(Context addPetActivityContext)
    {
        this.petId = 0;
        this.name = "";
        this.age = 0;
        this.weight = 0;
        this.breed = "";
        this.gender = 0;
        this.spayedNeutered = 0;

        this.addPetActivityContext = addPetActivityContext;
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

    public Pet() {

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

    public boolean breedValidation(String breed) {
        boolean breedBoolean = false;
        if (breed.isEmpty())
        {
            Toast.makeText(addPetActivityContext, "Breed field is empty !", Toast.LENGTH_SHORT).show();
            return breedBoolean;
        }

        else if (!breed.matches("^[a-zA-Z ]*$"))
        {
            Toast.makeText(addPetActivityContext, "Breed should only contains alphabetic letters", Toast.LENGTH_SHORT).show();
            return breedBoolean;
        }

        else if (breed.length() >20)
        {
            Toast.makeText(addPetActivityContext, "Breed should be no more than 20 characters long", Toast.LENGTH_SHORT).show();
            return breedBoolean;
        }else{
        breedBoolean = true;
        }
        return breedBoolean;
    }

    public boolean weightValidation(String weight) {
        boolean weightBoolean = false;
        if (weight.isEmpty())
        {
            Toast.makeText(addPetActivityContext, "Weight field is empty !", Toast.LENGTH_SHORT).show();
            return weightBoolean;
        }

        if (!weight.isEmpty()) {
            if (Integer.parseInt(weight) > 999) {
                Toast.makeText(addPetActivityContext, "Weight in lbs should be in the range of 3 digits", Toast.LENGTH_SHORT).show();
                return weightBoolean;
            }
        }
        weightBoolean = true;
        return weightBoolean;
    }

    public boolean ageValidation(String age) {
        boolean ageBoolean = false;
        if (age.isEmpty())
        {
            Toast.makeText(addPetActivityContext, "Age field is empty !", Toast.LENGTH_SHORT).show();
            return ageBoolean;
        }

        if (!age.isEmpty())
        {
            if (Integer.parseInt(age) > 99) {
                Toast.makeText(addPetActivityContext, "Age should be in the range of 2 digits", Toast.LENGTH_SHORT).show();
                return ageBoolean;
            }
        }
        ageBoolean = true;
        return ageBoolean;
    }

    public boolean nameValidation(String name) {
        boolean nameBoolean= false;
        if (name.isEmpty())
        {
            Toast.makeText(addPetActivityContext, "Name field is empty !", Toast.LENGTH_SHORT).show();
            return nameBoolean;
        }
        else if (!name.matches("^[a-zA-Z]*$"))
        {
            Toast.makeText(addPetActivityContext, "Name should only contains alphabetic letters", Toast.LENGTH_SHORT).show();
            return nameBoolean;
        }
        else if (name.length() >20)
        {
            Toast.makeText(addPetActivityContext, "Name should be no more than 20 characters long", Toast.LENGTH_SHORT).show();
            return nameBoolean;
        }else{
            nameBoolean = true;
        }
        return nameBoolean;
    }

    public void validateAndAdd(String name, String age, String weight, String breed, int gender, boolean spayedNeutered) {
        System.out.println("AT_ValidateAndAdd: "+name+" , "+age+" , "+weight+" , "+breed+" , "+gender+" , "+spayedNeutered );
        AddPetActivity addPetActivity = new AddPetActivity();

        if (!nameValidation(name))
        {
            return;
        }

        if (!ageValidation(age))
        {
            return;
        }

        if (!weightValidation(weight))
        {
            return;
        }

        if (!breedValidation(breed))
        {
            return;
        }
        addPetActivity.AddAPet(name,age,weight,breed,gender,spayedNeutered,addPetActivityContext);
    }
}
