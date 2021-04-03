package ca.on.conestogac.puppypal.fragments;

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
import ca.on.conestogac.puppypal.tables.Pet;

public class PetListFragment extends Fragment {
    DBHandler database;
    Pet pet;
    View view;

    public PetListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pet_list, container, false);
        database = new DBHandler(container.getContext());

        ArrayList<String> ids = (database.ReadSingleColumn(Pet.PRIMARY_KEY, Pet.TABLE_NAME));
        LinearLayout list = view.findViewById(R.id.petList);

        for (String id : ids) {
            pet = new Pet(database.ReadSingleEntry(id, Pet.TABLE_NAME));
            Button b = new Button(getContext());
            b.setText(pet.getName());
            b.setOnClickListener(v -> {
                pet = new Pet(database.ReadSingleEntry(id, Pet.TABLE_NAME));
                //send to pet homepage with Pet object
            });
            list.addView(b);
        }

        return view;
    }
}