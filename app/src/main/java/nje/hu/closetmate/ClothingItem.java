// ClothingItem.java
package nje.hu.closetmate;

public class ClothingItem {
    private int id;
    private String name;
    private String imageUri;
    private String category;
    private String color;
    private String season;
    private String style;
    private String occasion;
    private int wearCount;
    private String createdAt;

    public ClothingItem(int id, String name, String imageUri, String category,
                        String color, String season, String style,
                        String occasion, int wearCount, String createdAt) {
        this.id = id;
        this.name = name;
        this.imageUri = imageUri;
        this.category = category;
        this.color = color;
        this.season = season;
        this.style = style;
        this.occasion = occasion;
        this.wearCount = wearCount;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getImageUri() { return imageUri; }
    public String getCategory() { return category; }
    public String getColor() { return color; }
    public String getSeason() { return season; }
    public String getStyle() { return style; }
    public String getOccasion() { return occasion; }
    public int getWearCount() { return wearCount; }
    public String getCreatedAt() { return createdAt; }
}