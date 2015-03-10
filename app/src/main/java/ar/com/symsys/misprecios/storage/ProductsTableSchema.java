package ar.com.symsys.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class ProductsTableSchema {
    public static final String      TABLE_NAME                  = "Products";
    public static final String      PRODUCT_ID                  = "ProductId";
    public static final String      BRAND_ID                    = "BrandId";
    public static final String      CATEGORY                    = "Category";
    public static final String      DESCRIPTION                 = "Description";

    public static final String[]    COLUMNS = {
            PRODUCT_ID,
            BRAND_ID,
            CATEGORY,
            DESCRIPTION
    };

    public static final int      colPRODUCT_ID                  = 0;
    public static final int      colBRAND_ID                    = 1;
    public static final int      colCATEGORY                    = 2;
    public static final int      colDESCRIPTION                 = 3;

}
