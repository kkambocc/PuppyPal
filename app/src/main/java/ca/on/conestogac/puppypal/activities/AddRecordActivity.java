package ca.on.conestogac.puppypal.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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
    private String petId;
    private String primaryKeyName;
    private Long recordId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);

        database = new DBHandler(this);
        form = findViewById(R.id.layoutRecordForm);
        time = android.text.format.DateFormat.getTimeFormat(getApplicationContext());
        date = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        petId = getIntent().getStringExtra(Pet.PRIMARY_KEY);

        recordId = getIntent().getLongExtra("recordId", -1);
        tableName = getIntent().getStringExtra("tableName");
        String pageTitle;

        if (tableName.equals(ExerciseRecord.TABLE_NAME))
        {
            pageTitle = getString(R.string.rb_exercise);
            primaryKeyName = ExerciseRecord.PRIMARY_KEY;
        }
        else if (tableName.equals(EnergyRecord.TABLE_NAME))
        {
            pageTitle = getString(R.string.energy_level);
            primaryKeyName = EnergyRecord.PRIMARY_KEY;
        }
        else if (tableName.equals(ExcrementRecord.TABLE_NAME))
        {
            pageTitle = getString(R.string.excrement);
            primaryKeyName = ExcrementRecord.PRIMARY_KEY;
        }
        else if (tableName.equals(MealRecord.TABLE_NAME))
        {
            pageTitle = getString(R.string.meal);
            primaryKeyName = MealRecord.PRIMARY_KEY;
        }
        else //if (tableName.equals(WeightRecord.TABLE_NAME))
        {
            pageTitle = getString(R.string.rb_weight);
            primaryKeyName = WeightRecord.PRIMARY_KEY;
        }

        if (recordId == -1)
        {
            pageTitle = getString(R.string.add) + pageTitle;
            GenerateForm(database.GetColumnNames(tableName),false);
        }
        else
        {
            Button button = findViewById(R.id.saveRecordToDatabase);
            button.setText(R.string.delete);
            button.setOnClickListener(v -> {
                database.DeleteFromTable(tableName,primaryKeyName,recordId);
                finish();
            });
            GenerateForm(database.GetColumnNames(tableName),true);
        }

        TextView title = findViewById(R.id.labelRecordType);
        title.setText(pageTitle);

    }

    public void SaveRecordButton(View v)
    {
        ArrayList<String> record = new ArrayList<>();
        //emptyId
        record.add("0");
        record.add(petId);
        View timeView = ((LinearLayout) form.getChildAt(0)).getChildAt(1);
        View dateView = ((LinearLayout) form.getChildAt(1)).getChildAt(1);

        try
        {
            long selectedDate = Objects.requireNonNull(date.parse(((Button) dateView).getText().toString())).getTime();
            long selectedTime = Objects.requireNonNull(time.parse(((Button) timeView).getText().toString())).getTime();
            record.add((selectedDate + selectedTime) + "");
        } catch (Exception ex)
        {
            //this can't ever happen
        }


        for (int i = 2; i < form.getChildCount(); i++)
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

    private void GenerateForm(ArrayList<String> columnNames, boolean review)
    {
        form.removeAllViews();

        for (String column : columnNames)
        {
            //ignore primary keys
            if (!column.equals(columnNames.get(0)) && !column.equals((columnNames.get(1))))
            {
                TextView label = new TextView(this);
                View view = new EditText(this);
                label.setText(column.toUpperCase());

                if (review)
                {
                    ((EditText) view).setText(database.ReadSingleValue(column, tableName, primaryKeyName, recordId.toString()));
                    view.setEnabled(false);
                }

                if (column.equals(WeightRecord.COLUMN_NAMES[0])) //date
                {
                    calendar = Calendar.getInstance();

                    if (review)
                    {
                        calendar.setTime(new Date(Long.parseLong(database.ReadSingleValue(column, tableName, primaryKeyName, recordId.toString()))));
                    }

                    Button viewTime = new Button(this);
                    TextView labelTime = new TextView(this);
                    labelTime.setText(getText(R.string.time).toString().toUpperCase());
                    if (!review)
                    {
                        viewTime.setOnClickListener(this::ChangeTime);
                    }
                    viewTime.setId(R.id.viewTime);
                    viewTime.setText(time.format(calendar.getTime()));
                    viewTime.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                    AddRow(labelTime, viewTime);

                    view = new Button(this);
                    if (!review)
                    {
                        view.setOnClickListener(this::ChangeDate);
                    }

                    view.setId(R.id.viewDate);
                    ((Button) view).setText(date.format(calendar.getTime()));
                    ((Button) view).setTextAppearance(R.style.TextAppearance_AppCompat_Large);
                }
                else if (column.equals(ExcrementRecord.COLUMN_NAMES[1])) //abnormalities
                {
                    ((EditText) view).setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }
                else if (column.equals(EnergyRecord.COLUMN_NAMES[1])) //energy_level
                {
                    label.setText(R.string.energy_level);
                    view = new SeekBar(this, null, 0, R.style.Widget_AppCompat_SeekBar_Discrete);
                    ((SeekBar) view).setMax(10);
                }
                else if (column.equals(ExerciseRecord.COLUMN_NAMES[2]))
                {
                    label.setText(getText(R.string.duration).toString().toUpperCase());
                }

                AddRow(label, view);
            }
        }
    }

    private void AddRow(TextView label, View view)
    {
        ViewGroup.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        LinearLayout row = new LinearLayout(this);

        /*
         * Design goes here for now
         */
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setLayoutParams(params);
        view.setLayoutParams(params);
        view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        /*
         *
         */
        row.addView(label);
        row.addView(view);
        form.addView(row);

    }

    private void ChangeDate(View view)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view1, year, month, dayOfMonth) -> {
            Button dateButton = findViewById(R.id.viewDate);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateButton.setText(date.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void ChangeTime(View view)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
            Button timeButton = findViewById(R.id.viewTime);
            calendar.set(Calendar.HOUR, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            timeButton.setText(time.format(calendar.getTime()));
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}