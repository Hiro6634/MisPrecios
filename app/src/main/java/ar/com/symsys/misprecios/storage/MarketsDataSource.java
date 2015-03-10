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
public class MarketsDataSource {
    private MarketsSQLiteHelper         marketsSQLiteHelper;
    private SQLiteDatabase              database = null;
    private Context                     context;

    public MarketsDataSource(Context context){
        this.context = context;
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

                values.put(MarketsTableSchema.MARKET_ID, market.getMarketId());
                values.put(MarketsTableSchema.NAME, market.getName());
                values.put(MarketsTableSchema.LOCATION, market.getLocation());
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
                    while (cursor.moveToFirst()) {
                        while (cursor.isAfterLast()) {
                            marketList.add(readCursor(cursor));
                        }
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
}
