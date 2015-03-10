package ar.com.symsys.misprecios.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class PricesSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPrices";
    private static final int        dbVersion           = 1;
    private static final String     sqlCreateProducts   = "CREATE TABLE "
            + PricesTableSchema.TABLE_NAME + " ("
            + PricesTableSchema.PRODUCT_ID      + " INTEGER PRIMARY KEY, "
            + PricesTableSchema.MARKET_ID       + " INTEGER, "
            + PricesTableSchema.TIMESTAMP       + " INTEGER, "
            + PricesTableSchema.BULK_PRICE      + " REAL, "
            + PricesTableSchema.BULK_QUANTITY   + " INTEGER)";


    public PricesSQLiteHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateProducts);
//      loadDB(db)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductsTableSchema.TABLE_NAME);
        db.execSQL(sqlCreateProducts);
//      loadDB(db)
    }

    /*private void loadDB(SQLiteDatabase db){}*/
}
