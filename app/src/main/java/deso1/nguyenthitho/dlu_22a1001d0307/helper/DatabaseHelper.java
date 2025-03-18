package deso1.nguyenthitho.dlu_22a1001d0307.helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import deso1.nguyenthitho.dlu_22a1001d0307.model.Food;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodDB";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "foods";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_IMAGE = "image";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PRICE + " REAL, " +
                COLUMN_UNIT + " TEXT, " +
                COLUMN_IMAGE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addFood(String name, double price, String unit, int image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_UNIT, unit);
        values.put(COLUMN_IMAGE, image);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1;
    }


    public List<Food> getAllFoods() {
        List<Food> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double price = cursor.getDouble(2);
                String unit = cursor.getString(3);
                int image = cursor.getInt(4);

                foodList.add(new Food(id, name, price, unit, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }

    public Food getFoodById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Food food = null;
        Cursor cursor = null;

        try {
            cursor = db.query("foods",
                    new String[]{"id", "name", "price", "unit", "image"},
                    "id = ?",
                    new String[]{String.valueOf(id)},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int foodId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                int image = cursor.getInt(cursor.getColumnIndexOrThrow("image"));
                food = new Food(foodId, name, price, unit, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return food;
    }



    // Cập nhật món ăn (Không thay đổi ảnh và ID)
    public boolean updateFood(int id, String name, double price, String unit) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_UNIT, unit);

        // Thực hiện cập nhật và kiểm tra số dòng bị ảnh hưởng
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        // Nếu có ít nhất 1 dòng bị ảnh hưởng, cập nhật thành công -> trả về true
        return rowsAffected > 0;
    }

    public boolean deleteFood(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return rowsDeleted > 0;
    }

}
