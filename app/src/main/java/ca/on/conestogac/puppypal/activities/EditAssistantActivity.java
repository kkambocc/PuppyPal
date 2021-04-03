package ca.on.conestogac.puppypal.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;

public class EditAssistantActivity extends AppCompatActivity
{
    LinearLayout assistantList;
    public static ArrayMap<Integer, String> assistantArrayMap;
    DBHandler dbHandler;
    public static Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assistant);
        updateTheList();
    }

    public void updateTheList()
    {
        assistantList = findViewById(R.id.assistantList);
        dbHandler = new DBHandler(this);
        cursor = dbHandler.getAssistantData();
        if (cursor != null && cursor.getColumnCount() > 0 && cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            viewCreator(cursor.getInt(cursor.getColumnIndex("assistant_id")), cursor.getString(cursor.getColumnIndex("name")));
            while (cursor.moveToNext())
            {
                viewCreator(cursor.getInt(cursor.getColumnIndex("assistant_id")), cursor.getString(cursor.getColumnIndex("name")));
            }
        }
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        //updateTheList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // updateTheList();
    }

    public void viewCreator(int viewID, String text)
    {
        Button listButton = new Button(this);
        listButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listButton.setId(viewID);
        listButton.setText(text);
        listButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                System.out.println("ViewID: " + view.getId());
                Intent intent = new Intent(EditAssistantActivity.this, AddAssistantActivity.class);
                intent.putExtra("id", view.getId());
                startActivity(intent);
                finish();
            }
        });
        assistantList.addView(listButton);
    }
}