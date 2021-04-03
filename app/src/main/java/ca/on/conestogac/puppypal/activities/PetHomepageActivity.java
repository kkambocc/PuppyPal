package ca.on.conestogac.puppypal.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

import static java.lang.Long.parseLong;

public class PetHomepageActivity extends AppCompatActivity
{

    private GraphView graph;
    private DBHandler database;
    private RadioGroup graphSelector;
    private Viewport viewport;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_homepage);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        database = new DBHandler(this);
        graph = findViewById(R.id.graph);
        graphSelector = findViewById(R.id.graphType);

        viewport = graph.getViewport();
        GridLabelRenderer renderer = graph.getGridLabelRenderer();
        renderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        renderer.setLabelFormatter(new DateAsXAxisLabelFormatter(this, dateFormat));
        renderer.setHumanRounding(false, true);
        renderer.setNumVerticalLabels(10);
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
        ArrayList<String> ids = database.ReadSingleColumn(primaryKeyName, tableName, WeightRecord.COLUMN_NAMES[0] /* this is "date"*/);
        ArrayList<String> petIds = database.ReadSingleColumn(Pet.PRIMARY_KEY, Pet.TABLE_NAME);

        graph.removeAllSeries();

        for (String petId : petIds)
        {
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

            for (String id : ids)
            {
                DataPoint dataPoint;
                long recordPetId;
                double x;
                double y;
                ArrayList<String> recordData = database.ReadSingleEntry(id, tableName);

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
                DisplayGraph(WeightRecord.PRIMARY_KEY, WeightRecord.TABLE_NAME);
                break;
            case R.id.mealGraph:
                DisplayGraph(MealRecord.PRIMARY_KEY, MealRecord.TABLE_NAME);
                break;
            case R.id.exerciseGraph:
                DisplayGraph(ExerciseRecord.PRIMARY_KEY, ExerciseRecord.TABLE_NAME);
                break;
            case R.id.energyGraph:
                DisplayGraph(EnergyRecord.PRIMARY_KEY, EnergyRecord.TABLE_NAME);
                break;
        }
    }
}