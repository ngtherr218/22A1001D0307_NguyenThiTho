package deso1.nguyenthitho.dlu_22a1001d0307.model;

public class Food {
    private int id;
    private String name;
    private double price;
    private String unit;
    private int imageResId; // Lưu resource ID thay vì URL

    public Food(int id, String name, double price, String unit, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.imageResId = imageResId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getUnit() { return unit; }
    public int getImageResId() { return imageResId; } // Lấy resource ID của ảnh
}
