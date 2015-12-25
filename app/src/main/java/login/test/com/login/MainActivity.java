package login.test.com.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.android.gms.common.SignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends Activity {


    RequestQueue queue;
    TextView info;
    EditText phone, password;
    BootstrapButton login, new_user;
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        queue = VolleySingleton.getInstance().getRequestQueue();

        signin = (Button) findViewById(R.id.googlesignin);
        info = (TextView) findViewById(R.id.info);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        login = (BootstrapButton) findViewById(R.id.btnlogin);
        new_user = (BootstrapButton) findViewById(R.id.new_user);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ph = phone.getText().toString();
                String pass = password.getText().toString();

                if (ph.length() > 9 && pass.length() > 0) {

                    parseData(ph, pass);
                } else {

                    Toast.makeText(getApplicationContext(), "Enter correct Details", Toast.LENGTH_SHORT).show();

                }
            }
        });

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, SetupAccount.class);
                startActivity(myIntent);
            }

        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, GoogleLogin.class);
                startActivity(myIntent);


            }
        });


    }

    private void parseData(String ph, String pass) {


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", ph);
        params.put("password", pass);

        String url = "http://dexesnotes.net84.net/login.php";
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    String data = response.getString("login");
                    if (data.equals("data found")) {

                        Intent myIntent = new Intent(MainActivity.this, LoggedIn.class);
                        myIntent.putExtra("Name", response.getString("Name"));
                        myIntent.putExtra("Age", response.getString("Age"));
                        myIntent.putExtra("Phone", response.getString("Phone"));
                        startActivity(myIntent);


                    } else {

                        Toast.makeText(getApplicationContext(), "YOU ARE NOT REGISTERED", Toast.LENGTH_SHORT).show();
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

        jsObjRequest.setTag("Login");
        queue.add(jsObjRequest);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (queue != null) {

            queue.cancelAll("Login");
        }
    }
}

