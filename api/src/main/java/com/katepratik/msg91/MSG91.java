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

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Pratik on 19-12-2015.
 */
public class MSG91 {

    String mainUrl = "http://api.msg91.com/api/postsms.php?data=";
    String authKey,textMessage,senderId,singleNumber,msgSchedule,smsRoute,smsCampaign,smsCountryCode;
    ArrayList<String> multiNumber;
    boolean isFlash=false,isUnicode=false;

    public MSG91(String key)
    {
        authKey = key;
    }

    //Required
    public void setMessage(String text)
    {
        textMessage = text;
    }

    //Required
    public void setSenderID(String id)
    {
        senderId = id;
    }
    //Required
    public void to(String mobile)
    {
        singleNumber = mobile;
    }
    public void to(ArrayList<String> mobiles)
    {
        multiNumber = mobiles;
    }

    public void setSchedule(String dateTime)
    {
        msgSchedule = dateTime;
    }

    public void flash()
    {
        isFlash = true;
    }

    public void unicode()
    {
        isUnicode = true;
    }

    public void setRoute(String route)
    {
        smsRoute = route;
    }

    public void setCampaign(String campaign)
    {
        smsCampaign = campaign;
    }

    public void setCountryCode(String code)
    {
        smsCountryCode = code;
    }

    public String send()
    {
        StringBuilder xmlData = new StringBuilder("<MESSAGE>");
        xmlData.append("<AUTHKEY>").append(authKey).append("</AUTHKEY>");

        // ROUTE
        if (smsRoute!=null)
            xmlData.append("<ROUTE>").append(smsRoute).append("</ROUTE>");

        // CAMPAIGN
        if (smsCampaign!=null)
            xmlData.append("<CAMPAIGN>").append(smsCampaign).append("</CAMPAIGN>");

        // COUNTRY
        if (smsCountryCode!=null)
            xmlData.append("<COUNTRY>").append(smsCountryCode).append("</COUNTRY>");

        // SENDER ID : Cannot be Null
        if (senderId!=null)
            xmlData.append("<SENDER>").append(senderId).append("</SENDER>");
        else
            return "ERROR : Sender ID is Missing";

        // SCHEDULE DATE TIME
        if (msgSchedule!=null)
            xmlData.append("<SCHEDULEDATETIME>").append(msgSchedule).append("</SCHEDULEDATETIME>");

        //FLASH
        if (isFlash)
            xmlData.append("<FLASH>").append("1").append("</FLASH>");

        //UNICODE
        if (isUnicode)
            xmlData.append("<UNICODE>").append("1").append("</UNICODE>");

        // TEXT : Cannot be Null
        if (textMessage!=null)
            xmlData.append("<SMS TEXT=\"").append(textMessage).append("\">");
        else
            return "ERROR : Text Message is Missing";

        // TO : Cannot be null
        if (singleNumber!=null || multiNumber!=null)
        {
            if (singleNumber!=null)
            {
                if (singleNumber.length()==10)
                    xmlData.append("<ADDRESS TO=\"").append(singleNumber).append("\"></ADDRESS>");
                else
                    return "ERROR : "+singleNumber+" is not a valid Mobile Number";
            }
            if (multiNumber!=null)
            {
                for (int i=0;i<multiNumber.size();i++)
                {
                    String mobileNumber = multiNumber.get(i);
                    if (mobileNumber.length()==10)
                        xmlData.append("<ADDRESS TO=\"").append(mobileNumber).append("\"></ADDRESS>");
                    else
                        return "ERROR : "+mobileNumber+" is not a valid Mobile Number";
                }
            }

            xmlData.append("</SMS>").append("</MESSAGE>");
        }
        else
            return "ERROR : Mobile Number(s) Missing";

        try {

            String finalLink = mainUrl+ URLEncoder.encode(xmlData.toString(), "UTF-8");
            new SendTask(new SendResult() {
                @Override
                public void onResult(String result) {
                    Log.d("RESULT"," "+result);
                }
            }).execute(finalLink);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return ""+xmlData;
    }
}
