package login.test.com.login;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class GoogleLogin extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private static final int RC_SIGN_IN = 0;

    private GoogleApiClient mGoogleApiClient;

    private boolean mIntentInProgress;

    private boolean mShouldResolve;

    private ConnectionResult connectionResult;

   //com.beardedhen.androidbootstrap.BootstrapEditText Phone, Password, ConfirmPassword;
    //com.beardedhen.androidbootstrap.BootstrapButton create;
    //TextInputLayout ErrorPhone, ErrorPassword, ErrorConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

//        refrencing();


  //      create.setOnClickListener(this);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person person = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = person.getDisplayName();


                Toast.makeText(getApplicationContext(),
                        "You are Logged In " + personName, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Couldnt Get the Person Info", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {


        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {

            connectionResult = result;

            if (mShouldResolve) {

                resolveSignInError();
            }
        }


    }



/*
    private void refrencing() {

        Phone = (BootstrapEditText) findViewById(R.id.google_sign_in_phone);
        Password = (BootstrapEditText) findViewById(R.id.google_sign_in_password);
        ConfirmPassword = (BootstrapEditText) findViewById(R.id.google_sign_in_confirm_password);
        create = (BootstrapButton) findViewById(R.id.google_sign_in_create);

        ErrorPhone = (TextInputLayout) Phone.getParent();
        ErrorPassword = (TextInputLayout) Password.getParent();
        ErrorConfirmPassword = (TextInputLayout) ConfirmPassword.getParent();

    }


    private void generateError(TextInputLayout t, String Message) {

        t.setErrorEnabled(true);
        t.setError(Message);

    }

    @Override
    public void onClick(View v) {


        int counter = 0;


        String phone, pass, confirmpassword;

        phone = Phone.getText().toString();
        pass = Password.getText().toString();
        confirmpassword = ConfirmPassword.getText().toString();


        if (phone.length() != 10)
            generateError(ErrorPhone, "Enter Valid Phone No.");

        else {
            counter++;
            ErrorPhone.setErrorEnabled(false);
        }

        if (pass.length() < 8) {
            generateError(ErrorPassword, "Min 8 Characters");
        } else {
            counter++;
            ErrorPassword.setErrorEnabled(false);
        }

        if (!confirmpassword.equals(pass)) {


            generateError(ErrorConfirmPassword, "Password Doesnt Match");
        } else {

            if (pass.length() > 8)
                counter++;
            ErrorConfirmPassword.setErrorEnabled(false);
        }


        if (pass.length() > 0 && counter >= 3) {


            Toast.makeText(getBaseContext(), "REGISTERTING ACCOUNT", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(getBaseContext(), "FILL OUT MISSING DETAILS", Toast.LENGTH_SHORT).show();
        }

    }

*/
}
