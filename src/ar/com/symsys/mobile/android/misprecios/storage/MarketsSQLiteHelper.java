package ar.com.symsys.mobile.android.misprecios.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import ar.com.symsys.mobile.android.misprecios.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class MarketsSQLiteHelper extends SQLiteOpenHelper {
    private static final String     dbName              = "MyPricesMarkets";
    private static final int        dbVersion           = 1;
    private static final String     sqlCreateMarkets   = "CREATE TABLE "
            + MarketsTableSchema.TABLE_NAME + " ("
            + MarketsTableSchema.MARKET_ID      + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MarketsTableSchema.NAME           + " TEXT, "
            + MarketsTableSchema.LOCATION       + " TEXT)";
    protected Context _context;
    
    public MarketsSQLiteHelper(Context context){
        super(context, dbName, null, dbVersion);
        _context = context;
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
    	InputStream 			inputStream = _context.getResources().openRawResource(R.raw.marketsdefault);
    	ByteArrayOutputStream 	byteArrayOutputStream = new ByteArrayOutputStream(); 
		int						i;

		try {
        	
			i = inputStream.read();
			while( i!=-1)
			{
				byteArrayOutputStream.write(i);
				i = inputStream.read();
			}
			inputStream.close();
			
			// parseamos el stream
			String[] datos;
			String[] filas = byteArrayOutputStream.toString().split("\r\n");
			for( i = 0; i < filas.length; i++){
				datos = filas[i].split(",");

				db.execSQL(
						"INSERT INTO " + 
						MarketsTableSchema.TABLE_NAME + " (" +	
						MarketsTableSchema.NAME + ", " + 
						MarketsTableSchema.LOCATION + ") " +
						"VALUES ( '" +  
						datos[0].trim() + "', '" + 
						datos[1].trim() + "')");
			}
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
