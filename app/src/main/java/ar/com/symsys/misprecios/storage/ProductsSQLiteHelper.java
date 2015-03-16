package ar.com.symsys.misprecios.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class ProductsSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPrices";
    private static final String     sqlCreateProducts   = "CREATE TABLE "
            + ProductsTableSchema.TABLE_NAME + " ("
            + ProductsTableSchema.PRODUCT_ID    + " INTEGER PRIMARY KEY, "
            + ProductsTableSchema.BRAND_ID      + " INTEGER, "
            + ProductsTableSchema.CATEGORY      + " TEXT, "
            + ProductsTableSchema.DESCRIPTION   + " TEXT)";


    public ProductsSQLiteHelper(Context context){
        super(context, dbName, null, StorageManager.getInstance().dbVersion);
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
