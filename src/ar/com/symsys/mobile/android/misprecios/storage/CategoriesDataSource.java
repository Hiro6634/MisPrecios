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
public class CategoriesDataSource {
    private CategoriesSQLiteHelper      categoriesSQLiteHelper;
    private SQLiteDatabase              database = null;
    public CategoriesDataSource(Context context){
        categoriesSQLiteHelper = new CategoriesSQLiteHelper(context);
    }

    public void openDataBase(){
        database = categoriesSQLiteHelper.getWritableDatabase();
    }

    public void closeDataBase(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    public void addCategory(String category) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(CategoriesTableSchema.CATEGORY_NAME , category);
                database.insert(CategoriesTableSchema.TABLE_NAME, null, values);
                
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
    			
    public List<String> getAllCategories() {
        ArrayList<String> brandList = new ArrayList<String>();
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                		CategoriesTableSchema.TABLE_NAME,
                		CategoriesTableSchema.COLUMNS,
                        null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        brandList.add(cursor.getString(CategoriesTableSchema.colCATEGORY_NAME));
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

    public String findCategoryById( int categoryId) {
        String category = null;
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        CategoriesTableSchema.TABLE_NAME,
                        CategoriesTableSchema.COLUMNS,
                        CategoriesTableSchema.CATEGORY_ID + " = ?",
                        new String[]{String.valueOf(categoryId)}, null, null, null);

                if (cursor.moveToFirst()) {
                    category = cursor.getString(CategoriesTableSchema.colCATEGORY_NAME);
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
        return category;
    }
    
    public int findCategoryByName(String categoryName){
    	int categoryId = -1;
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        CategoriesTableSchema.TABLE_NAME,
                        CategoriesTableSchema.COLUMNS,
                        CategoriesTableSchema.CATEGORY_NAME + " = ?",
                        new String[]{categoryName}, null, null, null);

                if (cursor.moveToFirst()) {
                    categoryId = cursor.getInt(CategoriesTableSchema.colCATEGORY_ID);
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
        return categoryId;
    }
}
