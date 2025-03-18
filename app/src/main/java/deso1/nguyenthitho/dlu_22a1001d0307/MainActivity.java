package deso1.nguyenthitho.dlu_22a1001d0307;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import deso1.nguyenthitho.dlu_22a1001d0307.adapter.FoodAdapter;
import deso1.nguyenthitho.dlu_22a1001d0307.helper.DatabaseHelper;
import deso1.nguyenthitho.dlu_22a1001d0307.model.Food;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    FoodAdapter foodAdapter;
    List<Food> foodList;
    FloatingActionButton fabAddFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAddFood = findViewById(R.id.fabAddFood);
        databaseHelper = new DatabaseHelper(this);

        // Thêm dữ liệu mẫu vào SQLite nếu chưa có
        if (databaseHelper.getAllFoods().isEmpty()) {
            databaseHelper.addFood("Gỏi gà", 120000, "VND", R.drawable.goi_ga);
            databaseHelper.addFood("Bò lúc lắc", 150000, "VND", R.drawable.bo_luc_lac);
            databaseHelper.addFood("Tôm hấp", 100000, "VND", R.drawable.tom_hap);
        }

        // Lấy danh sách món ăn từ SQLite
        foodList = databaseHelper.getAllFoods();
        foodAdapter = new FoodAdapter(this, foodList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodAdapter);

        fabAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
                startActivity(intent);
            }
        });
    }
}