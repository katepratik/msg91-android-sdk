# MSG91 SDK for Android (Beta)

Easy way to integrate [MSG91](https://msg91.com/) API's to your Android App.

## About MSG91
MSG91, a bulk SMS service provider offers transactional and promotional bulk SMS solutions to enterprises via powerful & robust bulk SMS gateway internationally.

## Gradle Dependency
Add this in your build.gradle
```groovy
dependencies {
  compile 'com.katepratik.msg91-android-sdk:library:0.1'
}
```

## Usage

#### Create MSG91 object with you [Authentication Key](https://control.msg91.com/user/index.php#api)

  Normal Use: 
```java
MSG91 msg91 = new MSG91("your_auth_key");
```    
  Debugging Use: 
```java
MSG91 msg91Debug = new MSG91("your_auth_key", true);
```
##### After creating MSG91's Object, below are the methods provides
NOTE : These methods return String response which is received from the REST API's of MSG91

1) To validate your auth key
```java
String validate = msg91Debug.validate();
```    
2) To check SMS balance
```java
// Promotional SMS Route is 1
String balancePromotional = msg91.getBalance("1");
// Transactional SMS Route is 4
String balanceTransactional = msg91.getBalance("4");
```    
3) To change password
```java
String change = msg91.changePassword("old_password", "new_password");
```    
3) To send SMS to SINGLE mobile number
```java
msg91.composeMessage("ABCDEF", "This Sample message body that will be sent with sender id : ABCDEF to single mobile number");
msg91.to("9876543210");
String sendStatus = msg91.send();
```    
4) To send SMS to MULTIPLE mobile number
```java
ArrayList<String> mobileNumbers =new ArrayList<>();
mobileNumbers.add("9876543210");
mobileNumbers.add("9876543211");
mobileNumbers.add("1234567890");
mobileNumbers.add("1234567890");
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to(mobileNumbers);
String sendStatus = msg91.send();
```    
5) To send Schedule SMS
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210";
msg91.setSchedule("2015-12-27 12:38:38");
String sendStatus = msg91.send();
```    
6) To send Flash SMS
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210");
msg91.flash(true);
String sendStatus = msg91.send();
```    
7) To send Unicode SMS
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210");
msg91.unicode(true);
String sendStatus = msg91.send();
```    
8) To send Promotional SMS
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210");
msg91.setRoute("1");
String sendStatus = msg91.send();
```    
9) To send Transactional SMS
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210");
msg91.setRoute("4");
String sendStatus = msg91.send();
```    
10) To set Campaign
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210");
msg91.setCampaign("Campaign");
String sendStatus = msg91.send();
```    
11) To set Country Code
```java
msg91.composeMessage("ABCDEF", "Your Message");
msg91.to("9876543210");
msg91.setCountryCode("91");
String sendStatus = msg91.send();
```    


## Credits
SDK Developer : [Pratik Kate](https://www.facebook.com/kate.pratik) | REST API's : [MSG91 Team](https://msg91.com/about)



## License

    Copyright 2015 Pratik Kate

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

