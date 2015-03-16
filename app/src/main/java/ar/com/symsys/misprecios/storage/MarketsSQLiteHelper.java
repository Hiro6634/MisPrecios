package ar.com.symsys.misprecios.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLClientInfoException;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class MarketsSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPrices";
    private static final int        dbVersion           = 3;
    private static final String     sqlCreateMarkets   = "CREATE TABLE "
            + MarketsTableSchema.TABLE_NAME + " ("
            + MarketsTableSchema.MARKET_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MarketsTableSchema.NAME           + " TEXT, "
            + MarketsTableSchema.LOCATION       + " TEXT)";

    public MarketsSQLiteHelper(Context context){
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(sqlCreateMarkets);
            loadDB(db);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE IF EXISTS " + MarketsTableSchema.TABLE_NAME);
            db.execSQL(sqlCreateMarkets);
            loadDB(db);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void loadDB(SQLiteDatabase db){
        try {
            db.execSQL("INSERT INTO " + MarketsTableSchema.TABLE_NAME + " (" + MarketsTableSchema.NAME + ", " + MarketsTableSchema.LOCATION + ") VALUES ( 'CHANGO MAS', 'BURZACO')");
            db.execSQL("INSERT INTO " + MarketsTableSchema.TABLE_NAME + " (" + MarketsTableSchema.NAME + ", " + MarketsTableSchema.LOCATION + ") VALUES ( 'CARREFOUR', 'ADROGUE')");
            db.execSQL("INSERT INTO " + MarketsTableSchema.TABLE_NAME + " (" + MarketsTableSchema.NAME + ", " + MarketsTableSchema.LOCATION + ") VALUES ( 'JUMBO', 'LOMAS')");
            db.execSQL("INSERT INTO " + MarketsTableSchema.TABLE_NAME + " (" + MarketsTableSchema.NAME + ", " + MarketsTableSchema.LOCATION + ") VALUES ( 'DIA', 'BYNNON')");
            db.execSQL("INSERT INTO " + MarketsTableSchema.TABLE_NAME + " (" + MarketsTableSchema.NAME + ", " + MarketsTableSchema.LOCATION + ") VALUES ( 'LA HERRADURA', 'ADROGUE')");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
