package aryasoft.company.arachoob.Models;

public class Product {

    private int ProductPhoto;
    private String ProductTitle;
    private String ProductPrimaryPrice;
    private String ProductDisCountedPrice;
    private String ProductDescription;

    public Product(int productPhoto, String productTitle, String productPrimaryPrice, String productDisCountedPrice, String productDescription) {
        ProductPhoto = productPhoto;
        ProductTitle = productTitle;
        ProductPrimaryPrice = productPrimaryPrice;
        ProductDisCountedPrice = productDisCountedPrice;
        ProductDescription = productDescription;
    }

    public int getProductPhoto() {
        return ProductPhoto;
    }

    public String getProductTitle() {
        return ProductTitle;
    }

    public String getProductPrimaryPrice() {
        return ProductPrimaryPrice;
    }

    public String getProductDisCountedPrice() {
        return ProductDisCountedPrice;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductPhoto(int productPhoto) {
        ProductPhoto = productPhoto;
    }

    public void setProductTitle(String productTitle) {
        ProductTitle = productTitle;
    }

    public void setProductPrimaryPrice(String productPrimaryPrice) {
        ProductPrimaryPrice = productPrimaryPrice;
    }

    public void setProductDisCountedPrice(String productDisCountedPrice) {
        ProductDisCountedPrice = productDisCountedPrice;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

}
