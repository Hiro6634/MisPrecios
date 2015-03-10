package ar.com.symsys.misprecios;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ar.com.symsys.misprecios.storage.StorageManager;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private Button      btnButton;
    private TextView    tvFormat, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageManager.getInstance().setContext(getApplicationContext());

        btnButton = (Button)findViewById(R.id.scan_button);
        tvContent = (TextView)findViewById(R.id.scan_content);
        tvFormat = (TextView)findViewById(R.id.scan_format);

        btnButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


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

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent );

        if(scanningResult != null){
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            tvFormat.setText("FORMAT: " + scanFormat);
            tvContent.setText("CONTENT: " + scanContent);
        }
        else{
            Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT).show();
        }
    }

}
