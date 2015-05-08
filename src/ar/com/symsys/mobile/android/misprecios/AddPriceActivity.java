package ar.com.symsys.mobile.android.misprecios;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.storage.Price;
import ar.com.symsys.mobile.android.misprecios.storage.Product;
import ar.com.symsys.mobile.android.misprecios.storage.ProductsTableSchema;
import ar.com.symsys.mobile.android.misprecios.storage.StorageManager;

public class AddPriceActivity extends Activity implements OnClickListener{
	protected TextView 		tvProductDescription;
	protected EditText 		etTotalPrice;
	protected EditText 		etQuantity;
	protected EditText 		etPricePerUnit;
	protected Spinner  		spMarket;
	protected TextView		tvDate;
	protected Button		btnAccept;
	protected Button		btnCancel;
	protected String		productId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_price);
		
		tvProductDescription 	= (TextView)findViewById(R.id.add_price_product_description);
		etTotalPrice			= (EditText)findViewById(R.id.add_price_total_price_edittext);
		etQuantity				= (EditText)findViewById(R.id.add_price_quantity_edittext);
		etPricePerUnit			= (EditText)findViewById(R.id.add_price_price_per_unit_edittext);
		spMarket				= (Spinner)findViewById(R.id.add_price_market_spinner);
		tvDate					= (TextView)findViewById(R.id.add_price_date_textview);
		btnAccept				= (Button)findViewById(R.id.add_price_accept_button);
		btnCancel				= (Button)findViewById(R.id.add_price_cancel_button);
		
		Intent intent = getIntent();
		productId = intent.getStringExtra(ProductsTableSchema.PRODUCT_ID);
		
		btnAccept.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		etTotalPrice.setText("");
		etQuantity.setText("1");
		etPricePerUnit.setText("");
		
		etPricePerUnit.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				int quantity = Integer.parseInt(etQuantity.getText().toString());
				if( s.length() > 0 ){
					Float totalPrice = Float.parseFloat(s.toString()) * quantity;
					etTotalPrice.setText(String.format("% .2f", totalPrice));
				}
			}
		});
		
		etQuantity.addTextChangedListener( new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if( s.length() > 0 ){
					if( Integer.parseInt(s.toString()) > 0){
						Float totalPrice = Float.parseFloat(etPricePerUnit.getText().toString())*Integer.parseInt(s.toString());
						etTotalPrice.setText(String.format("% .2f", totalPrice));
					}
				}
			}
		});
	}

	
	@Override
	protected void onResume() {
		Product product = StorageManager.getInstance().findProductById(productId);
		tvProductDescription.setText(product.getDescription() + ", " + StorageManager.getInstance().findBrandById(product.getBrandId()));
		
		List<String> markets = StorageManager.getInstance().getAllMarketsStr();
		if(markets.size() == 0)
			markets.add("");
		markets.add(getResources().getString(R.string.new_market));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, markets);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spMarket.setAdapter(adapter);
		spMarket.setOnItemSelectedListener( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				Spinner spinner = (Spinner)parent;
				if(spinner.getItemAtPosition(position).toString()==getResources().getString(R.string.new_market))
				{
					Intent intent = new Intent(getApplicationContext(), ABMMarketActivity.class);
					startActivity(intent);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		if( adapter.getCount() > 1){
			if(markets.get(0).length() > 0)
				spMarket.setSelection(adapter.getCount()-2);
		}
		
		tvDate.setText(StorageManager.DATE_FORMAT.format(Calendar.getInstance().getTime()));
		tvDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Set Date", Toast.LENGTH_SHORT).show();
			}
		});
		super.onResume();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_price, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onClick(View view) {
		if( view.getId() == R.id.add_price_accept_button){
			if( etPricePerUnit.length() == 0){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_price_missing_price), Toast.LENGTH_SHORT).show();
				return;
			}
			if( etQuantity.length() == 0){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_price_missing_quantity), Toast.LENGTH_SHORT).show();
				return;
			}
			if( spMarket.getSelectedItem().toString().length() == 0 ){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.add_price_missing_market), Toast.LENGTH_SHORT).show();
				return;
			}
			
			Price price = new Price();
			price.setBulkPrice(Float.parseFloat(etPricePerUnit.getText().toString()));
			price.setBulkQuantity(Integer.parseInt(etQuantity.getText().toString()));
			price.setMarketId(StorageManager.getInstance().findMarketByName(spMarket.getSelectedItem().toString()));
			price.setProductId(productId);
			
			String date = tvDate.getText().toString();
			try {
				price.setTimeStamp(StorageManager.DATE_FORMAT.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			StorageManager.getInstance().AddPrice(price);
		}
		finish();
		
	}
}
