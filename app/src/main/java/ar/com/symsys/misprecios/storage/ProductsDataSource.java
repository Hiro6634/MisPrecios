package ar.com.symsys.misprecios.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class ProductsDataSource {
    private ProductsSQLiteHelper        productsSQLiteHelper;
    private SQLiteDatabase              database = null;
    private Context                     context;

    public ProductsDataSource(Context context){
        this.context = context;
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
                values.put(ProductsTableSchema.CATEGORY, product.getCategory());
                values.put(ProductsTableSchema.DESCRIPTION, product.getDescription());
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
                    while (cursor.moveToFirst()) {
                        while (cursor.isAfterLast()) {
                            productList.add(readCursor(cursor));
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

        product.setProductId(cursor.getLong(ProductsTableSchema.colPRODUCT_ID));
        product.setBrandId(cursor.getInt(ProductsTableSchema.colBRAND_ID));
        product.setCategory(cursor.getString(ProductsTableSchema.colCATEGORY));
        product.setDescription(cursor.getString(ProductsTableSchema.colDESCRIPTION));

        return product;
    }
}
