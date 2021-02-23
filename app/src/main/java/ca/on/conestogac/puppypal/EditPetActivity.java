package ca.on.conestogac.puppypal;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class EditPetActivity extends AppCompatActivity
{

    DBHandler database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pet);
        database = new DBHandler(this);

        Long[] ids = database.GetPetIdList();
        LinearLayout list = (LinearLayout) findViewById(R.id.petList);

        for (Long id : ids)
        {
            Pet pet = database.ReadPetFromTable((long) id);
            Button b = new Button(this);
            b.setText(pet.getName());
            //b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            //b.setOnClickListener();
            list.addView(b);
        }
    }

}