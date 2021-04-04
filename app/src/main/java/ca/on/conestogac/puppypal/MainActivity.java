package ca.on.conestogac.puppypal;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import ca.on.conestogac.puppypal.activities.AddAssistantActivity;
import ca.on.conestogac.puppypal.activities.AddFitnessGoalActivity;
import ca.on.conestogac.puppypal.activities.AddPetActivity;
import ca.on.conestogac.puppypal.activities.AddRecordActivity;
import ca.on.conestogac.puppypal.activities.EditAssistantActivity;
import ca.on.conestogac.puppypal.activities.EditPetActivity;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

import static java.lang.Long.parseLong;
public class MainActivity extends AppCompatActivity
{

    private GraphView graph;
    private DBHandler database;
    private RadioGroup graphSelector;
    private GridLabelRenderer renderer;
    private Viewport viewport;
    private Random random;
    public static CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        database = new DBHandler(this);
        graph = findViewById(R.id.graph);
        graphSelector = findViewById(R.id.graphType);

        viewport = graph.getViewport();
        renderer = graph.getGridLabelRenderer();
        renderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        renderer.setLabelFormatter(new DateAsXAxisLabelFormatter(this,dateFormat));
        renderer.setHumanRounding(false, true);
        renderer.setNumVerticalLabels(10);
        random =new Random();

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

        SelectGraph();

        viewport.setScrollable(true);
        viewport.setScrollableY(true);
        viewport.setScrollableY(true);
        viewport.setScalable(true);
        viewport.scrollToEnd();
    }

    private void DisplayGraph(String primaryKeyName, String tableName)
    {
        ArrayList<String> ids = database.ReadSingleColumn(primaryKeyName, tableName, "date");
        ArrayList<String> petIds = database.ReadSingleColumn(Pet.PRIMARY_KEY, Pet.TABLE_NAME);

        graph.removeAllSeries();

        for (String petId : petIds)
        {
            LineGraphSeries series = new LineGraphSeries<>();

            for (String id : ids)
            {
                DataPoint dataPoint;
                long recordPetId;
                double x;
                double y;
                ArrayList recordData = database.ReadSingleEntry(id,tableName);

                //create datapoint to be added to series
                switch (tableName)
                {
                    //case WeightRecord.TABLE_NAME:
                    default:
                        WeightRecord weight = new WeightRecord(recordData);
                        x = weight.GetDate().getTime();
                        y = weight.GetWeight();
                        recordPetId = weight.GetPetId();
                        break;
                    case MealRecord.TABLE_NAME:
                        MealRecord meal = new MealRecord(recordData);
                        x = meal.GetDate().getTime();
                        y = meal.GetAmount();
                        recordPetId = meal.GetPetId();
                        break;
                    case ExerciseRecord.TABLE_NAME:
                        ExerciseRecord exercise = new ExerciseRecord(recordData);
                        x = exercise.GetDate().getTime();
                        y = exercise.GetDuration().getTime();
                        recordPetId = exercise.GetPetId();
                        break;
                    case EnergyRecord.TABLE_NAME:
                        EnergyRecord energy = new EnergyRecord(recordData);
                        x = energy.GetDate().getTime();
                        y = energy.GetEnergyLevel();
                        recordPetId = energy.GetPetId();
                        break;
                }

                dataPoint = new DataPoint(x, y);

                if (parseLong(petId) == recordPetId)
                {
                    series.appendData(dataPoint, true, ids.size());
                    series.setColor(Color.argb(255,random.nextInt(256),random.nextInt(256),random.nextInt(256)));
                }

            }

            //int colour = Color.parseColor(Integer.toHexString(16777215/parseInt(petId)));
            //series.setColor(colour);
            series.setDrawDataPoints(true);
            graph.addSeries(series);
        }
    }

    public void ChangeGraph(View v)
    {
        SelectGraph();
    }

    private void SelectGraph()
    {
        switch (graphSelector.getCheckedRadioButtonId())
        {
            //case R.id.weightGraph:
            default:
                DisplayGraph(WeightRecord.PRIMARY_KEY,WeightRecord.TABLE_NAME);
                break;
            case R.id.mealGraph:
                DisplayGraph(MealRecord.PRIMARY_KEY,MealRecord.TABLE_NAME);
                break;
            case R.id.exerciseGraph:
                DisplayGraph(ExerciseRecord.PRIMARY_KEY,ExerciseRecord.TABLE_NAME);
                break;
            case R.id.energyGraph:
                DisplayGraph(EnergyRecord.PRIMARY_KEY,EnergyRecord.TABLE_NAME);
                break;
        }
    }

    public void AddPetButton(View v)
    {
        Intent intent = new Intent(this, AddPetActivity.class);
        startActivity(intent);
    }

    public void EditPetButton(View v)
    {
        Intent intent = new Intent(this, EditPetActivity.class);
        startActivity(intent);
    }

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