package ar.com.symsys.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class Product {
    private long        ProductId;
    private int         BrandId;
    private String      Category;
    private String      Description;

    public long getProductId() {
        return ProductId;
    }

    public void setProductId(long productId) {
        ProductId = productId;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
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

