package com.teamliquid.volksfitness.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.teamliquid.volksfitness.R;
import com.teamliquid.volksfitness.pojo.Meal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    Context context;
    private List<Meal> meals;
    //Interface callback
    private onItemClickListener onItemClickListener;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param context Context
     * @param meals Data<List<Meal>> containing the meals
     * by RecyclerView.
     */
    public MealAdapter(Context context, List<Meal> meals){
        this.context = context;
        this.meals = meals;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_recycler_view, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position){
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Meal meal = meals.get(position);

        viewHolder.mealType.setText(meal.getMealType());
        viewHolder.foodRecord.setText(meal.getMealFood());
        viewHolder.calories.setText(String.valueOf(meal.getMealCalories()));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        viewHolder.mealDate.setText(formatter.format(meal.getMealTime()));

        if (onItemClickListener!=null){
            viewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int elementPosition = viewHolder.getLayoutPosition();
                    onItemClickListener.onItemClick(viewHolder.imageDelete,elementPosition);
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return meals.size();
    }

    //refresh Item
    public void refreshMeal(List<Meal> meals){
        this.meals = meals;
        notifyDataSetChanged();
    }

    //Get the meal that is prepare to be deleted
    public Meal getCurrentMeal(int position){
        return meals.get(position);
    }

    // Interface callback
    // Codes are from https://wiki.jikexueyuan.com/project/twenty-four-Scriptures/recycler-view-add-remove.html
    // I am very grateful to the unnamed Author.
    public interface onItemClickListener{
        void onItemClick(View view,int position);
    }
    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }




    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView mealType;
        private final TextView foodRecord;
        private final TextView calories;
        private final ImageView imageDelete;
        private final TextView mealDate;

        public ViewHolder(View view){
            super(view);
            // Define click listener for the ViewHolder's View
            mealType = itemView.findViewById(R.id.text_meal_type);
            foodRecord = itemView.findViewById(R.id.text_food_record);
            calories = itemView.findViewById(R.id.text_calories);
            imageDelete = itemView.findViewById(R.id.image_delete);
            mealDate = itemView.findViewById(R.id.text_date);
        }
    }
}
