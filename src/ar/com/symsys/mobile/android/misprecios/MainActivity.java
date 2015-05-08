package ar.com.symsys.mobile.android.misprecios;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.storage.Market;
import ar.com.symsys.mobile.android.misprecios.storage.Price;
import ar.com.symsys.mobile.android.misprecios.storage.PricesTableSchema;
import ar.com.symsys.mobile.android.misprecios.storage.Product;
import ar.com.symsys.mobile.android.misprecios.storage.ProductsTableSchema;
import ar.com.symsys.mobile.android.misprecios.storage.StorageManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends Activity implements OnClickListener{
	
	private static final int ST_IDLE 				= 0;
	private static final int ST_ASK_ADD_PRDUCT 		= 1;
	private static final int ST_ADD_PRDUCT 			= 2;
	private static final int ST_ASK_ADD_PRICE 		= 3;
	private static final int ST_ADD_PRICE 			= 4;
	private static final int ST_SHOW_PRICES 		= 5;
	private static final int ST_EDIT_PRODUCT 		= 6;

	
	
	
	protected TextView  tvProductInfo;
	protected ListView  lvProductPrices;
	protected Button	btnScan;
	protected String	productId;
	protected int		state;
	protected Intent 	intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		state = ST_IDLE;
			
		StorageManager.getInstance().setContext(getApplicationContext()); 
		
		tvProductInfo 	= (TextView)findViewById(R.id.product_info_textview);
		lvProductPrices	= (ListView)findViewById(R.id.product_info_listview);
		btnScan			= (Button)findViewById(R.id.scan_button);
		productId		= "";

		tvProductInfo.setText("");
		btnScan.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_add:
				if( !productId.equals("")){
					state = ST_ADD_PRICE;
					this.onResume();
				}
				return true;
			case R.id.action_edit:
				if(!productId.equals("")){
					state = ST_EDIT_PRODUCT;
					this.onResume();
				}
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		try{
			super.onResume();
			switch(state){
			case ST_IDLE:
				break;
			case ST_ASK_ADD_PRDUCT:
				ShowAddProductDialog();
				break;
				
			case ST_ADD_PRDUCT:
				intent = new Intent( getApplicationContext(), ProductActivity.class);
				intent.putExtra(ProductsTableSchema.PRODUCT_ID, productId);
				startActivity(intent);
				state = ST_ASK_ADD_PRICE;
				break;
				
			case ST_ASK_ADD_PRICE:
				 
				ShowAddPriceDialog(StorageManager.getInstance().findProductById(productId));
				break;
			case ST_ADD_PRICE:	
				intent = new Intent( getApplicationContext(), AddPriceActivity.class);
				intent.putExtra(ProductsTableSchema.PRODUCT_ID, productId);
				startActivity(intent);
				state = ST_SHOW_PRICES;
				break;
	
			case ST_SHOW_PRICES:
				if( !productId.equals(""))
					ShowPrices( productId );
				break;
				
			case ST_EDIT_PRODUCT:
				if( !productId.equals("")){
					intent = new Intent( getApplicationContext(), ProductActivity.class);
					intent.putExtra(ProductsTableSchema.PRODUCT_ID, productId);
					startActivity(intent);
					
					state = ST_SHOW_PRICES;
				}
				else
					state = ST_IDLE;
				break;
			default: 
				state = ST_IDLE;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			state = ST_IDLE;
		}
	}

	private void ShowPrices(String productId) {
		Product product = StorageManager.getInstance().findProductById(productId);
		if( product != null){
			String[] pricesFrom = new String[]{
				PricesTableSchema.BULK_PRICE,
				PricesTableSchema.BULK_QUANTITY,
				PricesTableSchema.TIMESTAMP,
				PricesTableSchema.MARKET_ID
			};
			List<Price> prices = StorageManager.getInstance().findPriceByProductId(productId);
			List<HashMap<String, String>> pricesList = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(pricesFrom[0], "Precio");
			map.put(pricesFrom[1], "#");
			map.put(pricesFrom[2], "Fecha");
			map.put(pricesFrom[3], "Comercio");
			pricesList.add(map);
			for( Price price : prices){
				map = new HashMap<String, String>();
				map.put(pricesFrom[0], "$" + String.valueOf(price.getBulkPrice()));
				map.put(pricesFrom[1], String.valueOf(price.getBulkQuantity()));
				map.put(pricesFrom[2], StorageManager.DATE_FORMAT.format(price.getTimeStamp()));
				map.put(pricesFrom[3], StorageManager.getInstance().findMarket(price.getMarketId()).getName() + ", " +
						StorageManager.getInstance().findMarket(price.getMarketId()).getLocation());
				pricesList.add(map);
			}
			int[]  pricesTo = new int[]{R.id.price, R.id.quantity, R.id.date, R.id.market_name};
			SimpleAdapter pricesAdapter = new SimpleAdapter(
					this, 
					pricesList, 
					R.layout.price_list_item_layout, 
					pricesFrom,
					pricesTo);
			
			lvProductPrices.setAdapter(pricesAdapter);
			
			tvProductInfo.setText(product.getDescription() + " " + StorageManager.getInstance().findBrandById(product.getBrandId()));
		}
		else
			Toast.makeText(getApplicationContext(), "nothing to view", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putString("PRODUCT_ID", productId);
		outState.putInt("APP_STATE", state);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		productId = savedInstanceState.getString("PRODUCT_ID");
		state = savedInstanceState.getInt("APP_STATE");
	}


	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.scan_button:
				IntentIntegrator scanIntegrator = new IntentIntegrator(this);
				scanIntegrator.initiateScan();
			break;
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent){
		IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if( scanningResult != null){
			Product product;
			productId = scanningResult.getContents();
			if( productId != null){
				product = StorageManager.getInstance().findProductById(productId);
				if (product == null)
					state = ST_ASK_ADD_PRDUCT;
				else{
					List<Price> prices = StorageManager.getInstance().findPriceByProductId(productId);
					
					if(prices.size() == 0)
						state = ST_ASK_ADD_PRICE;
					else
						state = ST_SHOW_PRICES;
				}
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "No scan data retrived!", Toast.LENGTH_SHORT).show();
		}
	}

	protected void ShowAddProductDialog(){
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if( which == AlertDialog.BUTTON_POSITIVE)
					state = ST_ADD_PRDUCT;
				else
					state = ST_IDLE;
				dialog.dismiss();
				MainActivity.this.onResume();
			}
		};
		
		AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
		alert.setTitle(R.string.add_product_dialog_title);
		alert.setMessage(String.format(getResources().getString(R.string.add_product_dialog_message),productId));
		alert.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.btn_cancel) , onClickListener);
		alert.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.btn_ok) , onClickListener);
		alert.show();
	}

	private void ShowAddPriceDialog(Product product) {
		DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if( which == AlertDialog.BUTTON_POSITIVE)
					state = ST_ADD_PRICE;
				else
					state = ST_IDLE;
				dialog.dismiss();
				MainActivity.this.onResume();
			}
		};
		
		AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
		alert.setTitle(R.string.add_price_dialog_title);
		alert.setMessage(String.format(getResources().getString(R.string.add_price_dialog_message), product.getDescription(), StorageManager.getInstance().findBrandById(product.getBrandId())));
		alert.setButton(AlertDialog.BUTTON_NEGATIVE, getResources().getString(R.string.btn_cancel) , onClickListener);
		alert.setButton(AlertDialog.BUTTON_POSITIVE, getResources().getString(R.string.btn_ok) , onClickListener);
		alert.show();
	}
}
