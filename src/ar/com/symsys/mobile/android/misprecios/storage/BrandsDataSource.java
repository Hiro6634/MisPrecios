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
public class BrandsDataSource {
    private BrandsSQLiteHelper        brandsSQLiteHelper;
    private SQLiteDatabase              database = null;
    public BrandsDataSource(Context context){
        brandsSQLiteHelper = new BrandsSQLiteHelper(context);
    }

    public void openDataBase(){
        database = brandsSQLiteHelper.getWritableDatabase();
    }

    public void closeDataBase(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    public void addBrand(String brand) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(BrandsTableSchema.BRAND_NAME, brand);
                
                database.insert( 
                		BrandsTableSchema.TABLE_NAME, 
                		null, values);
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

    public List<String> getAllBrands() {
        ArrayList<String> brandList = new ArrayList<String>();
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                		BrandsTableSchema.TABLE_NAME,
                		BrandsTableSchema.COLUMNS,
                        null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        brandList.add(cursor.getString(BrandsTableSchema.colBRAND_NAME));
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
        return brandList;
    }

    public String findBrandById( int brandId) {
        String brand = null;
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        BrandsTableSchema.TABLE_NAME,
                        BrandsTableSchema.COLUMNS,
                        BrandsTableSchema.BRAND_ID + " = ?",
                        new String[]{String.valueOf(brandId)}, null, null, null);

                if (cursor.moveToFirst()) {
                    brand = cursor.getString(BrandsTableSchema.colBRAND_NAME);
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
        return brand;
    }

    public int findBrandByName( String brandName) {
        int brandId = -1;
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        BrandsTableSchema.TABLE_NAME,
                        BrandsTableSchema.COLUMNS,
                        BrandsTableSchema.BRAND_NAME + " = ?",
                        new String[]{brandName}, null, null, null);

                if (cursor.moveToFirst()) {
                    brandId = cursor.getInt(BrandsTableSchema.colBRAND_ID);
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
        return brandId;
    }
}
