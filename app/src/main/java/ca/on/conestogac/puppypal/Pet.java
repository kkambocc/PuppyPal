package ca.on.conestogac.puppypal;

public class Pet {
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
        this.petId = 0;
        this.name = "";
        this.age = 0;
        this.weight = 0;
        this.breed = "";
        this.gender = 0;
        this.spayedNeutered = 0;
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

    public void UpdateWeight(WeightRecord weightRecord)
    {
        this.weight = weightRecord.GetWeight();
    }

    public void UpdatePet(String columnName, String value) {}
}
