package login.test.com.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LoggedIn extends Activity {

    String Name,Age,Phone;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);



        tv = (TextView) findViewById(R.id.loggedin);

        Name = getIntent().getStringExtra("Name");
        Age = getIntent().getStringExtra("Age");
        Phone = getIntent().getStringExtra("Phone");

        String display = "The app is yet to design , Here is your info \n Name: " + Name + "\nAge: " + Age + "\nPhone: " + Phone;

        tv.setText(display);
    }
}
