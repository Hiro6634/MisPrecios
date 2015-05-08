package ar.com.symsys.mobile.android.misprecios.storage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.R;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class BrandsSQLiteHelper extends SQLiteOpenHelper {
	private final Context	_context;
    private static final String     dbName              = "MyPricesBrands";
    private static final int        dbVersion           = 3;
    private static final String     sqlCreateProducts   = "CREATE TABLE "
            + BrandsTableSchema.TABLE_NAME + " ("
            + BrandsTableSchema.BRAND_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BrandsTableSchema.BRAND_NAME + " TEXT)";


    public BrandsSQLiteHelper(Context context){
        super(context, dbName, null, dbVersion);
        this._context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateProducts);
      loadDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BrandsTableSchema.TABLE_NAME);
        db.execSQL(sqlCreateProducts);
      loadDB(db);
    }

    private void loadDB(SQLiteDatabase db){
    	InputStream 			inputStream = _context.getResources().openRawResource(R.raw.brandsdefault);
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
			
			String[] filas = byteArrayOutputStream.toString().split("\r\n");
			for( i = 0; i < filas.length; i++){
				ContentValues values = new ContentValues();
				values.put(BrandsTableSchema.BRAND_NAME, filas[i]);
				long l = db.insert(BrandsTableSchema.TABLE_NAME, null, values);
				Toast.makeText(_context, "Insert result: " + l, Toast.LENGTH_SHORT).show();
			}
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
