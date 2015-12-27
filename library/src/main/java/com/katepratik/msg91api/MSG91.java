
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


package com.katepratik.msg91api;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Pratik on 19-12-2015.
 */

public class MSG91 extends AsyncTask<String, Void, String> {

    String mainUrl = "http://api.msg91.com/api/postsms.php?data=";
    String authKey, textMessage, senderId, singleNumber, msgSchedule, smsRoute, smsCampaign, smsCountryCode;
    ArrayList<String> multiNumber;
    boolean isFlash = false, isUnicode = false, debugMode = false;
    String debugTAG = "MSG91";
    String response;


    public MSG91(String key) {
        authKey = key;
    }

    public MSG91(String key, boolean debug) {
        authKey = key;
        debugMode = debug;
    }

    public void composeMessage(String id, String message) {
        senderId = id;
        textMessage = message;
    }


    public void to(String mobile) {
        singleNumber = mobile;
    }

    public void to(ArrayList<String> mobiles) {
        multiNumber = mobiles;
    }

    public void setSchedule(String dateTime) {
        msgSchedule = dateTime;
    }

    public void flash(boolean trueORfalse) {
        isFlash = trueORfalse;
    }

    public void unicode(boolean trueORfalse) {
        isUnicode = trueORfalse;
    }

    public void setRoute(String route) {
        smsRoute = route;
    }

    public void setCampaign(String campaign) {
        smsCampaign = campaign;
    }

    public void setCountryCode(String code) {
        smsCountryCode = code;
    }

    public String getXML() {
        StringBuilder xmlData = new StringBuilder("<MESSAGE>");
        xmlData.append("<AUTHKEY>").append(authKey).append("</AUTHKEY>");

        // ROUTE
        if (smsRoute != null)
            xmlData.append("<ROUTE>").append(smsRoute).append("</ROUTE>");

        // CAMPAIGN
        if (smsCampaign != null)
            xmlData.append("<CAMPAIGN>").append(smsCampaign).append("</CAMPAIGN>");

        // COUNTRY
        if (smsCountryCode != null)
            xmlData.append("<COUNTRY>").append(smsCountryCode).append("</COUNTRY>");

        // SENDER ID : Cannot be Null
        if (senderId != null)
            xmlData.append("<SENDER>").append(senderId).append("</SENDER>");
        else
            return "ERROR : Sender ID is Missing";

        // SCHEDULE DATE TIME
        if (msgSchedule != null)
            xmlData.append("<SCHEDULEDATETIME>").append(msgSchedule).append("</SCHEDULEDATETIME>");

        //FLASH
        if (isFlash)
            xmlData.append("<FLASH>").append("1").append("</FLASH>");

        //UNICODE
        if (isUnicode)
            xmlData.append("<UNICODE>").append("1").append("</UNICODE>");

        // TEXT : Cannot be Null
        if (textMessage != null)
            xmlData.append("<SMS TEXT=\"").append(textMessage).append("\">");
        else
            return "ERROR : Text Message is Missing";

        // TO : Cannot be null
        if (singleNumber != null || multiNumber != null) {
            if (singleNumber != null) {
                if (singleNumber.length() == 10)
                    xmlData.append("<ADDRESS TO=\"").append(singleNumber).append("\"></ADDRESS>");
                else
                    return "ERROR : " + singleNumber + " is not a valid Mobile Number";
            }
            if (multiNumber != null) {
                for (int i = 0; i < multiNumber.size(); i++) {
                    String mobileNumber = multiNumber.get(i);
                    if (mobileNumber.length() == 10)
                        xmlData.append("<ADDRESS TO=\"").append(mobileNumber).append("\"></ADDRESS>");
                    else
                        return "ERROR : " + mobileNumber + " is not a valid Mobile Number";
                }
            }

            xmlData.append("</SMS>").append("</MESSAGE>");
        } else
            return "ERROR : Mobile Number(s) Missing";

        String finalLink = null;
        try {
            finalLink = mainUrl + URLEncoder.encode(xmlData.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return finalLink;
    }

    public String send() {
        try {
            return execute("SEND").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Exception: " + e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Exception: " + e;
        }
    }

    public String getBalance(String route) {
        try {
            return new MSG91(authKey, debugMode).execute("BALANCE", route).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Exception: " + e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Exception: " + e;
        }
    }

    public String changePassword(String oldPass, String newPass) {
        try {
            return new MSG91(authKey, debugMode).execute("PASSWORD", oldPass, newPass).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Exception: " + e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Exception: " + e;
        }
    }

    public String validate() {
        try {
            return new MSG91(authKey, debugMode).execute("VALIDATE").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Exception: " + e;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return "Exception: " + e;
        }
    }


    @Override
    protected String doInBackground(String... params) {
        try {

            URLConnection myURLConnection;
            URL myURL = null;
            BufferedReader reader;


            String command = params[0];
            switch (command) {
                case "SEND":
                    String url = getXML();
                    if (!url.contains("ERROR")) {
                        myURL = new URL(url);
                    } else {
                        return url;
                    }
                    break;
                case "BALANCE":
                    // URL syntax : http://api.msg91.com/api/balance.php?authkey=YourAuthKey&type=1
                    myURL = new URL("http://api.msg91.com/api/balance.php?authkey=" + authKey + "&type=" + params[1]);
                    break;
                case "PASSWORD":
                    // URL syntax : http://api.msg91.com/api/password.php?authkey=YourAuthKey&password=password&newpass=newpassword&new_pass=confirmpass
                    myURL = new URL("http://api.msg91.com/api/password.php?authkey=" + authKey + "&password=" + params[1] + "&newpass=" + params[2] + "&new_pass2=" + params[2]);
                    break;
                case "VALIDATE":
                    // URL syntax : http://api.msg91.com/api/validate.php?authkey=YourAuthKey
                    myURL = new URL("http://api.msg91.com/api/validate.php?authkey=" + authKey);
                    break;
            }

            if (myURL != null) {
                if (debugMode)
                    Log.d(debugTAG, "URL: " + myURL.toString());
                myURLConnection = myURL.openConnection();
                myURLConnection.connect();
                reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
                String lineReader;
                while ((lineReader = reader.readLine()) != null)
                    response = lineReader;
                if (debugMode)
                    Log.d(debugTAG, "RESPONSE: " + response);
                reader.close();

                if (response.contains("error")) {
                    if (response.contains("207"))
                        return "ERROR (207) : Auth key invalid";
                    else if (response.contains("302"))
                        return "ERROR (302) : Expired user account";
                    else if (response.contains("303"))
                        return "ERROR (303) : Banned user account";
                    else if (response.contains("001"))
                        return "ERROR (001) : Unable to connect database";
                    else if (response.contains("002"))
                        return "ERROR (002) : Unable to select database";
                    else
                        return response;
                }
            } else
                response = "ERROR (NULL) : URL is NULL";

            return response;

        } catch (IOException e) {
            if (debugMode)
                return "ERROR (IOException) : Unable to download the requested page.\n";
            else
                return "ERROR (IOException) : \n" + e;
        }
    }
}
