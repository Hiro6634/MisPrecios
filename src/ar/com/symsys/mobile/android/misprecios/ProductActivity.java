package ar.com.symsys.mobile.android.misprecios;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.storage.Product;
import ar.com.symsys.mobile.android.misprecios.storage.ProductsTableSchema;
import ar.com.symsys.mobile.android.misprecios.storage.StorageManager;

public class ProductActivity extends Activity implements OnItemSelectedListener, android.view.View.OnClickListener{
	private TextView  	tvProductId;
	private Spinner		spBrands;
	private Spinner		spCategories;
	private EditText	etDescription;
	private Button		btnOk;
	private Button		btnCancel;
	
	private int 		brandPos = 0;
	private int			categoryPos = 0;
	
	String	productId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);
		StorageManager.getInstance().setContext(getApplicationContext());
		
		tvProductId 	= (TextView)findViewById(R.id.product_id_textview);
		spBrands		= (Spinner)findViewById(R.id.add_product_brand_spinner);
		spCategories	= (Spinner)findViewById(R.id.add_product_category_spinner);
		etDescription	= (EditText)findViewById(R.id.add_product_description_edittext);
		btnOk			= (Button)findViewById(R.id.add_product_ok_btn);
		btnCancel		= (Button)findViewById(R.id.add_product_cancel_btn);
		
		Intent intent = getIntent();
		
		productId = intent.getStringExtra(ProductsTableSchema.PRODUCT_ID);
		tvProductId.setText(productId);
		etDescription.setText("");
		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_product, menu);
		return true;
	}

	@Override
	protected void onResume() {
		ArrayAdapter<String> adapter = null;
		
		//Populate Brands
		List<String> brands = StorageManager.getInstance().getAllBrands();
		brandPos = 0;
		if(brands.size() == 0)
			brands.add("");
		brands.add(getResources().getString(R.string.new_brand));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brands);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spBrands.setAdapter(adapter);
		spBrands.setOnItemSelectedListener(this);
		if(adapter.getCount() > 1){
			if(brands.get(0).length() > 0)
				brandPos = adapter.getCount()-2;
		}
		spBrands.setSelection(brandPos);
			
		//Populate Category
		List<String> categories = StorageManager.getInstance().getAllCategories();
		categoryPos = 0;
		if(categories.size() == 0)
			categories.add("");
		categories.add(getResources().getString(R.string.new_category));
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategories.setAdapter(adapter);
		spCategories.setOnItemSelectedListener(this);
		if(adapter.getCount() > 1){
			if(categories.get(0).length() > 0)
				categoryPos = adapter.getCount()-2;
		}
		spCategories.setSelection(categoryPos);

		// edit product
		Product product = StorageManager.getInstance().findProductById(productId);
		if( product != null )
		{
			int position;
			String storedBrand = StorageManager.getInstance().findBrandById(product.getBrandId());
			for( position = 0; position < spBrands.getCount(); position++){
				if(spBrands.getItemAtPosition(position).equals(storedBrand)){
					brandPos = position;
					spBrands.setSelection(position);
					break;
				}
			}
			
			String storedCategory = StorageManager.getInstance().findCategoryById(product.getCategoryId());
			for( position = 0; position < spCategories.getCount(); position++){
				if(spCategories.getItemAtPosition(position).equals(storedCategory)){
					categoryPos = position;
					spCategories.setSelection(position);
					break;
				}
			}
			
			etDescription.setText(product.getDescription());
		}
		
		
		
		super.onResume();
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Spinner spinner = (Spinner)parent;
		
		if(spinner.getId()== R.id.add_product_brand_spinner ){
			if(spinner.getItemAtPosition(position).toString()==getResources().getString(R.string.new_brand))
			{
				Intent intent = new Intent(getApplicationContext(), ABMBrandActivity.class);
				startActivity(intent);
			}
			else
				brandPos = position;
			
		} else if ( spinner.getId() == R.id.add_product_category_spinner) {
			if( spinner.getItemAtPosition(position).toString()==getResources().getString(R.string.new_category)){
				Intent intent = new Intent(getApplicationContext(), ABMCategoryActivity.class);
				startActivity(intent);
			}
			else
				categoryPos = position;
		}			
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View view) {
		if(view.getId()==R.id.add_product_ok_btn){
			Product product = new Product();
			product.setProductId(productId);

			//Valiate Description
			if( etDescription.length() == 0 ){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_miss_description), Toast.LENGTH_SHORT).show();
				
				etDescription.requestFocus();
				return;
			}
			product.setDescription(etDescription.getText().toString());

			if(spBrands.getSelectedItem().toString().length()==0){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_miss_brand), Toast.LENGTH_SHORT).show();
			
				spBrands.requestFocus();
				return;
			}
			
			product.setBrandId(StorageManager.getInstance().findBrandByName(spBrands.getSelectedItem().toString()));

			if( spCategories.getSelectedItem().toString().length()==0){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.warning_miss_category), Toast.LENGTH_SHORT).show();
				
				spCategories.requestFocus();
				return;
			}
			product.setCategoryId(StorageManager.getInstance().findCategoryByName(spCategories.getSelectedItem().toString()));
			
			if( StorageManager.getInstance().findProductById(productId)==null)
				StorageManager.getInstance().AddProduct(product);
			else
				StorageManager.getInstance().UpdateProduct(product);
		}
		finish();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("BRAND_POS", brandPos);
		outState.putInt("CATEGORY_POS", categoryPos);
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		brandPos = savedInstanceState.getInt("BRAND_POS");
		categoryPos = savedInstanceState.getInt("CATEGORY_POS");
		super.onRestoreInstanceState(savedInstanceState);
	}
}
