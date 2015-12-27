/*
 * Copyright 2015 Pratik Kate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.katepratik.msg91;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pratik on 19-12-2015.
 */

public class SendTask extends AsyncTask<String, Void, String> {
    SendResult callback;

    public SendTask(SendResult callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            URLConnection myURLConnection=null;
            URL myURL=null;
            BufferedReader reader=null;
            //prepare connection
            myURL = new URL(urls[0]);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            String response;
            //reading response
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", "" + response);
            //finally close connection
            reader.close();
            return response;


        } catch (IOException e) {
            return "Unable to download the requested page.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String response) {
            callback.onResult(response);
    }
}
