package ar.com.symsys.mobile.android.misprecios.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class PricesDataSource {
    private PricesSQLiteHelper          pricesSQLiteHelper;
    private SQLiteDatabase              database = null;
    public PricesDataSource(Context context){
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
                values.put(PricesTableSchema.TIMESTAMP, persistDate(price.getTimeStamp()));
                values.put(PricesTableSchema.BULK_PRICE, price.getBulkPrice());
                values.put(PricesTableSchema.BULK_QUANTITY, price.getBulkQuantity());
                
                database.insertOrThrow(PricesTableSchema.TABLE_NAME, null, values);
                
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
    private Long persistDate(Date date){
    	if(date != null){
    		return date.getTime();
    	}
    	return null;
    }
    
    private Date loadDate( Cursor cursor, int index){
    	if( cursor.isNull(index)){
    		return null;
    	}
    	return new Date(cursor.getLong(index));
    }

    public Price readCursor(Cursor cursor) {
        Price price = new Price();
        price.setProductId(cursor.getString(PricesTableSchema.colPRODUCT_ID));
        price.setBulkQuantity(cursor.getInt(PricesTableSchema.colBULK_QUANTITY));
        price.setBulkPrice(cursor.getFloat(PricesTableSchema.colBULK_PRICE));
        price.setTimeStamp(loadDate(cursor, PricesTableSchema.colTIMESTAMP ));
        price.setMarketId(cursor.getInt(PricesTableSchema.colMARKET_ID));
        return price;
    }
}
