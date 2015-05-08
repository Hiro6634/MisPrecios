package ar.com.symsys.mobile.android.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class CategoriesTableSchema {
    public static final String      TABLE_NAME                  = "Categories";
    public static final String      CATEGORY_ID                 = "CategoryId";
    public static final String      CATEGORY_NAME               = "CategoryName";

    public static final String[]    COLUMNS = {
            CATEGORY_ID,
            CATEGORY_NAME
    };

    public static final int      colCATEGORY_ID                    = 0;
    public static final int      colCATEGORY_NAME                  = 1;
}
