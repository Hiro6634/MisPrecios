package ar.com.symsys.mobile.android.misprecios.storage;
/**
 * Created by hsuyama on 10/03/2015.
 */
public class Product {
    private String      ProductId;
    private int         BrandId;
    private int         CategoryId;
    private String      Description;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getBrandId() {
        return BrandId;
    }

    public void setBrandId(int brandId) {
        BrandId = brandId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}

