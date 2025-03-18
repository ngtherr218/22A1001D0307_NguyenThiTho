package deso1.nguyenthitho.dlu_22a1001d0307;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import deso1.nguyenthitho.dlu_22a1001d0307.helper.DatabaseHelper;

public class AddFoodActivity extends AppCompatActivity {

    private EditText etFoodName, etFoodPrice, etFoodUnit;
    private Button btnSaveFood;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        etFoodName = findViewById(R.id.etFoodName);
        etFoodPrice = findViewById(R.id.etFoodPrice);
        etFoodUnit = findViewById(R.id.etFoodUnit);
        btnSaveFood = findViewById(R.id.btnSaveFood);

        databaseHelper = new DatabaseHelper(this);

        btnSaveFood.setOnClickListener(v -> saveFood());
    }

    private void saveFood() {
        String name = etFoodName.getText().toString().trim();
        String priceStr = etFoodPrice.getText().toString().trim();
        String unit = etFoodUnit.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isAdded = databaseHelper.addFood(name, price, unit, R.drawable.tom_hap);

        if (isAdded) {
            Toast.makeText(this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddFoodActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        } else {
            Toast.makeText(this, "Thêm món ăn thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

}
