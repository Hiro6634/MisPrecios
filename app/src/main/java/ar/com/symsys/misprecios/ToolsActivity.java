package ar.com.symsys.misprecios;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ToolsActivity extends FragmentActivity{
    public static final String PRODUCT_ID = "ProductId";
    public static final String ACTION = "Action";
    public static final String ADD_PRODUCT = "AddProduct";
    public static final String ADD_PRICE = "AddPrice";
    private String productId="";


    public String getAraca() {
        return araca;
    }

    private String araca;

    public String getProductId() {
        return productId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        Intent intent = getIntent();
        productId = intent.getStringExtra(PRODUCT_ID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tools, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
