package ca.on.conestogac.puppypal.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.ArrayList;
import java.util.List;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.MainActivity;
import ca.on.conestogac.puppypal.R;

public class AddAssistantActivity extends AppCompatActivity implements Validator.ValidationListener
{

    @NotEmpty
    @Pattern(regex = "^([a-zA-Z]+[ ])*[a-zA-Z]+$")
    @Length(min = 3, max = 20)
    private EditText nameEditText;

    @NotEmpty
    @Length(min = 10, max = 10)
    private EditText phoneNumberEditText;

    @NotEmpty
    @Length(min = 3, max = 50)
    private EditText addressEditText;

    @NotEmpty
    @Length(min = 3, max = 30)
    @Pattern(regex = "^([a-zA-Z]+[ ])*[a-zA-Z]+$")
    private EditText titleEditText;

    @NotEmpty
    @Length(min = 3, max = 50)
    @Pattern(regex = "^([a-zA-Z0-9,.]+[ ])*[a-zA-Z0-9,.]+$")
    private EditText generalDescriptionEditText;


    //Intent intent;
    private DBHandler dbHandler;

    //added this
    private Integer assistantId;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTheme(R.style.Theme_PuppyPal);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assistant);
        //added this
        assistantId  = getIntent().getIntExtra("id", -1);

        dbHandler = new DBHandler(this);
        nameEditText = findViewById(R.id.textAssistantName);
        phoneNumberEditText = findViewById(R.id.textPhoneNumber);
        addressEditText = findViewById(R.id.textAddress);
        titleEditText = findViewById(R.id.textTitle);
        generalDescriptionEditText = findViewById(R.id.textGeneralDescription);
        Button addAssistantButton = findViewById(R.id.addAssistantToDatabase);
        //this is unnecessary, text is already set to be this string
        //addAssistantButton.setText(R.string.add_an_assistant);
        Button deleteAssistantButton = findViewById(R.id.deleteAssistantFromDatabase);
        //also unnecessary
        //deleteAssistantButton.setVisibility(View.INVISIBLE);


        //added this
        validator = new Validator(this);
        validator.setValidationListener(this);


        //changed intent.getIntExtra("id", -1) to assistantId
        if (assistantId != -1)
        {
            addAssistantButton.setText(R.string.update_assistant);
            deleteAssistantButton.setVisibility(View.VISIBLE);
            deleteAssistantButton.setEnabled(true);
            deleteAssistantButton.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddAssistantActivity.this);
                builder.setTitle("Delete Alert");
                builder.setMessage("Would you like to delete assistant info?");
                builder.setPositiveButton("YES", (dialog, which) -> {
                    System.out.println("Delete method Called");
                    deleteAssistant(assistantId);
                });
                builder.setNegativeButton("NO", (dialog, which) -> {

                });
                builder.show();

            });
            System.out.println("Success: Clicked Id is " + assistantId);


            //added this
            ArrayList<String> assistant = dbHandler.ReadSingleEntry(Integer.toString(assistantId), "tbl_assistant");
            System.out.println("Received name is: " + assistant.get(1));
            nameEditText.setText(assistant.get(1));
            phoneNumberEditText.setText(assistant.get(2));
            addressEditText.setText(assistant.get(3));
            titleEditText.setText(assistant.get(4));
            generalDescriptionEditText.setText(assistant.get(5));

            /**
             * everything seen below can be done with the block of code above.
             *
             * We should never be using a cursor to access the database outside of the DBHandler class
             */
            /*
            Cursor cursor = EditAssistantActivity.cursor;
            cursor.moveToFirst();
            if (cursor.getInt(cursor.getColumnIndex("assistant_id")) == intent.getIntExtra("id", -1))
            {
                System.out.println("Received name is: " + cursor.getString(cursor.getColumnIndex("name")));
                nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
                phoneNumberEditText.setText(cursor.getString(cursor.getColumnIndex("phone_number")));
                addressEditText.setText(cursor.getString(cursor.getColumnIndex("address")));
                titleEditText.setText(cursor.getString(cursor.getColumnIndex("title")));
                generalDescriptionEditText.setText(cursor.getString(cursor.getColumnIndex("general_description")));
            }
            while (cursor.moveToNext())
            {
                if (cursor.getInt(cursor.getColumnIndex("assistant_id")) == intent.getIntExtra("id", -1))
                {
                    System.out.println("Received name is: " + cursor.getString(cursor.getColumnIndex("name")));
                    nameEditText.setText(cursor.getString(cursor.getColumnIndex("name")));
                    phoneNumberEditText.setText(cursor.getString(cursor.getColumnIndex("phone_number")));
                    addressEditText.setText(cursor.getString(cursor.getColumnIndex("address")));
                    titleEditText.setText(cursor.getString(cursor.getColumnIndex("title")));
                    generalDescriptionEditText.setText(cursor.getString(cursor.getColumnIndex("general_description")));
                }
            }

             */

            //nameEditText.setText(EditAssistantActivity.assistantArrayMap.get(intent.getIntExtra("id",-1)));
        }
        addAssistantButton.setOnClickListener(view -> {
            if (assistantId != -1)
            {
                System.out.println("Update Method Called");
                Add_the_Assistant(true, assistantId);
            }
            else
            {
                System.out.println("Add Method Called");
                Add_the_Assistant(false, -1);
            }
        });
    }

    public void Add_the_Assistant(boolean isUpdate, int updateID)
    {
        /*
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
         */

        //easier validation
        validator.validate();


        /*no need to check isUpdate, just pass it
        if (!isUpdate)
        {
         */
            //dbHandler.addAssistantToDB(nameEditText.getText().toString(), phoneNumberEditText.getText().toString(), addressEditText.getText().toString(), titleEditText.getText().toString(), generalDescriptionEditText.getText().toString(), isUpdate, updateID);
        /*
        }
        else
        {
            dbHandler.addAssistantToDB(nameEditText.getText().toString(), phoneNumberEditText.getText().toString(), addressEditText.getText().toString(), titleEditText.getText().toString(), generalDescriptionEditText.getText().toString(), isUpdate, updateID);
        }
         */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Assistant has added to the Database", Toast.LENGTH_SHORT).show();
    }

    public void deleteAssistant(int deleteID)
    {
        dbHandler.deleteAssistant(deleteID);
        Toast.makeText(this, "Assistant has deleted from the database", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    This class is never used, is it necessary?
     */
    private AlertDialog AskOption()
    {
        return new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Do you want to Delete")
                .setIcon(R.drawable.ic_warning_outline)

                .setPositiveButton("Delete", (dialog, whichButton) -> {
                    //your deleting code
                    dialog.dismiss();
                })
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .create();
    }

    /*
    Replaced with an easier validator


    public void alertDialogBuilder(String title, String message)
    {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning_outline)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", null)
                .show();
    }

    public boolean nameValidation(String name)
    {
        boolean nameBoolean = false;
        if (name.isEmpty())
        {
            alertDialogBuilder("Name field is empty !", "Name can't be empty");
            return nameBoolean;
        }

        if (!name.matches("^([a-zA-Z]+[ ])*[a-zA-Z]+$"))
        {
            alertDialogBuilder("Wrong Pattern", "Name should only contains alphabetic letters");
            return nameBoolean;
        }

        if (name.length() > 20)
        {
            alertDialogBuilder("Text limit exceeds !", "Name should be no more than 20 characters long");
            return nameBoolean;
        }

        nameBoolean = true;
        return nameBoolean;
    }

    public boolean phoneNumberValidation(String number)
    {
        boolean numberBoolean = false;
        if (number.isEmpty())
        {
            alertDialogBuilder("Phone number field is empty !", "Phone Number can't be empty");
            return numberBoolean;
        }

        if (number.length() < 10)
        {
            alertDialogBuilder("Number is too short !", "Phone Number should be minimum of 10 characters long");
            return numberBoolean;
        }

        if (number.length() > 10)
        {
            alertDialogBuilder("Number is too long !", "Phone Number should not be more than 10 characters long");
            return numberBoolean;
        }

        numberBoolean = true;
        return numberBoolean;
    }

    public boolean addressValidation(String address)
    {
        boolean addressBoolean = false;
        if (address.isEmpty())
        {
            alertDialogBuilder("Address field is empty !", "Adress can't be empty");
            return addressBoolean;
        }

        if (address.length() > 50)
        {
            alertDialogBuilder("Text limit exceeds !", "Address should be no more than 50 characters long");
            return addressBoolean;
        }

        addressBoolean = true;
        return addressBoolean;
    }

    public boolean titleValidation(String title)
    {
        boolean titleBoolean = false;
        if (title.isEmpty())
        {
            alertDialogBuilder("Title field is empty !", "Title can't be empty");
            return titleBoolean;
        }

        if (!title.matches("^([a-zA-Z]+[ ])*[a-zA-Z]+$"))
        {
            alertDialogBuilder("Wrong Pattern", "Title should only contains alphabetic letters");
            return titleBoolean;
        }

        if (title.length() > 10)
        {
            alertDialogBuilder("Text limit exceeds !", "Title should be no more than 10 characters long");
            return titleBoolean;
        }

        titleBoolean = true;
        return titleBoolean;
    }

    public boolean generalDescriptionValidation(String generalDescription)
    {
        boolean generalDescriptionBoolean = false;
        if (generalDescription.isEmpty())
        {
            alertDialogBuilder("General Description field is empty !", "General Description can't be empty");
            return generalDescriptionBoolean;
        }

        if (!generalDescription.matches("^([a-zA-Z0-9,.]+[ ])*[a-zA-Z0-9,.]+$"))
        {
            alertDialogBuilder("Wrong Pattern", "General Description should only contains alphabetic letters");
            return generalDescriptionBoolean;
        }

        if (generalDescription.length() > 50)
        {
            alertDialogBuilder("Text limit exceeds !", "General Description should be no more than 50 characters long");
            return generalDescriptionBoolean;
        }

        generalDescriptionBoolean = true;
        return generalDescriptionBoolean;
    }

     */

    @Override
    public void onValidationSucceeded()
    {
        ArrayList<String> assistant = new ArrayList<>();
        assistant.add(assistantId.toString());
        assistant.add(nameEditText.getText().toString());
        assistant.add(phoneNumberEditText.getText().toString());
        assistant.add(addressEditText.getText().toString());
        assistant.add(titleEditText.getText().toString());
        assistant.add(generalDescriptionEditText.getText().toString());

        if (assistantId != -1)
        {
            dbHandler.UpdateTable("tbl_assistant", assistant, "assistant_id",assistantId.toString());
        }
        else
        {
            dbHandler.AddToTable("tbl_assistant", assistant);
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {

        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}