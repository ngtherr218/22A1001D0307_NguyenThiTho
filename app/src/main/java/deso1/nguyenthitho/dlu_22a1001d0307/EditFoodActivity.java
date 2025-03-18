package deso1.nguyenthitho.dlu_22a1001d0307;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import deso1.nguyenthitho.dlu_22a1001d0307.helper.DatabaseHelper;
import deso1.nguyenthitho.dlu_22a1001d0307.model.Food;

public class EditFoodActivity extends AppCompatActivity {

    private EditText edtName, edtPrice, edtUnit;
    private Button btnUpdate,btnDelete;
    private DatabaseHelper databaseHelper;
    private ImageView imgFood;
    private int foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtUnit = findViewById(R.id.edtUnit);
        imgFood = findViewById(R.id.imgFood);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        databaseHelper = new DatabaseHelper(this);

        // Nhận ID món ăn từ Intent
        foodId = getIntent().getIntExtra("food_id", -1);
        Food food = databaseHelper.getFoodById(foodId);
        if (food != null) {
            edtName.setText(food.getName());
            edtPrice.setText(String.valueOf(food.getPrice()));
            edtUnit.setText(food.getUnit());
            imgFood.setImageResource(food.getImageResId());
        } else {
            Toast.makeText(this, "Món ăn không tồn tại!", Toast.LENGTH_SHORT).show();
            finish();
        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFood();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (databaseHelper.deleteFood(foodId)) {
                    Toast.makeText(EditFoodActivity.this, "Xóa món ăn thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditFoodActivity.this,MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(EditFoodActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateFood() {
        String name = edtName.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        String priceString = edtPrice.getText().toString().trim();

        if (priceString.endsWith(".0")) {
            priceString = priceString.substring(0, priceString.length() - 2);
        }

        int price = Integer.parseInt(priceString);

        boolean isUpdated = databaseHelper.updateFood(foodId,name, price, unit);

        if (isUpdated) {
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditFoodActivity.this,MainActivity.class);
            startActivity(intent);
            finishAffinity();
        } else {
            Toast.makeText(this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
        }
    }
}
