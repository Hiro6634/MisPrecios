package ar.com.symsys.mobile.android.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class PricesTableSchema {
    public static final String      TABLE_NAME                  = "Price";
    public static final String      PRODUCT_ID                  = "ProductId";
    public static final String      MARKET_ID                   = "MarketId";
    public static final String      TIMESTAMP                   = "TimeStamp";
    public static final String      BULK_PRICE                  = "BulkPrice";
    public static final String      BULK_QUANTITY               = "BulkQuantity";

    public static final String[]    COLUMNS = {
            PRODUCT_ID,
            MARKET_ID,
            TIMESTAMP,
            BULK_PRICE,
            BULK_QUANTITY
    };

    public static final int      colPRODUCT_ID                  = 0;
    public static final int      colMARKET_ID                   = 1;
    public static final int      colTIMESTAMP                   = 2;
    public static final int      colBULK_PRICE                  = 3;
    public static final int      colBULK_QUANTITY               = 4;

}
