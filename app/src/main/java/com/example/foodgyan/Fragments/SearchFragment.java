package com.example.foodgyan.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodgyan.Activities.ModelClass.FoodItems;
import com.example.foodgyan.Adapters.FoodItemAdapter;
import com.example.foodgyan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private SearchView searchView;
    private View mView;
    private DatabaseReference Ref;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FoodItemAdapter adapter;

    private RecyclerView foodItems;

    private String currentUserID;

    private ArrayList<FoodItems> foodItemsArrayList = new ArrayList<>();


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_search, container, false);

        InitializeFields();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        foodItems.setLayoutManager(layoutManager);
        Ref.child("FoodData").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(dataSnapshot1.hasChild("Name") && dataSnapshot1.hasChild("ImageLink"))
                    {
                        String name = dataSnapshot1.child("Name").getValue().toString();
                        String image = dataSnapshot1.child("ImageLink").getValue().toString();

                        foodItemsArrayList.add(new FoodItems(image, name));


                    }


                    else
                    {
                        String name = dataSnapshot1.child("Name").getValue().toString();

                        foodItemsArrayList.add(new FoodItems("data:image/jpeg;base64,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", name));

                    }
                }


                adapter = new FoodItemAdapter(getContext(), foodItemsArrayList);
                foodItems.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter.getFilter() != null)
                {
                    adapter.getFilter().filter(newText);

                }

                return false;
            }
        });




        return mView;
    }

    private void InitializeFields() {
        searchView = (androidx.appcompat.widget.SearchView)mView.findViewById(R.id.search_food_items);
        Ref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        currentUserID = currentUser.getUid();

        foodItems = (RecyclerView)mView.findViewById(R.id.food_items_recycler);


    }

}
