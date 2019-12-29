package com.example.fleetmanager;


 import android.app.Activity;



    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.toolbox.Volley;


/*
 * code by gurman and Rohit*/
    public class AppControllerActivity {

        private RequestQueue mRequestQueue;
        private Activity c;


        private static AppControllerActivity mInstance;

        public AppControllerActivity(Activity c)
        {
            mInstance = this;
            this.c = c;

        }

        public static synchronized AppControllerActivity getInstance()
        {
            return mInstance;
        }

        public RequestQueue getRequestQueue() {
            if (mRequestQueue == null)
            {
                mRequestQueue = Volley.newRequestQueue(c);
            }

            return mRequestQueue;
        }



        public  void addToRequestQueue(Request req) {

            getRequestQueue().add(req);
        }


    }