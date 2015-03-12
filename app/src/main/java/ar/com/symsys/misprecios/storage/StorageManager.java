package ar.com.symsys.misprecios.storage;

import android.content.Context;

import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class StorageManager {
    private static StorageManager ourInstance = new StorageManager();
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private ProductsDataSource      productsDataSource;
    private PricesDataSource        pricesDataSource;
    private MarketsDataSource       marketsDataSource;

    public static StorageManager getInstance() {
        return ourInstance;
    }

    private StorageManager() {
    }

    public void setContext(Context context){
        synchronized (this){
            productsDataSource  = new ProductsDataSource(context);
            pricesDataSource    = new PricesDataSource(context);
            marketsDataSource   = new MarketsDataSource(context);
        }
    }

    //  Product Queries
    public List<Product> getAllProducts(){
        return productsDataSource.getAllProducts();
    }

    public void AddProduct(Product product){
        productsDataSource.addProduct(product);
    }

    // Price Queries
    public List<Price> getAllPrices(){
        return pricesDataSource.getAllPrices();
    }

    public void AddPrice(Price price){
        pricesDataSource.addPrice(price);
    }

    public List<Price> findPriceByProductId( String productId ){
        return pricesDataSource.findPriceByProductId(productId);
    }

    // Market Queries
    public List<Market> getAllMarkets(){
        return marketsDataSource.getAllMarkets();
    }

    public void AddMarket(Market market){
        marketsDataSource.addMarket(market);
    }
}
