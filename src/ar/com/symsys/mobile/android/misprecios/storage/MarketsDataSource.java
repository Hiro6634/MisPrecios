package ar.com.symsys.mobile.android.misprecios.storage;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class MarketsDataSource {
    private MarketsSQLiteHelper         marketsSQLiteHelper;
    private SQLiteDatabase              database = null;
    public MarketsDataSource(Context context){
        marketsSQLiteHelper = new MarketsSQLiteHelper(context);
    }

    public void openDataBase(){
        database = marketsSQLiteHelper.getWritableDatabase();
    }

    public void closeDataBase(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    public void addMarket(Market market) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(MarketsTableSchema.NAME, market.getName());
                values.put(MarketsTableSchema.LOCATION, market.getLocation());	
                
                database.insertOrThrow(
                		MarketsTableSchema.TABLE_NAME, 
                		null, 
                		values);
                
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

    public void addMarket( int marketId, String name, String location ) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(MarketsTableSchema.MARKET_ID, marketId);
                values.put(MarketsTableSchema.NAME, name);
                values.put(MarketsTableSchema.LOCATION, location);
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

    public List<Market> getAllMarkets() {
        ArrayList<Market> marketList = new ArrayList<Market>();
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        MarketsTableSchema.TABLE_NAME,
                        MarketsTableSchema.COLUMNS,
                        null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        marketList.add(readCursor(cursor));
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
        return marketList;
    }

    public List<String> getAllMarketsStr() {
        ArrayList<String> marketList = new ArrayList<String>();
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        MarketsTableSchema.TABLE_NAME,
                        MarketsTableSchema.COLUMNS,
                        null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        marketList.add(readCursor(cursor).getName() + ", " + readCursor(cursor).getLocation());
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
        return marketList;
    }

    public Market readCursor( Cursor cursor ){
        Market  market = new Market();

        market.setMarketId(cursor.getInt(MarketsTableSchema.colMARKET_ID));
        market.setName(cursor.getString(MarketsTableSchema.colNAME));
        market.setLocation(cursor.getString(MarketsTableSchema.colLOCATION));

        return market;
    }

    public Market findMarket( int marketId){
        Market market = null;
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        MarketsTableSchema.TABLE_NAME,
                        MarketsTableSchema.COLUMNS,
                        MarketsTableSchema.MARKET_ID + " = ?",
                        new String[]{String.valueOf(marketId)},
                        null, null, null);
                if(cursor != null ){
                    cursor.moveToFirst();
                    market = readCursor(cursor);
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

        return market;
    }

	public int findMarketByName(String market) {
		String marketName;
		String marketLocation;
		int marketId = -1;
		
		String data[] = market.split(",");
		marketName = data[0].trim();
		marketLocation = data[1].trim();
		
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        MarketsTableSchema.TABLE_NAME,
                        MarketsTableSchema.COLUMNS,
                        "TRIM(" + MarketsTableSchema.NAME + ") = ? AND " +
                        "TRIM(" + MarketsTableSchema.LOCATION + ") = ?",
                        new String[]{ marketName, marketLocation},
                        null, null, null);
                cursor.moveToFirst();
                if(!cursor.isAfterLast()){
                    marketId = cursor.getInt(MarketsTableSchema.colMARKET_ID);
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

        return marketId;
	}
}
