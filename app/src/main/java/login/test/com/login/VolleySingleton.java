package login.test.com.login;

import android.app.DownloadManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Baymax on 12/16/2015.
 */
public class VolleySingleton {


    public static VolleySingleton sInstance = null;

    private RequestQueue mRequestQueue;

    private VolleySingleton(){

        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
    }

    public static VolleySingleton getInstance(){

        if(sInstance == null){

            sInstance = new VolleySingleton();
        }
            return sInstance;

    }

    public RequestQueue getRequestQueue(){

        return mRequestQueue;
    }
}
