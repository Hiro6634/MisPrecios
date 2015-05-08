package ar.com.symsys.mobile.android.misprecios.storage;

import android.content.Context;
import android.text.Editable;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hsuyama on 10/03/2015.
 */
public class StorageManager {
    private static StorageManager  ourInstance 		 = new StorageManager();
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MM yyyy");

    private ProductsDataSource      productsDataSource = null;
    private PricesDataSource        pricesDataSource = null;
    private MarketsDataSource       marketsDataSource = null;
    private BrandsDataSource		brandDataSource = null;
    private CategoriesDataSource	categoriesDataSource = null;

    public static StorageManager getInstance() {
        return ourInstance;
    }

    private StorageManager() {
    }

    public void setContext(Context context){
        synchronized (this){
        	if( productsDataSource == null)
        		productsDataSource  	= new ProductsDataSource(context);
        	if( pricesDataSource == null)
        		pricesDataSource    	= new PricesDataSource(context);
        	if( marketsDataSource == null)
        		marketsDataSource   	= new MarketsDataSource(context);
        	if( brandDataSource == null)
        		brandDataSource			= new BrandsDataSource(context);
        	if( categoriesDataSource == null)
        		categoriesDataSource	= new CategoriesDataSource(context);
        }
    }

    //  Product Queries
    public List<Product> getAllProducts(){
        return productsDataSource.getAllProducts();
    }

    public void AddProduct(Product product){
        productsDataSource.addProduct(product);
    }

    public Product findProductById( String productId ){
        return productsDataSource.findProductById(productId);
    }

	public void UpdateProduct(Product product) {
		productsDataSource.updateProduct(product);
		
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
    public List<String> getAllMarketsStr(){
        return marketsDataSource.getAllMarketsStr();
    }

    public List<Market> getAllMarkets(){
        return marketsDataSource.getAllMarkets();
    }

    public void AddMarket(Market market){
        marketsDataSource.addMarket(market);
    }

    public Market findMarket( int marketId ){
        return marketsDataSource.findMarket(marketId);
    }

	public int findMarketByName(String market) {
		
		return marketsDataSource.findMarketByName(market);
	}
    // Brand Queries
    
    public List<String> getAllBrands(){
    	return brandDataSource.getAllBrands();
    }

    public void addBrand(String brandName ) {
		brandDataSource.addBrand(brandName);
		
	}
    
    public int findBrandByName(String brandName){
    	return brandDataSource.findBrandByName(brandName);
    }

	public String findBrandById(int brandId) {

		return brandDataSource.findBrandById(brandId);
	}

    // Category Queries
    public List<String> getAllCategories(){
    	return categoriesDataSource.getAllCategories();
    }

	public void addCategory(String category) {
		categoriesDataSource.addCategory(category);
	}
	
	public int findCategoryByName(String categoryName){
		return categoriesDataSource.findCategoryByName(categoryName);
	}
	
	public String findCategoryById( int categoryId ){
		return categoriesDataSource.findCategoryById(categoryId);
	}
}
