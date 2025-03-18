package deso1.nguyenthitho.dlu_22a1001d0307.adapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import deso1.nguyenthitho.dlu_22a1001d0307.EditFoodActivity;
import deso1.nguyenthitho.dlu_22a1001d0307.R;
import deso1.nguyenthitho.dlu_22a1001d0307.helper.DatabaseHelper;
import deso1.nguyenthitho.dlu_22a1001d0307.model.Food;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.tvName.setText(food.getName());
        holder.tvPrice.setText("Giá: " + food.getPrice() + " " + food.getUnit());
        holder.imgFood.setImageResource(food.getImageResId());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditFoodActivity.class);
                intent.putExtra("food_id", food.getId()); // Truyền ID món ăn qua Intent
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView imgFood;
        ImageView ivEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgFood = itemView.findViewById(R.id.imgFood);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
