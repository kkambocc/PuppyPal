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
import ca.on.conestogac.puppypal.tables.Assistant;

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
        assistantId  = getIntent().getIntExtra("id", -1);
        dbHandler = new DBHandler(this);
        nameEditText = findViewById(R.id.textAssistantName);
        phoneNumberEditText = findViewById(R.id.textPhoneNumber);
        addressEditText = findViewById(R.id.textAddress);
        titleEditText = findViewById(R.id.textTitle);
        generalDescriptionEditText = findViewById(R.id.textGeneralDescription);
        Button addAssistantButton = findViewById(R.id.addAssistantToDatabase);
        Button deleteAssistantButton = findViewById(R.id.deleteAssistantFromDatabase);

        validator = new Validator(this);
        validator.setValidationListener(this);

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

            ArrayList<String> assistant = dbHandler.ReadSingleEntry(Integer.toString(assistantId), "tbl_assistant");
            System.out.println("Received name is: " + assistant.get(1));
            nameEditText.setText(assistant.get(1));
            phoneNumberEditText.setText(assistant.get(2));
            addressEditText.setText(assistant.get(3));
            titleEditText.setText(assistant.get(4));
            generalDescriptionEditText.setText(assistant.get(5));
        }
        addAssistantButton.setOnClickListener(view -> {
            if (assistantId != -1)
            {
                System.out.println("Update Method Called");
                Add_the_Assistant();
            }
            else
            {
                System.out.println("Add Method Called");
                Add_the_Assistant();
            }
        });
    }

    public void Add_the_Assistant()
    {
        validator.validate();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Assistant has added to the Database", Toast.LENGTH_SHORT).show();
    }

    public void deleteAssistant(Integer deleteID)
    {
        dbHandler.DeleteFromTable(Assistant.TABLE_NAME,Assistant.PRIMARY_KEY,Long.parseLong(deleteID.toString()));

        Toast.makeText(this, "Assistant has deleted from the database", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

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