package ca.on.conestogac.puppypal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ca.on.conestogac.puppypal.activities.AddAssistantActivity;
import ca.on.conestogac.puppypal.activities.AddFitnessGoalActivity;
import ca.on.conestogac.puppypal.activities.AddPetActivity;
import ca.on.conestogac.puppypal.activities.AddRecordActivity;
import ca.on.conestogac.puppypal.activities.EditAssistantActivity;
import ca.on.conestogac.puppypal.activities.EditPetActivity;
import ca.on.conestogac.puppypal.fragments.AssistantListFragment;
import ca.on.conestogac.puppypal.fragments.PetListFragment;

public class MainActivity extends AppCompatActivity
{
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PetListFragment petListFragment;
    private AssistantListFragment assistantListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        petListFragment = new PetListFragment();
        assistantListFragment = new AssistantListFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);

        viewPagerAdapter.addFragment(petListFragment);
        viewPagerAdapter.addFragment(assistantListFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
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

    //move
    public void AddRecordButton(View v)
    {
        Intent intent = new Intent(this, AddRecordActivity.class);
        startActivity(intent);
    }

    public void AddAssistantButton(View v)
    {
        Intent intent = new Intent(this, AddAssistantActivity.class);
        startActivity(intent);
    }

    public void EditAssistantButton(View v)
    {
        Intent intent = new Intent(this, EditAssistantActivity.class);
        startActivityForResult(intent, 0);
    }

    public void AddFitnessGoalButton(View v)
    {
        Intent intent = new Intent(this, AddFitnessGoalActivity.class);
        startActivity(intent);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> fragments = new ArrayList<>();
        //private List<String> fragmentTitles = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior)
        {
            super(fm, behavior);
        }

        //add fragment to the viewpager
        public void addFragment(Fragment fragment)
        {
            fragments.add(fragment);
            //fragmentTitles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position)
        {
            return fragments.get(position);
        }

        @Override
        public int getCount()
        {
            return fragments.size();
        }

        /*/to setup title of the tab layout
        @Nullable
        @Override
        public CharSequence getPageTitle(int position)
        {
            return fragmentTitles.get(position);
        }

         */
    }
}