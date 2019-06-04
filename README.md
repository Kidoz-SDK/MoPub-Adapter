
# KIDOZ MOPUB ADAPTER
Kidoz MoPub mediation adapter
</br>

**Prerequisits:**

In order to use the Kidoz SDK adapter for MoPub, please make sure you have:
1. MoPub Mediation integrated in your project.
2. A fully functional MoPub ad placement defined in your MoPub dashboard.
3. Kidoz SDK integrated in your project.

3.1. You can get Kidoz SDK as a Gradle dependency (together with it's needed dependencies) using the following lines:

```
compile group: 'org.greenrobot', name: 'eventbus', version: '3.0.0'
compile 'com.android.support:support-v4:23.0.+'
compile 'com.kidoz.sdk:KidozSDK:0.8.8.2@aar'
```

3.2. Please make sure you have a set up Kidoz publisher account.

3.3. The plugin itself consists of the java files inside the 'com\kidoz\mediation\mopub\adapters' directory. Copy this entire package to your own project, OR either integrate `kidoz-mopub.jar` adapter jar file with your project.

3.4. Set your Kidoz PublisherId & PublisherToken in the adapter, as the following:

- If you are using the java files in the adapters folder:
```
KidozManager.setKidozPublisherId(<publisherId>)
KidozManager.setKidozPublisherToken(<publisherToken>)
```

- If you are using the `kidoz-mopub.jar` define in the custom native network the `Custom event class data` column and enter a JSON object with String keys and values:
```
For Banner:
{"AppID":"your_publisher_Id", "Token":"your_publisher_Token"}

For Interstitial:
{"AppID":"your_publisher_Id", "Token":"your_publisher_Token"}

For Rewarded Video:
{"AppID":"your_publisher_Id", "Token":"your_publisher_Token"}
```

3.5. In order to connect directly with the Kidoz reward events, use the following (valid only for java files in the adapters folder):
```
KidozManager.setRewardedEvents(<new BaseInterstitial.IOnInterstitialRewardedEventListener>);
```

</br>

**Integration Steps:**
* Include the `com.kidoz.mediation.mopub.adapters` classes in your project OR use the `kidoz-mopub.jar` adapter file .
* Define a Custom Native Network in your MoPub dashboard.
* In this custom native network, define the following Kidoz custom events in `Custom event class` column:
  * To use Kidoz full screen Interstitial: `com.kidoz.mediation.mopub.adapters.KidozustomEventInterstitial`
  * To use Kidoz Rewarded ad: `com.kidoz.mediation.mopub.adapters.KidozCustomEventRewardedVideo`
  * To use Kidoz Banner: `com.kidoz.mediation.mopub.adapters.KidozCustomEventBanner`


** Please Note: if you are using the java files in the adapters folder, you may need to change the Kidoz adapter classpath in your project, but make sure the class names in the MoPub dashboard correspond to your final adapter location.


<br/>
**Example for adding a network:**
</br>

</br>
<a href="url"><img src="https://cdn.kidoz.net/sdk/mopub_add_network.png" align="left" height="659" width="994" ></a>
</br>



</br>
<br/>
</br>
<a href="url"><img src="https://cdn.kidoz.net/sdk/mopub_manage_network.png" align="center" height="663" width="998" ></a>
</br>



```
For any question or assistance, please contact us at SDK@kidoz.net

License
=======

Copyright 2015 KIDOZ, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
