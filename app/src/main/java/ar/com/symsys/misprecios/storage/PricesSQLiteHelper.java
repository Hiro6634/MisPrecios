package ar.com.symsys.misprecios.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.text.format.Time;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class PricesSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPrices";
    private static final String     sqlCreateProducts   = "CREATE TABLE "
            + PricesTableSchema.TABLE_NAME + " ("
            + PricesTableSchema.PRODUCT_ID      + " TEXT, "
            + PricesTableSchema.MARKET_ID       + " INTEGER, "
            + PricesTableSchema.TIMESTAMP       + " INTEGER, "
            + PricesTableSchema.BULK_PRICE      + " REAL, "
            + PricesTableSchema.BULK_QUANTITY   + " INTEGER)";


    public PricesSQLiteHelper(Context context){
        super(context, dbName, null, StorageManager.getInstance().dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateProducts);



        loadDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProductsTableSchema.TABLE_NAME);
        db.execSQL(sqlCreateProducts);
        loadDB(db);
    }

    private void loadDB(SQLiteDatabase db){
        Time today = new Time();
        ContentValues values = new ContentValues();

        values.put(PricesTableSchema.PRODUCT_ID,  "7790045824890");
        values.put(PricesTableSchema.MARKET_ID, 1);
        today.setToNow();
        values.put(PricesTableSchema.TIMESTAMP, today.toMillis(true));
        values.put(PricesTableSchema.BULK_PRICE, 10.0);
        values.put(PricesTableSchema.BULK_QUANTITY, 1);

        db.insertOrThrow(
                PricesTableSchema.TABLE_NAME,
                null,
                values);

        values.put(PricesTableSchema.PRODUCT_ID,  "7790045824810");
        values.put(PricesTableSchema.MARKET_ID, 1);
        today.setToNow();
        values.put(PricesTableSchema.TIMESTAMP, today.toMillis(true));
        values.put(PricesTableSchema.BULK_PRICE, 15.0);
        values.put(PricesTableSchema.BULK_QUANTITY, 1);

        db.insertOrThrow(
                PricesTableSchema.TABLE_NAME,
                null,
                values);

        values.put(PricesTableSchema.PRODUCT_ID,  "7790045824890");
        values.put(PricesTableSchema.MARKET_ID, 2);
        today.setToNow();
        values.put(PricesTableSchema.TIMESTAMP, today.toMillis(true));
        values.put(PricesTableSchema.BULK_PRICE, 12.0);
        values.put(PricesTableSchema.BULK_QUANTITY, 1);

        db.insertOrThrow(
                PricesTableSchema.TABLE_NAME,
                null,
                values);

        values.put(PricesTableSchema.PRODUCT_ID,  "7790045824893");
        values.put(PricesTableSchema.MARKET_ID, 2);
        today.setToNow();
        values.put(PricesTableSchema.TIMESTAMP, today.toMillis(true));
        values.put(PricesTableSchema.BULK_PRICE, 15.0);
        values.put(PricesTableSchema.BULK_QUANTITY, 1);

        db.insertOrThrow(
                PricesTableSchema.TABLE_NAME,
                null,
                values);
    }
}
