package ca.on.conestogac.puppypal.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
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
import java.util.Date;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.AddRecordActivity;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExcrementRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

public class PetDataFragment extends Fragment
{
    private GraphView graph;
    private LinearLayout recordLog;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
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
        recordLog = view.findViewById(R.id.recordLog);
        ArrayList<String> ids;
        Viewport viewport = graph.getViewport();
        GridLabelRenderer renderer = graph.getGridLabelRenderer();

        //graphing
        if (this.graphTableName.equals(ExcrementRecord.TABLE_NAME))
        {
            graph.setVisibility(View.GONE);
            ids = database.ReadSingleColumn(ExcrementRecord.PRIMARY_KEY, ExcrementRecord.TABLE_NAME, ExcrementRecord.COLUMN_NAMES[0] + " DESC");
        }
        else
        {

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
            renderer.setHorizontalAxisTitle(getString(R.string.date));

            if (this.graphTableName.equals(MealRecord.TABLE_NAME))
            {
                DisplayGraph(MealRecord.PRIMARY_KEY, MealRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle(getString(R.string.amount));
                ids = database.ReadSingleColumn(MealRecord.PRIMARY_KEY, MealRecord.TABLE_NAME, MealRecord.COLUMN_NAMES[0] + " DESC");
            }
            else if (this.graphTableName.equals(ExerciseRecord.TABLE_NAME))
            {
                DisplayGraph(ExerciseRecord.PRIMARY_KEY, ExerciseRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle(getString(R.string.duration));
                ids = database.ReadSingleColumn(ExerciseRecord.PRIMARY_KEY, ExerciseRecord.TABLE_NAME, ExerciseRecord.COLUMN_NAMES[0] + " DESC");
            }
            else if (this.graphTableName.equals(EnergyRecord.TABLE_NAME))
            {
                DisplayGraph(EnergyRecord.PRIMARY_KEY, EnergyRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle(getString(R.string.energy_level));
                ids = database.ReadSingleColumn(EnergyRecord.PRIMARY_KEY, EnergyRecord.TABLE_NAME, EnergyRecord.COLUMN_NAMES[0] + " DESC");
            }
            else //if (this.graphTableName.equals(WeightRecord.TABLE_NAME))
            {
                DisplayGraph(WeightRecord.PRIMARY_KEY, WeightRecord.TABLE_NAME);
                renderer.setVerticalAxisTitle(getString(R.string.weight));
                ids = database.ReadSingleColumn(WeightRecord.PRIMARY_KEY, WeightRecord.TABLE_NAME, WeightRecord.COLUMN_NAMES[0] + " DESC");
            }

            viewport.setScrollable(true);
            viewport.setScrollableY(true);
            viewport.setScrollableY(true);
            viewport.setScalable(true);
            viewport.scrollToEnd();
        }



        //record log
        ConstraintLayout row = (ConstraintLayout) inflater.inflate(R.layout.record_layout,null);
        TextView date = (TextView) row.getChildAt(0);
        TextView content = (TextView) row.getChildAt(2);
        date.setText(R.string.date);
        date.setTypeface(null, Typeface.BOLD);

        if (this.graphTableName.equals(ExcrementRecord.TABLE_NAME))
        {
            content.setText(R.string.abnormalities);
        }
        else
        {
            content.setText(renderer.getVerticalAxisTitle());
        }
        content.setTypeface(null,Typeface.BOLD);
        recordLog.addView(row);
        dateFormat = new SimpleDateFormat("MMM d, H:mm");

        for (String id : ids)
        {
            ArrayList<String> record = database.ReadSingleEntry(id,graphTableName);
            if (record.get(1).equals(petId))
            {
                row = (ConstraintLayout) inflater.inflate(R.layout.record_layout, null);
                date = (TextView) row.getChildAt(0);
                content = (TextView) row.getChildAt(2);

                date.setText(dateFormat.format(new Date(Long.parseLong(record.get(2)))));

                if (graphTableName.equals(ExerciseRecord.TABLE_NAME))
                {
                    TextView type = (TextView) row.getChildAt(1);
                    type.setText(record.get(3));
                    content.setText(record.get(4));
                }
                else
                {
                    content.setText(record.get(3));
                }

                row.setClickable(true);
                row.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(view.getContext(),AddRecordActivity.class);
                        intent.putExtra("recordId",Long.parseLong(id));
                        intent.putExtra("tableName",graphTableName);
                        startActivity(intent);
                    }
                });
                recordLog.addView(row);
            }
        }

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

                if (tableName.equals(MealRecord.TABLE_NAME))
                {
                    MealRecord meal = new MealRecord(recordData);
                    x = meal.GetDate().getTime();
                    y = meal.GetAmount();
                }
                else if (tableName.equals(ExerciseRecord.TABLE_NAME))
                {
                    ExerciseRecord exercise = new ExerciseRecord(recordData);
                    x = exercise.GetDate().getTime();
                    y = exercise.GetDuration().getTime();
                }
                else if (tableName.equals(EnergyRecord.TABLE_NAME))
                {
                    EnergyRecord energy = new EnergyRecord(recordData);
                    x = energy.GetDate().getTime();
                    y = energy.GetEnergyLevel();
                }
                else //if (tableName.equals(WeightRecord.TABLE_NAME))
                {
                    WeightRecord weight = new WeightRecord(recordData);
                    x = weight.GetDate().getTime();
                    y = weight.GetWeight();
                }
                dataPoint = new DataPoint(x, y);
                series.appendData(dataPoint, true, ids.size());
            }
        }
        series.setDrawDataPoints(true);
        graph.addSeries(series);
    }
}