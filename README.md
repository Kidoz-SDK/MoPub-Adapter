# KIDOZ MOPUB ADAPTER
Kidoz MoPub mediation adapter
</br>

**Prerequisits:**
* To use the Kidoz SDK adapter for MoPub you should make sure you have:
1. MoPub Mediation integrated in your project.
2. A fully functional MoPub ad placement defined in your MoPub dashboard.
3. Kidoz SDK integrated in your project.
3.1. You can get Kidoz SDK as a Gradle dependency (together with it's needed dependencies) using the following lines:
```
    compile group: 'org.greenrobot', name: 'eventbus', version: '3.0.0'
    compile 'com.android.support:support-v4:23.0.+'
    compile 'com.kidoz.sdk:KidozSDK:0.8.1.0@aar'
```
3.2. Please make sure you have a set up Kidoz publisher account.
3.3. The plugin itself consists of the java files inside the 'pluginFiles' directory, copy this entire package to your own project.

3.4. Set your Kidoz PublisherId & PublisherToken in the adapter using the following:
```
KidozManager.setKidozPublisherId(<publisherId>)
KidozManager.setKidozPublisherToken(<publisherToken>)
```
3.5. If you want to connect directly with the Kidoz reward events use the following:
```
KidozManager.setRewardedEvents(<new BaseInterstitial.IOnInterstitialRewardedEventListener>);
```

</br>

**Integration Steps:**
* Include the 'kidoz.mopublib' classes in your project.
* Define a Custom Native Network in your MoPub dashboard.
* In this custom native network, define the following Kidoz custom events:
    * To use Kidoz full screen Interstitial: kidoz.mopublib.KidozCustomEventInterstitial
    * To use Kidoz Rewarded ad: kidoz.mopublib.KidozCustomEventRewardedVideo
    * To use Kidoz Banner: kidoz.mopublib.KidozCustomEventBanner

* Please Note: you can change the Kidoz adapter classpath in your project but maker sure the class names you put in the MoPub dashboard correspond to your final adapter location.
 

</br>
License
--------

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

