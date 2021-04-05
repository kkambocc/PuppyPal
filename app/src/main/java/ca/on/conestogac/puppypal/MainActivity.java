package ca.on.conestogac.puppypal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
    public static CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        countDownTimer = new CountDownTimer(1800000,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                MainActivity.notifyUser(getApplicationContext());
                countDownTimer.start();
            }
        };
        countDownTimer.start();
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
    public static void notifyUser(Context context)
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel=new NotificationChannel("test","Remember to Insert data", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("When a 30 minutes have passed since the data was inserted");
            NotificationManager notificationManager= context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        NotificationCompat.Builder builder;
        NotificationManagerCompat managerCompat;
        builder=new NotificationCompat.Builder(context,"test")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setContentTitle("Enter the input")
                .setContentText("You haven't been enter the input for 30 minutes.")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        managerCompat=NotificationManagerCompat.from(context);
        managerCompat.notify(1,builder.build());
    }
}