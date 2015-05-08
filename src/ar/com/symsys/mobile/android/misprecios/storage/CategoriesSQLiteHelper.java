package ar.com.symsys.mobile.android.misprecios.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class CategoriesSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPricesCategories";
    private static final int        dbVersion           = 1;
    private static final String     sqlCreateProducts   = "CREATE TABLE "
            + CategoriesTableSchema.TABLE_NAME + " ("
            + CategoriesTableSchema.CATEGORY_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + CategoriesTableSchema.CATEGORY_NAME + " TEXT)";


    public CategoriesSQLiteHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateProducts);
      loadDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTableSchema.TABLE_NAME);
        db.execSQL(sqlCreateProducts);
      loadDB(db);
    }

    private void loadDB(SQLiteDatabase db){
    }
}
