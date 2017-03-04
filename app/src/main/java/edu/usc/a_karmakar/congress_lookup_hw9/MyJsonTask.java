package edu.usc.a_karmakar.congress_lookup_hw9;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by abhishek-karmakar on 11/17/2016.
 */

public class MyJsonTask extends AsyncTask<URL, Void, String> {
        private HttpURLConnection urlConnection;
        private String res = "";
        private int responseCode;

        public interface AsyncResponse {
            void processFinish(String output);
        }

        private AsyncResponse delegate = null;

        public MyJsonTask(AsyncResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(URL... urls) {
                try {
                    urlConnection = (HttpURLConnection) urls[0].openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    res += readStream(in) + "";
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            delegate.processFinish(result);

        }

        private String readStream (InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            String line;

            while((line = r.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            return sb.toString();
        }
    }