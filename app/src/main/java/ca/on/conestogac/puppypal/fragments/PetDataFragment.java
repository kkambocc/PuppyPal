package ca.on.conestogac.puppypal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.AddRecordActivity;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

public class PetDataFragment extends Fragment
{
    private GraphView graph;
    private DBHandler database;
    private String graphTableName;
    private String petId;

    public PetDataFragment()
    {
        // Required empty public constructor
    }

    public PetDataFragment(String tableName, String petId)
    {
        this.graphTableName = tableName;
        this.petId = petId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        View view = inflater.inflate(R.layout.fragment_pet_data, container, false);

        FloatingActionButton button = view.findViewById(R.id.goToAddRecord);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(view.getContext(), AddRecordActivity.class);
            intent.putExtra(Pet.PRIMARY_KEY, petId);
            intent.putExtra("tableName", graphTableName);
            startActivity(intent);
        });

        database = new DBHandler(container.getContext());
        graph = view.findViewById(R.id.graph);

        Viewport viewport = graph.getViewport();
        GridLabelRenderer renderer = graph.getGridLabelRenderer();

        renderer.setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        renderer.setLabelFormatter(new DateAsXAxisLabelFormatter(view.getContext(), dateFormat)
        {
            @Override
            public String formatLabel(double value, boolean isValueX)
            {
                if (isValueX)
                {
                    //return date
                    return super.formatLabel(value, isValueX);
                }
                else
                {
                    //remove fraction digits
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(0);
                    Float a = Float.parseFloat(super.formatLabel(value, isValueX));
                    return nf.format(a);
                }
            }
        });
        renderer.setHumanRounding(false, true);
        renderer.setNumVerticalLabels(6);
        renderer.setHorizontalAxisTitle("Date");

        switch (this.graphTableName)
        {
            case MealRecord.TABLE_NAME:
                DisplayGraph(MealRecord.PRIMARY_KEY, MealRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle("Amount");
                break;
            case ExerciseRecord.TABLE_NAME:
                DisplayGraph(ExerciseRecord.PRIMARY_KEY, ExerciseRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle("Duration");
                break;
            case EnergyRecord.TABLE_NAME:
                DisplayGraph(EnergyRecord.PRIMARY_KEY, EnergyRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle("Energy Level");
                break;
            case WeightRecord.TABLE_NAME:
            default:
                DisplayGraph(WeightRecord.PRIMARY_KEY, WeightRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle("Weight (lbs)");
                break;
        }

        viewport.setScrollable(true);
        viewport.setScrollableY(true);
        viewport.setScrollableY(true);
        viewport.setScalable(true);
        viewport.scrollToEnd();
        return view;
    }

    private void DisplayGraph(String primaryKeyName, String tableName)
    {
        ArrayList<String> ids = database.ReadSingleColumn(primaryKeyName, tableName, WeightRecord.COLUMN_NAMES[0] /* this is "date"*/);

        graph.removeAllSeries();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        for (String id : ids)
        {
            DataPoint dataPoint;
            double x;
            double y;
            ArrayList<String> recordData = database.ReadSingleEntry(id, tableName);

            if (recordData.get(1).equals(petId))
            {
                //create datapoint to be added to series
                switch (tableName)
                {
                    case MealRecord.TABLE_NAME:
                        MealRecord meal = new MealRecord(recordData);
                        x = meal.GetDate().getTime();
                        y = meal.GetAmount();
                        break;
                    case ExerciseRecord.TABLE_NAME:
                        ExerciseRecord exercise = new ExerciseRecord(recordData);
                        x = exercise.GetDate().getTime();
                        y = exercise.GetDuration().getTime();
                        break;
                    case EnergyRecord.TABLE_NAME:
                        EnergyRecord energy = new EnergyRecord(recordData);
                        x = energy.GetDate().getTime();
                        y = energy.GetEnergyLevel();
                        break;
                    case WeightRecord.TABLE_NAME:
                    default:
                        WeightRecord weight = new WeightRecord(recordData);
                        x = weight.GetDate().getTime();
                        y = weight.GetWeight();
                        break;
                }
                dataPoint = new DataPoint(x, y);
                series.appendData(dataPoint, true, ids.size());
            }
        }
        series.setDrawDataPoints(true);
        graph.addSeries(series);
    }
}