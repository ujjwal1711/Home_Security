package security.com.home.app.homesecurity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Activity2 extends AppCompatActivity {
    private final static String URL="https://io.adafruit.com/api/v2/Devil927/feeds/img";
    private final static String URL2="https://api.thingspeak.com/update?api_key=0VK1SZAJ8C8WT76K&field1=0";
    private RequestQueue queue;
    ImageView imageview;
    private FirebaseAuth mAuth;
    Button unlockbt;
    Button lockbt;
    Button permission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        mAuth = FirebaseAuth.getInstance();
        queue= Volley.newRequestQueue(this);
        imageview = (ImageView) findViewById(R.id.img);
        unlockbt = (Button) findViewById(R.id.unlock);
        lockbt=(Button) findViewById(R.id.lock);
        permission=(Button) findViewById(R.id.dont);
        unlockbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url2="https://api.thingspeak.com/update?api_key=0VK1SZAJ8C8WT76K&field1="+"1";
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url2,new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
        lockbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url3="https://api.thingspeak.com/update?api_key=0VK1SZAJ8C8WT76K&field1=0";
                AsyncHttpClient client = new AsyncHttpClient();
               client.get(url3,new AsyncHttpResponseHandler() {
                   @Override
                   public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                   }

                   @Override
                   public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                   }
               });
            }

        });
        permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url4="https://api.thingspeak.com/update?api_key=0VK1SZAJ8C8WT76K&field1=3";
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url4,new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });

                getdata(URL);

    }
    private void getdata(String url)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try { //Log.d("data",response.getString("feeds").toString());
                    String encodedimage=response.getString("last_value").toString();
                    byte[] decodedString = Base64.decode(encodedimage, Base64.DEFAULT);
                    if(decodedString!=null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageview.setImageBitmap(bitmap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_signout) {

            mAuth.signOut();
            Toast.makeText(Activity2.this, "Signed out", Toast.LENGTH_LONG)
                    .show();
            startActivity(new Intent(Activity2.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
