package com.example.foodgyan.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodgyan.Activities.FoodDetailsActivity;
import com.example.foodgyan.Activities.ModelClass.FoodItems;
import com.example.foodgyan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> implements Filterable {


        private View myView;
        private Context mContext;
        private ArrayList<FoodItems> AdsArrayList;
        private ArrayList<FoodItems> AdsArrayListFull;
        private DatabaseReference Ref;
        String productTitle;
        private FirebaseAuth mAuth;
        private String currentUserID, key;


        public FoodItemAdapter(Context mContext, ArrayList<FoodItems> AdsArrayList) {
            this.mContext = mContext;
            this.AdsArrayList = AdsArrayList;
            AdsArrayListFull = new ArrayList<>(AdsArrayList);
            Ref = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
            currentUserID = mAuth.getCurrentUser().getUid();
        }

        @NonNull
        @Override
        public FoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_layout, parent, false);
            return new FoodItemAdapter.ViewHolder(myView);
        }

    @Override
        public void onBindViewHolder(@NonNull final FoodItemAdapter.ViewHolder holder, int position) {

            final FoodItems ads = AdsArrayList.get(position);
            holder.foodName.setText(ads.getFoodName());

            Glide.with(mContext)
                    .load(ads.getFoodImage())
                    .into(holder.foodImage);


            holder.foodDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, FoodDetailsActivity.class);
                    intent.putExtra("foodName", ads.getFoodName());
                    intent.putExtra("foodImage", ads.getFoodImage());
                    mContext.startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return AdsArrayList.size();
        }

        @Override
        public Filter getFilter() {
            return adsFilter;
        }

        private Filter adsFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<FoodItems> filteredList = new ArrayList<>();

                if(constraint== null || constraint.length() ==0)
                {
                    filteredList.addAll(AdsArrayListFull);
                }
                else
                {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for(FoodItems ads : AdsArrayListFull)
                    {
                        if(ads.getFoodName().toLowerCase().contains(filterPattern))
                        {
                            filteredList.add(ads);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                AdsArrayList.clear();
                AdsArrayList.addAll((ArrayList)results.values);
                notifyDataSetChanged();
            }
        };
        public class ViewHolder extends RecyclerView.ViewHolder {

            private CircleImageView foodImage;
            private TextView foodName;
            private CardView foodDetails;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                foodDetails = (CardView)myView.findViewById(R.id.food_details);
                foodImage = (CircleImageView) myView.findViewById(R.id.ad_image);
                foodName = (TextView)myView.findViewById(R.id.food_name);



            }
        }
    }


