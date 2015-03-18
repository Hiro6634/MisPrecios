package ar.com.symsys.misprecios.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class ProductsSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPricesProducts";
    private static final int        dbVersion           = 1;
    private static final String     sqlCreateProducts   = "CREATE TABLE "
            + ProductsTableSchema.TABLE_NAME + " ("
            + ProductsTableSchema.PRODUCT_ID    + " TEXT PRIMARY KEY, "
            + ProductsTableSchema.BRAND         + " TEXT, "
            + ProductsTableSchema.CATEGORY      + " TEXT, "
            + ProductsTableSchema.DESCRIPTION   + " TEXT)";


    public ProductsSQLiteHelper(Context context){
        super(context, dbName, null, dbVersion);
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
        ContentValues values = new ContentValues();

        values.put(ProductsTableSchema.PRODUCT_ID, "7790045824893");
        values.put(ProductsTableSchema.BRAND, "Granix");
        values.put(ProductsTableSchema.CATEGORY, "Almacen");
        values.put(ProductsTableSchema.DESCRIPTION, "Galletas de Avena y Pasas");

        db.insertOrThrow(
                ProductsTableSchema.TABLE_NAME,
                null,
                values
        );
    }
}
