package ca.on.conestogac.puppypal;

public class Pet {
    private long petId;
    private String name;
    private int age;
    private float weight;
    private String breed;
    private boolean gender;
    private boolean spayedNeutered;

    //Basic Constructor
    public Pet()
    {

    }

    public void UpdateWeight(WeightRecord weightRecord)
    {
        this.weight = weightRecord.GetWeight();
    }
}
