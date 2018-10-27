package aryasoft.company.arachoob.Models;

public class Category {

    private int CategoryIcon;
    private String CategoryTitle;

    public Category(int categoryIcon, String categoryTitle) {
        CategoryIcon = categoryIcon;
        CategoryTitle = categoryTitle;
    }

    public int getCategoryIcon() {
        return CategoryIcon;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    public void setCategoryIcon(int categoryIcon) {
        CategoryIcon = categoryIcon;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

}
