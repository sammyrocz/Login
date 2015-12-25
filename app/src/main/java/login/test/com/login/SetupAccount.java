package login.test.com.login;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SetupAccount extends AppCompatActivity implements View.OnClickListener {

    RequestQueue queue;
    com.beardedhen.androidbootstrap.BootstrapEditText Name, Age, Phone, Email, Pass;
    com.beardedhen.androidbootstrap.BootstrapButton Create;
    android.support.design.widget.TextInputLayout ErrorPhone, ErrorPass, ErrorEmail;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_account);

        queue = VolleySingleton.getInstance().getRequestQueue();

        c = this;
        Name = (BootstrapEditText) findViewById(R.id.edName);
        Age = (BootstrapEditText) findViewById(R.id.edAge);
        Phone = (BootstrapEditText) findViewById(R.id.edPhone);
        Email = (BootstrapEditText) findViewById(R.id.edEmail);
        Pass = (BootstrapEditText) findViewById(R.id.edPass);
        Create = (BootstrapButton) findViewById(R.id.btncreate);
        ErrorPhone = (TextInputLayout) Phone.getParent();
        ErrorPass = (TextInputLayout) Pass.getParent();
        ErrorEmail = (TextInputLayout) Email.getParent();


        Create.setOnClickListener(this);


    }

    private void generateError(TextInputLayout t, String Message) {

        t.setErrorEnabled(true);
        t.setError(Message);

    }


    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private void parseData(String name, String age, String phone, String email, String pass) {


        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("age", age);
        params.put("phone", phone);
        params.put("email", email);
        params.put("password", pass);


        String url = "http://dexesnotes.net84.net/insert.php";
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    String data = response.getString("inserted");
                    if (data.equals("1")) {

                        Toast.makeText(c, "CREATED", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(c, "ALREADY REGISTRED", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(c, "Error in json", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


        jsObjRequest.setTag("SetupAccount");
        queue.add(jsObjRequest);

    }

    @Override
    public void onClick(View v) {

        int counter = 0;

        String name, age, phone, email, pass;
        name = Name.getText().toString();
        age = Age.getText().toString();
        phone = Phone.getText().toString();
        email = Email.getText().toString();
        pass = Pass.getText().toString();


        if (phone.length() != 10)
            generateError(ErrorPhone, "Enter Valid Phone No.");

        else {
            counter++;
            ErrorPhone.setErrorEnabled(false);
        }

        if (pass.length() < 8) {
            generateError(ErrorPass, "Min 8 Characters");
        } else {
            counter++;
            ErrorPass.setErrorEnabled(false);
        }

        if (!isValidEmail(email)) {

            generateError(ErrorEmail, "Enter Valid Email");
        } else {
            counter++;
            ErrorEmail.setErrorEnabled(false);
        }


        if (name.length() > 0 && age.length() > 0 && counter == 3) {


            Toast.makeText(c, "REGISTERTING ACCOUNT", Toast.LENGTH_SHORT).show();
            parseData(name, age, phone, email, pass);

        } else {

            Toast.makeText(c, "FILL OUT MISSING DETAILS", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        queue.cancelAll("SetupAccount");
    }
}
