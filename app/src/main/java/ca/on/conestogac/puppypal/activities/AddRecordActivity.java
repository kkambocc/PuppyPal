package ca.on.conestogac.puppypal.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.tables.EnergyRecord;
import ca.on.conestogac.puppypal.tables.ExcrementRecord;
import ca.on.conestogac.puppypal.tables.ExerciseRecord;
import ca.on.conestogac.puppypal.tables.MealRecord;
import ca.on.conestogac.puppypal.tables.Pet;
import ca.on.conestogac.puppypal.tables.WeightRecord;

public class AddRecordActivity extends AppCompatActivity
{

    private DBHandler database;
    private LinearLayout form;
    private Calendar calendar;
    private DateFormat time;
    private DateFormat date;
    private String tableName;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);
        database = new DBHandler(this);
        String[] recordTypes = new String[]{"Weight", "Meal", "Exercise", "Energy Level", "Excrement"};
        Spinner recordType = findViewById(R.id.spinRecordType);
        form = findViewById(R.id.layoutRecordForm);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, recordTypes);
        recordType.setAdapter(adapter);
        time = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        date = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        recordType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                CreateForm(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                //do nothing
            }
        });

    }

    public void SaveRecordButton(View v)
    {
        ArrayList<String> record = new ArrayList<>();
        //emptyId
        record.add("0");
        //
        Calendar calendar = Calendar.getInstance();
        View petIdView = ((LinearLayout) form.getChildAt(0)).getChildAt(1);
        View timeView = ((LinearLayout) form.getChildAt(1)).getChildAt(1);
        View dateView = ((LinearLayout) form.getChildAt(2)).getChildAt(1);
        ArrayList ids = database.ReadSingleColumn("pet_id", Pet.TABLE_NAME);


        record.add(ids.get(((Spinner) petIdView).getSelectedItemPosition()).toString());
        try
        {
            long selectedDate = date.parse(((Button) dateView).getText().toString()).getTime();
            long selectedTime = time.parse(((Button) timeView).getText().toString()).getTime();
            calendar.setTime(new Date(selectedDate + selectedTime));
        } catch (ParseException ex) {
            //do nothing for now
        }
        calendar.set(Calendar.ZONE_OFFSET,0);
        calendar.set(Calendar.DST_OFFSET,0);
        record.add(calendar.getTimeInMillis() + "");


        for (int i = 3; i < form.getChildCount(); i++)
        {
            View view = ((LinearLayout) form.getChildAt(i)).getChildAt(1);
            if (view instanceof SeekBar)
            {
                record.add("" + ((SeekBar) view).getProgress());
            }
            else
            {
                record.add(((EditText) view).getText().toString());
            }
        }
        database.AddToTable(tableName, record);
        finish();
    }

    private void CreateForm(String selectedType)
    {
        ArrayList<String> columnNames = new ArrayList<>();
        switch (selectedType.toLowerCase())
        {
            case "meal":
                tableName = MealRecord.TABLE_NAME;
                break;
            case "exercise":
                tableName = ExerciseRecord.TABLE_NAME;
                break;
            case "energy level":
                tableName = EnergyRecord.TABLE_NAME;
                break;
            case "weight":
                tableName = WeightRecord.TABLE_NAME;
                break;
            case "excrement":
                tableName = ExcrementRecord.TABLE_NAME;
                break;
        }
        GenerateForm(database.GetColumnNames(tableName));
    }

    private void GenerateForm(ArrayList<String> columnNames)
    {
        form.removeAllViews();

        for (String column : columnNames)
        {
            //ignore primary keys
            if (!column.equals(columnNames.get(0)))
            {
                ViewGroup.LayoutParams params = new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                );
                LinearLayout row = new LinearLayout(this);
                TextView label = new TextView(this);
                View view = new EditText(this);
                label.setText(column.toUpperCase());

                switch (column)
                {
                    case "pet_id":
                        label.setText("PET");
                        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, database.ReadSingleColumn("name", Pet.TABLE_NAME));
                        view = new Spinner(this);
                        ((Spinner) view).setAdapter(adapter);
                        break;
                    case "date":
                        calendar = Calendar.getInstance();

                        Button viewTime = new Button(this);
                        TextView labelTime = new TextView(this);
                        labelTime.setText("TIME");
                        viewTime.setOnClickListener(this::ChangeTime);
                        viewTime.setId(R.id.viewTime);
                        viewTime.setText(time.format(calendar.getTime()));
                        viewTime.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                        AddRow(labelTime,viewTime);

                        view = new Button(this);
                        view.setOnClickListener(this::ChangeDate);

                        view.setId(R.id.viewDate);
                        ((Button) view).setText(date.format(calendar.getTime()));
                        ((Button) view).setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                        break;
                    case "abnormalities":
                        ((EditText) view).setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                        break;
                    case "energy_level":
                        label.setText("ENERGY LEVEL");
                        view = new SeekBar(this,null,0,R.style.Widget_AppCompat_SeekBar_Discrete);
                        ((SeekBar) view).setMax(10);
                        break;
                }
                AddRow(label,view);
            }
        }
    }

    private void AddRow(TextView label,View view)
    {
        ViewGroup.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        LinearLayout row = new LinearLayout(this);

        /**
         * Design goes here for now
         */
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(params);
        view.setLayoutParams(params);
        view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        /**
         *
         */
        row.addView(label);
        row.addView(view);
        form.addView(row);

    }

    private void ChangeDate(View view)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
            {
                Button dateButton = findViewById(R.id.viewDate);
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                dateButton.setText(date.format(calendar.getTime()));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void ChangeTime(View view)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                Button timeButton = findViewById(R.id.viewTime);
                calendar.set(Calendar.HOUR,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                timeButton.setText(time.format(calendar.getTime()));
            }
        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
        timePickerDialog.show();
    }
}
