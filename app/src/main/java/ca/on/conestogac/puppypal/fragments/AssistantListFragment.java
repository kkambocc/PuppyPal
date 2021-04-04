package ca.on.conestogac.puppypal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import ca.on.conestogac.puppypal.DBHandler;
import ca.on.conestogac.puppypal.R;
import ca.on.conestogac.puppypal.activities.AddAssistantActivity;

public class AssistantListFragment extends Fragment
{
    LinearLayout assistantList;
    DBHandler dbHandler;
    View view;

    public AssistantListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_edit_assistant, container, false);
        updateTheList();
        return view;
    }

    public void updateTheList()
    {
        assistantList = view.findViewById(R.id.assistantList);
        dbHandler = new DBHandler(view.getContext());

        ArrayList<String> assistant = new ArrayList<>();
        ArrayList<String> ids = dbHandler.ReadSingleColumn("assistant_id","tbl_assistant");

        for ( String id : ids)
        {
            assistant = dbHandler.ReadSingleEntry(id,"tbl_assistant");
            viewCreator(Integer.parseInt(assistant.get(0)),assistant.get(1));
        }
    }

    public void viewCreator(int viewID, String text)
    {
        Button listButton = new Button(view.getContext());
        listButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listButton.setId(viewID);
        listButton.setText(text);
        listButton.setOnClickListener(view -> {
            System.out.println("ViewID: " + view.getId());
            Intent intent = new Intent(view.getContext(), AddAssistantActivity.class);
            intent.putExtra("id", view.getId());
            startActivity(intent);
        });
        assistantList.addView(listButton);
    }
}