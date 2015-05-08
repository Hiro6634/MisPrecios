package ar.com.symsys.mobile.android.misprecios.storage;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class MarketsTableSchema {
    public static final String      TABLE_NAME                  = "Market";
    public static final String      MARKET_ID                   = "MarketId";
    public static final String      NAME                        = "Name";
    public static final String      LOCATION                    = "Location";

    public static final String[]    COLUMNS = {
            MARKET_ID,
            NAME,
            LOCATION
    };

    public static final int      colMARKET_ID                   = 0;
    public static final int      colNAME                        = 1;
    public static final int      colLOCATION                    = 2;
}
