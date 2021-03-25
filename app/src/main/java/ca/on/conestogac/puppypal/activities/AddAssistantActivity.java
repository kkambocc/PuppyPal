package ca.on.conestogac.puppypal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;

public class AddAssistantActivity extends AppCompatActivity {
    EditText nameEditText;
    EditText phoneNumberEditText;
    EditText addressEditText;
    EditText titleEditText;
    EditText generalDescriptionEditText;
    Intent intent;
    DBHandler dbHandler;
    Button addAssistantButton;
    Button deleteAssistantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assistant);
        intent = getIntent();
        dbHandler = new DBHandler(this);
        nameEditText = findViewById(R.id.textAssistantName);
        phoneNumberEditText = findViewById(R.id.textPhoneNumber);
        addressEditText = findViewById(R.id.textAddress);
        titleEditText = findViewById(R.id.textTitle);
        generalDescriptionEditText = findViewById(R.id.textGeneralDescription);
        addAssistantButton = findViewById(R.id.addAssistantToDatabase);
        deleteAssistantButton = findViewById(R.id.deleteAssistantFromDatabase);
        addAssistantButton.setText(R.string.add_assistant);
        deleteAssistantButton.setVisibility(View.INVISIBLE);
        if (intent.getIntExtra("id",-1) != -1)
        {
            addAssistantButton.setText(R.string.update_assistant);
            deleteAssistantButton.setVisibility(View.VISIBLE);
            deleteAssistantButton.setEnabled(true);
            deleteAssistantButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAssistantActivity.this);
                    builder.setTitle("Delete Alert");
                    builder.setMessage("Would You like to delete assistant info");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.out.println("Delete method Called");
                            deleteAssistant(intent.getIntExtra("id", -1));
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
builder.show();

                }
            });
            System.out.println("Success: Clicked Id is "+intent.getIntExtra("id",-1));
            Cursor cursor = EditAssistantActivity.cursor;
            cursor.moveToFirst();
            if (cursor.getInt(cursor.getColumnIndex("assistant_id")) == intent.getIntExtra("id",-1))
            {
                System.out.println("Received name is: "+cursor.getString(cursor.getColumnIndex("name")));
                nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
                phoneNumberEditText.setText(cursor.getString(cursor.getColumnIndex("phone_number")));
                addressEditText.setText(cursor.getString(cursor.getColumnIndex("address")));
                titleEditText.setText(cursor.getString(cursor.getColumnIndex("title")));
                generalDescriptionEditText.setText(cursor.getString(cursor.getColumnIndex("general_description")));
            }
            while (cursor.moveToNext())
            {
                if (cursor.getInt(cursor.getColumnIndex("assistant_id")) == intent.getIntExtra("id",-1))
                {
                    System.out.println("Received name is: "+cursor.getString(cursor.getColumnIndex("name")));
                    nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
                    phoneNumberEditText.setText(cursor.getString(cursor.getColumnIndex("phone_number")));
                    addressEditText.setText(cursor.getString(cursor.getColumnIndex("address")));
                    titleEditText.setText(cursor.getString(cursor.getColumnIndex("title")));
                    generalDescriptionEditText.setText(cursor.getString(cursor.getColumnIndex("general_description")));
                }
            }

            //nameEditText.setText(EditAssistantActivity.assistantArrayMap.get(intent.getIntExtra("id",-1)));
        }
        addAssistantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent.getIntExtra("id",-1) != -1)
                {
                    System.out.println("Update Method Called");
                    Add_the_Assistant(true,intent.getIntExtra("id",-1));
                }
                else
                {
                    System.out.println("Add Method Called");
                    Add_the_Assistant(false,-1);
                }
            }
        });
    }
    public void Add_the_Assistant(boolean isUpdate,int updateID)
    {
        if (!nameValidation(nameEditText.getText().toString()))
        {
            return;
        }
        if (!phoneNumberValidation(phoneNumberEditText.getText().toString()))
        {
            return;
        }
        if (!addressValidation(addressEditText.getText().toString()))
        {
            return;
        }
        if (!titleValidation(titleEditText.getText().toString()))
        {
            return;
        }
        if (!generalDescriptionValidation(generalDescriptionEditText.getText().toString()))
        {
            return;
        }
        if (!isUpdate) {
            dbHandler.addAssistantToDB(nameEditText.getText().toString(), phoneNumberEditText.getText().toString(), addressEditText.getText().toString(), titleEditText.getText().toString(), generalDescriptionEditText.getText().toString(),isUpdate,updateID);
        }
        else {
            dbHandler.addAssistantToDB(nameEditText.getText().toString(), phoneNumberEditText.getText().toString(), addressEditText.getText().toString(), titleEditText.getText().toString(), generalDescriptionEditText.getText().toString(),isUpdate,updateID);
        }
        Intent intent = new Intent(this,EditAssistantActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Assistant has added to the Database", Toast.LENGTH_SHORT).show();
    }
    public void deleteAssistant(int deleteID)
    {
        dbHandler.deleteAssistant(deleteID);
        Toast.makeText(this, "Assistant has deleted from the database", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,EditAssistantActivity.class);
        startActivity(intent);
        finish();
    }
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_warning_outline)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }
    public void alertDialogBuilder(String title, String message)
    {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning_outline)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok",null)
                .show();
    }
    public boolean nameValidation(String name) {
        boolean nameBoolean= false;
        if (name.isEmpty())
        {
            alertDialogBuilder("Name field is empty !","Name can't be empty");
            return nameBoolean;
        }

        if (!name.matches("^([a-zA-Z]+[ ])*[a-zA-Z]+$"))
        {
            alertDialogBuilder("Wrong Pattern","Name should only contains alphabetic letters");
            return nameBoolean;
        }

        if (name.length() >20)
        {
            alertDialogBuilder("Text limit exceeds !","Name should be no more than 20 characters long");
            return nameBoolean;
        }

        nameBoolean = true;
        return nameBoolean;
    }
    public boolean phoneNumberValidation(String number) {
        boolean numberBoolean= false;
        if (number.isEmpty())
        {
            alertDialogBuilder("Phone number field is empty !","Phone Number can't be empty");
            return numberBoolean;
        }
        
        if (number.length() <10)
        {
            alertDialogBuilder("Number is too short !","Phone Number should be minimum of 10 characters long");
            return numberBoolean;
        }

        if (number.length() >10)
        {
            alertDialogBuilder("Number is too long !","Phone Number should not be more than 10 characters long");
            return numberBoolean;
        }

        numberBoolean = true;
        return numberBoolean;
    }
    public boolean addressValidation(String address) {
        boolean addressBoolean= false;
        if (address.isEmpty())
        {
            alertDialogBuilder("Address field is empty !","Adress can't be empty");
            return addressBoolean;
        }

        if (address.length() >50)
        {
            alertDialogBuilder("Text limit exceeds !","Address should be no more than 50 characters long");
            return addressBoolean;
        }

        addressBoolean = true;
        return addressBoolean;
    }
    public boolean titleValidation(String title) {
        boolean titleBoolean= false;
        if (title.isEmpty())
        {
            alertDialogBuilder("Title field is empty !","Title can't be empty");
            return titleBoolean;
        }

        if (!title.matches("^([a-zA-Z]+[ ])*[a-zA-Z]+$"))
        {
            alertDialogBuilder("Wrong Pattern","Title should only contains alphabetic letters");
            return titleBoolean;
        }

        if (title.length() >10)
        {
            alertDialogBuilder("Text limit exceeds !","Title should be no more than 10 characters long");
            return titleBoolean;
        }

        titleBoolean = true;
        return titleBoolean;
    }
    public boolean generalDescriptionValidation(String generalDescription) {
        boolean generalDescriptionBoolean= false;
        if (generalDescription.isEmpty())
        {
            alertDialogBuilder("General Description field is empty !","General Description can't be empty");
            return generalDescriptionBoolean;
        }

        if (!generalDescription.matches("^([a-zA-Z0-9,.]+[ ])*[a-zA-Z0-9,.]+$"))
        {
            alertDialogBuilder("Wrong Pattern","General Description should only contains alphabetic letters");
            return generalDescriptionBoolean;
        }

        if (generalDescription.length() >50)
        {
            alertDialogBuilder("Text limit exceeds !","General Description should be no more than 50 characters long");
            return generalDescriptionBoolean;
        }

        generalDescriptionBoolean = true;
        return generalDescriptionBoolean;
    }
}