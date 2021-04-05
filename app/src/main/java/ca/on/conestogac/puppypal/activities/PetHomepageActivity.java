package ca.on.conestogac.puppypal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.fragments.PetDataFragment;
import ca.on.conestogac.puppypal.fragments.ViewPagerAdapter;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

public class PetHomepageActivity extends AppCompatActivity
{
    private Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_homepage);
        DBHandler database = new DBHandler(this);

        pet = new Pet(database.ReadSingleEntry(getIntent().getStringExtra(Pet.PRIMARY_KEY), Pet.TABLE_NAME));
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        TextView nameText = findViewById(R.id.textName);
        ViewPager viewPager = findViewById(R.id.viewPagerPetData);
        TabLayout tabLayout = findViewById(R.id.tabSelector);
        String petId = pet.getPetId().toString();

        PetDataFragment weightFragment = new PetDataFragment(WeightRecord.TABLE_NAME, petId);
        PetDataFragment mealFragment = new PetDataFragment(MealRecord.TABLE_NAME, petId);
        PetDataFragment exerciseFragment = new PetDataFragment(ExerciseRecord.TABLE_NAME, petId);
        PetDataFragment energyFragment = new PetDataFragment(EnergyRecord.TABLE_NAME, petId);


        nameText.setText(pet.getName().toUpperCase());

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(weightFragment, "WEIGHT");
        viewPagerAdapter.addFragment(mealFragment, "MEAL");
        viewPagerAdapter.addFragment(exerciseFragment, "EXERCISE");
        viewPagerAdapter.addFragment(energyFragment, "ENERGY");
        viewPager.setAdapter(viewPagerAdapter);
    }

    public void EditPetButton(View view)
    {
        Intent intent = new Intent(this, EditPetActivity.class);
        intent.putExtra(Pet.PRIMARY_KEY, pet.getPetId().toString());
        startActivity(intent);
    }

    public void AddFitnessGoalButton(View v)
    {
        Intent intent = new Intent(this, AddFitnessGoalActivity.class);
        startActivity(intent);
    }
}