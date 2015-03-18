package ar.com.symsys.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class Product {
    private String      ProductId;
    private String      Brand;
    private String      Category;
    private String      Description;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

