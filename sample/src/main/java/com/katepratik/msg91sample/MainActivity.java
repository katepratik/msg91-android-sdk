
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

package com.katepratik.msg91sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.katepratik.msg91.R;
import com.katepratik.msg91api.MSG91;


public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);

        /* Create MSG91 object with your Auth Key */

        MSG91 msg91 = new MSG91("98117Ae3PHTGjLGtv564c707c");

        /* DEBUG MODE for testing */

        MSG91 msg91Debug = new MSG91("98117Ae3PHTGjLGtv564c707c", true);

        /* VALIDATE */

        String validate = msg91Debug.validate();
        text.setText("Validation : " + validate);

        /* BALANCE CHECK */

        String balancePromotional = msg91.getBalance("1");
        String balanceTransactional = msg91.getBalance("4");
        text.setText(text.getText() + "\n\nBalance : \nPromotional - " + balancePromotional + "\nTransactional - " + balanceTransactional);

        /*

// --- [ CHANGE PASSWORD ] --- [ CHANGE PASSWORD ] --- [ CHANGE PASSWORD ] --- [ CHANGE PASSWORD ] ---

        String change = msg91.changePassword("old_password", "new_password");
        text.setText("Password Change : " + change);


        /*

 // --- [ SEND SMS ] --- [ SEND SMS ] --- [ SEND SMS ] --- [ SEND SMS ] --- [ SEND SMS ] ---

        mobileNumbers =new ArrayList<>();
        mobileNumbers.add("9876543210");
        mobileNumbers.add("9876543211");
        mobileNumbers.add("1234567890");
        mobileNumbers.add("1234567890");


        msg91.composeMessage("ABCDEF", "This Sample message body that will be sent with sender id : ABCDEF");

        // .to(String) : will send message to single mobile number
        msg91.to("987654321");

        // .to(ArrayList<String>) : will send message to all the number in the String
        msg91.to(mobileNumbers);

        // Optional Functions to set type of message

        msg91.setSchedule("2015-12-27 12:38:38");
        msg91.flash(true);
        msg91.unicode(true);
        msg91.setRoute("1");
        msg91.setCampaign("Campaign");
        msg91.setCountryCode("91");


        // .send must me called at the end of all the optional functions
        // NOTE : Before calling this function you MUST call .composeMessage & .to

        String sendStatus = msg91.send();
        text.setText("Send Status : " + sendStatus);

        */


        // katepratik id : 100545AU5duEWsk56768171

        // 99digitech id : 98117Ae3PHTGjLGtv564c707c


    }
}
