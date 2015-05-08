package ar.com.symsys.mobile.android.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class BrandsTableSchema {
    public static final String      TABLE_NAME                  = "Brands";
    public static final String      BRAND_ID                    = "BrandId";
    public static final String      BRAND_NAME                  = "BrandName";

    public static final String[]    COLUMNS = {
            BRAND_ID,
            BRAND_NAME
    };

    public static final int      colBRAND_ID                    = 0;
    public static final int      colBRAND_NAME                  = 1;
}
