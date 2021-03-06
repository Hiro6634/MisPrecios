package ar.com.symsys.misprecios.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class PricesDataSource {
    private PricesSQLiteHelper          pricesSQLiteHelper;
    private SQLiteDatabase              database = null;
    private Context                     context;

    public PricesDataSource(Context context){
        this.context = context;
        pricesSQLiteHelper = new PricesSQLiteHelper(context);
    }

    public void openDataBase(){
        database = pricesSQLiteHelper.getWritableDatabase();
    }

    public void closeDataBase(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    public void addPrice(Price price) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(PricesTableSchema.PRODUCT_ID, price.getProductId());
                values.put(PricesTableSchema.MARKET_ID, price.getMarketId());
                values.put(PricesTableSchema.TIMESTAMP, price.getTimeStamp().toMillis(true));
                values.put(PricesTableSchema.BULK_PRICE, price.getBulkPrice());
                values.put(PricesTableSchema.BULK_QUANTITY, price.getBulkQuantity());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    closeDataBase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Price> findPriceByProductId( String productId ){
        ArrayList<Price> priceList = new ArrayList<Price>();
        synchronized (this){
            try{
                openDataBase();

                Cursor cursor = database.query(
                        PricesTableSchema.TABLE_NAME,           //TABLE NAME
                        PricesTableSchema.COLUMNS,              //COLUMNS
                        PricesTableSchema.PRODUCT_ID + " = ?",  //SELECTION
                        new String[]{productId},                //SELECTION ARGS
                        null,                                   //GROUP BY
                        null,                                   //HAVING
                        null);                                  //ORDER BY
//                      PricesTableSchema.TIMESTAMP);           //ORDER BY

                if( cursor.moveToFirst()){
                    while (!cursor.isAfterLast()){
                        Price price = readCursor(cursor);
                        priceList.add(price);
                        cursor.moveToNext();
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            finally {
                try{
                    closeDataBase();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return priceList;
    }

    public List<Price> getAllPrices() {
        ArrayList<Price> priceList = new ArrayList<Price>();
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        PricesTableSchema.TABLE_NAME,
                        PricesTableSchema.COLUMNS,
                        null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    while (cursor.isAfterLast()) {
                        priceList.add(readCursor(cursor));
                        cursor.moveToNext();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    closeDataBase();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return priceList;
    }

    public Price readCursor(Cursor cursor) {
        Price price = new Price();
        Time time = new Time();

        price.setProductId(cursor.getString(PricesTableSchema.colPRODUCT_ID));
        price.setBulkQuantity(cursor.getInt(PricesTableSchema.colBULK_QUANTITY));
        price.setBulkPrice(cursor.getFloat(PricesTableSchema.colBULK_PRICE));
        time.set(cursor.getLong(PricesTableSchema.colTIMESTAMP));
        price.setTimeStamp(time);
        price.setMarketId(cursor.getInt(PricesTableSchema.colMARKET_ID));
        return price;
    }
}
