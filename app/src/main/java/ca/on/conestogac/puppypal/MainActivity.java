package ca.on.conestogac.puppypal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import ca.on.conestogac.puppypal.activities.AddAssistantActivity;
import ca.on.conestogac.puppypal.activities.AddFitnessGoalActivity;
import ca.on.conestogac.puppypal.activities.AddPetActivity;
import ca.on.conestogac.puppypal.activities.EditPetActivity;
import ca.on.conestogac.puppypal.fragments.AssistantListFragment;
import ca.on.conestogac.puppypal.fragments.PetListFragment;
import ca.on.conestogac.puppypal.fragments.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        ViewPager viewPager;
        TabLayout tabLayout;
        PetListFragment petListFragment = new PetListFragment();
        AssistantListFragment assistantListFragment = new AssistantListFragment();

        viewPager = findViewById(R.id.viewPagerMain);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(petListFragment,"Pets");
        viewPagerAdapter.addFragment(assistantListFragment,"Assistants");
        viewPager.setAdapter(viewPagerAdapter);
    }


    public void AddPetButton(View v)
    {
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }

    //move
    public void EditPetButton(View v)
    {
        Intent intent = new Intent(this, EditPetActivity.class);
        startActivity(intent);
    }

    public void AddAssistantButton(View v)
    {
        Intent intent = new Intent(this, AddAssistantActivity.class);
        startActivity(intent);
    }

    public void AddFitnessGoalButton(View v)
    {
        Intent intent = new Intent(this, AddFitnessGoalActivity.class);
        startActivity(intent);
    }
}