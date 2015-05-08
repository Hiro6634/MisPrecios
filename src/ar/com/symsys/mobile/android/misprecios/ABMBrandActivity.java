package ar.com.symsys.mobile.android.misprecios;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.storage.StorageManager;

public class ABMBrandActivity extends Activity implements OnItemClickListener, android.view.View.OnClickListener{
	ListView	lvBrands;
	EditText	etNewBrand;
	Button		btnAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abm);
		StorageManager.getInstance().setContext(getApplicationContext());
		
		lvBrands 	= (ListView)findViewById(R.id.abm_listview);
		etNewBrand 	= (EditText)findViewById(R.id.abm_edittext);
		btnAdd		= (Button)findViewById(R.id.abm_add_button);
		
		etNewBrand.setText("");
		btnAdd.setOnClickListener(this);
		
	}

	@Override
	protected void onResume() {

		super.onResume();
		List<String> brands = StorageManager.getInstance().getAllBrands();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, brands){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView)view.findViewById(android.R.id.text1);
				
				textView.setTextColor(Color.BLACK);
				return view;
			}
			
		};
		lvBrands.setAdapter(adapter);
		lvBrands.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ListView listView = (ListView)parent;
		
		Toast.makeText(getApplicationContext(), listView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onClick(View view) {
		if( view.getId() == R.id.abm_add_button ){
			if(etNewBrand.length() > 0){
				StorageManager.getInstance().addBrand(etNewBrand.getText().toString());
				
				etNewBrand.setText("");
				finish(); 
			}
			else {
				Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
			}
		}
			
	}
}
