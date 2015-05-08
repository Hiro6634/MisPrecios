package ar.com.symsys.mobile.android.misprecios;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.storage.StorageManager;

public class ABMCategoryActivity extends Activity implements android.view.View.OnClickListener{

	private ListView 				lvCategories;
	private EditText 				etNewCategory;
	private Button	 				btnAdd;
	private ArrayAdapter<String> 	adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abm);
	
		StorageManager.getInstance().setContext(getApplicationContext()); 
		
		lvCategories 	= (ListView)findViewById(R.id.abm_listview);
		etNewCategory	= (EditText)findViewById(R.id.abm_edittext);
		btnAdd			= (Button)findViewById(R.id.abm_add_button);
		
		etNewCategory.setText("");
		btnAdd.setOnClickListener(this);
	}
	
	
	@Override
	protected void onResume() {
		List<String> categories = StorageManager.getInstance().getAllCategories();
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, categories){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView)view.findViewById(android.R.id.text1);
				
				textView.setTextColor(Color.BLACK);
				return view;
			}
			
		};
		lvCategories.setAdapter(adapter);
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
	public void onClick(View view) {
		if( view.getId() == R.id.abm_add_button ){
			if(etNewCategory.length() > 0){
				StorageManager.getInstance().addCategory(etNewCategory.getText().toString());
				
				etNewCategory.setText("");
				finish(); 
			}
			else {
				Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
