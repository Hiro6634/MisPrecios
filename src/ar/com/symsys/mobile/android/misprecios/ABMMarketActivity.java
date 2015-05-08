package ar.com.symsys.mobile.android.misprecios;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ar.com.symsys.mobile.android.misprecios.storage.Market;
import ar.com.symsys.mobile.android.misprecios.storage.StorageManager;

public class ABMMarketActivity extends Activity implements OnClickListener{

	private ListView 				lvMarkets;
	private EditText 				etNewMarket;
	private EditText				etNewLocation;
	private Button	 				btnAdd;
	private ArrayAdapter<String> 	adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abmmarket);
		
		StorageManager.getInstance().setContext(getApplicationContext()); 
		
		lvMarkets	 	= (ListView)findViewById(R.id.abm_listview);
		etNewMarket		= (EditText)findViewById(R.id.abm_edittext);
		etNewLocation	= (EditText)findViewById(R.id.abm_edittext2);
		btnAdd			= (Button)findViewById(R.id.abm_add_button);
		
		btnAdd.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.abmmarket, menu);
		return true;
	}

	@Override
	protected void onResume() {
		List<String> markets = StorageManager.getInstance().getAllMarketsStr();
		adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, markets){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = super.getView(position, convertView, parent);
				TextView textView = (TextView)view.findViewById(android.R.id.text1);
				
				textView.setTextColor(Color.BLACK);
				return view;
			}
			
		};
		lvMarkets.setAdapter(adapter);
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
			if(etNewMarket.length() == 0){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.abm_market_missing_market), Toast.LENGTH_SHORT).show();
				return;
			}
				
			if(etNewLocation.length() == 0){
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.abm_market_missing_location), Toast.LENGTH_SHORT).show();
				return;
			}
			Market market = new Market();
			market.setName(etNewMarket.getText().toString());
			market.setLocation(etNewLocation.getText().toString());
			StorageManager.getInstance().AddMarket(market);
				
			etNewMarket.setText("");
			etNewLocation.setText("");	
			finish(); 
		}
	}
}
