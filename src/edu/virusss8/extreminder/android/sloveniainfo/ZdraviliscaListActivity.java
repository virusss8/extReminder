package edu.virusss8.extreminder.android.sloveniainfo;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import edu.virusss8.extreminder.android.Aplikacija;
import edu.virusss8.extreminder.android.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ZdraviliscaListActivity extends ListActivity implements OnItemClickListener, OnItemLongClickListener {
	
	Aplikacija app;
	private ProgressDialog dialogWait;
    private static final String NAMESPACE = "http://parser.virusss8.feri.edu";
    private static final String METHOD_NAME= "seznamZdravilisc";
    private static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    String parsanString = null;
    List<String> gotZdravilisca = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);
		app = (Aplikacija) getApplication();
		
		BackgroundAsyncTask task = new BackgroundAsyncTask();
        task.execute();
	}
	
	public class BackgroundAsyncTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected String doInBackground(Void... params) {

            parsanString = getData();

            return parsanString;
        }

        @Override
        protected void onPreExecute() {
        	dialogWait = 
    				ProgressDialog.show(ZdraviliscaListActivity.this, "", "Pridobivanje seznama slovenskih naravnih zdravilišè...", true);
        }

        protected void onPostExecute(String arg) {
            dialogWait.cancel();
            doParse();
        }
    }

    public String getData(){
        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://192.168.1.102:8888/SloveniaInfoParser/services/Parser?wsdl");
            androidHttpTransport.call(SOAP_ACTION,envelope);
            Object result = envelope.getResponse();
            return result.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "napaka: "+e.toString();
        }
    }
    
    public void doParse() {
    	String[] polje;
		polje = parsanString.split("&nbsp;");
		for(int i = 0; i < polje.length; i++) {
			gotZdravilisca.add(polje[i]);
		}
		app.setSeznamZdr(gotZdravilisca);
		app.setAllList(polje.length - 1);
		setListAdapter(new ZdraviliscaListAdapter(this, R.layout.seznam_layout, gotZdravilisca));
		this.getListView().setOnItemClickListener(this); // POMEMBNO!!!
    }

//	@Override
	public void onItemClick(AdapterView<?> aView, View v, int position, long rowId) {
		app.setwZdr(position);
		Intent namen = new Intent(ZdraviliscaListActivity.this, MainZdravilisce.class);
		ZdraviliscaListActivity.this.startActivity(namen);
	}

//	@Override
	public boolean onItemLongClick(AdapterView<?> aView, View v, int position, long rowId) {
		return false;
	}
}
