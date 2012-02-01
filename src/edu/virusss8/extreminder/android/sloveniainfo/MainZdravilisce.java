package edu.virusss8.extreminder.android.sloveniainfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import edu.virusss8.extreminder.android.Aplikacija;
import edu.virusss8.extreminder.android.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainZdravilisce extends Activity {

	String parsanString = null;
	String[] opis = null, ostalo = null;
    private static final String NAMESPACE = "http://parser.virusss8.feri.edu";
    private static final String METHOD_NAME= "zdravilisca";
    private static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private ProgressDialog dialogWait;
	TextView head, body, info, link;
	ImageView pic;
	Aplikacija app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.zdravilisce_parse);
		app = (Aplikacija) getApplication();
		head = (TextView) findViewById(R.id.head);
		info = (TextView) findViewById(R.id.info);
		link = (TextView) findViewById(R.id.link);
		body = (TextView) findViewById(R.id.body);
		pic = (ImageView) findViewById(R.id.pic);
		
		BackgroundAsyncTask asi = new BackgroundAsyncTask();
        asi.execute();
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
    				ProgressDialog.show(MainZdravilisce.this, "", "Poteka razpoznavanje izbrane spletne strani...", true);
        }

        protected void onPostExecute(String arg) {
            dialogWait.cancel();
            doParse();
            head.setText(ostalo[0]);
            info.setText(ostalo[1] + "\n");
            link.setText(
                    Html.fromHtml(
                        "<b>Spletna stran:</b> " + "<a href=" + ostalo[3] + ">" + ostalo[0] + "</a>"));
            link.append("\n");
                link.setMovementMethod(LinkMovementMethod.getInstance());
            body.setText("");
            for(int i = 1; i < opis.length-1; i++)
            	body.append(opis[i] + "\n\n\n");
            drawImage();
        }
    }

    public String getData(){
        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("webAddress", app.getwZdr());
            request.addProperty("language", app.getLanguage());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://192.168.1.102:8888/SloveniaInfoParser/services/Parser?wsdl");
            androidHttpTransport.call(SOAP_ACTION,envelope);
            Object result = envelope.getResponse();
            
            //System.out.println("Result: " + result);

            return result.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return "napaka: "+e.toString();
        }
    }
    
    public void doParse() {
    	opis = parsanString.split("#");
    	ostalo = opis[opis.length-1].split("€");
    }
    
    public void drawImage() {
    	Drawable image = ImageOperations(this,"http://www.slovenia.info"+ostalo[2],"image.jpg");
        pic.setImageDrawable(image);
    }
    
    private Drawable ImageOperations(Context ctx, String url, String saveFilename) {
		try {
			InputStream is = (InputStream) this.fetch(url);
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object fetch(String address) throws MalformedURLException,IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}
}
