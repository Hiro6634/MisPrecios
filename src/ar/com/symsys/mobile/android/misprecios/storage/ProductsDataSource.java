package ar.com.symsys.mobile.android.misprecios.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class ProductsDataSource {
    private ProductsSQLiteHelper        productsSQLiteHelper;
    private SQLiteDatabase              database = null;
    public ProductsDataSource(Context context){
        productsSQLiteHelper = new ProductsSQLiteHelper(context);
    }

    public void openDataBase(){
        database = productsSQLiteHelper.getWritableDatabase();
    }

    public void closeDataBase(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    public void addProduct(Product product) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(ProductsTableSchema.PRODUCT_ID, product.getProductId());
                values.put(ProductsTableSchema.BRAND_ID, product.getBrandId());
                values.put(ProductsTableSchema.CATEGORY_ID, product.getCategoryId());
                values.put(ProductsTableSchema.DESCRIPTION, product.getDescription());
                
                database.insertOrThrow( ProductsTableSchema.TABLE_NAME, null, values);
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

    public void updateProduct(Product product) {
        synchronized (this) {
            try {
                openDataBase();
                ContentValues values = new ContentValues();

                values.put(ProductsTableSchema.PRODUCT_ID, product.getProductId());
                values.put(ProductsTableSchema.BRAND_ID, product.getBrandId());
                values.put(ProductsTableSchema.CATEGORY_ID, product.getCategoryId());
                values.put(ProductsTableSchema.DESCRIPTION, product.getDescription());
                
                database.update(
                		ProductsTableSchema.TABLE_NAME, 
                		values, 
                		ProductsTableSchema.PRODUCT_ID + " = ?",
                		new String[]{product.getProductId()});
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
 
    public List<Product> getAllProducts() {
        ArrayList<Product> productList = new ArrayList<Product>();
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        ProductsTableSchema.TABLE_NAME,
                        ProductsTableSchema.COLUMNS,
                        null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        productList.add(readCursor(cursor));
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
        return productList;
    }

    public Product findProductById( String productId) {
        Product product = null;
        synchronized (this) {
            try {
                openDataBase();

                Cursor cursor = database.query(
                        ProductsTableSchema.TABLE_NAME,
                        ProductsTableSchema.COLUMNS,
                        ProductsTableSchema.PRODUCT_ID + " = ?",
                        new String[]{productId}, null, null, null);

                if (cursor.moveToFirst()) {
                    product = readCursor(cursor);
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
        return product;
    }

    public Product readCursor( Cursor cursor ){
        Product             product = new Product();

        product.setProductId(cursor.getString(ProductsTableSchema.colPRODUCT_ID));
        product.setBrandId(cursor.getInt(ProductsTableSchema.colBRAND_ID));
        product.setCategoryId(cursor.getInt(ProductsTableSchema.colCATEGORY_ID));
        product.setDescription(cursor.getString(ProductsTableSchema.colDESCRIPTION));

        return product;
    }
}
