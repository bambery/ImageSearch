# Project 2 - *Google Image Search Client*

**Google Image Search Client** is an android app that allows a user to search for images on web using simple filters. The app utilizes [Google Image Search API](https://developers.google.com/image-search/). Please note that API has been officially deprecated as of May 26, 2011.

Time spent: **20?** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **search for images** by specifying a query and launching a search. Search displays a grid of image results from the Google Image API.
* [x] User can click on "settings" which allows selection of **advanced search options** to filter results
* [x] User can configure advanced search filters such as:
  * [x] Size (small, medium, large, extra-large)
  * [x] Color filter (black, blue, brown, gray, green, etc...)
  * [x] Type (faces, photo, clip art, line art)
* [x] Site (espn.com)
  * [x] Subsequent searches have any filters applied to the search results
  * [x] User can tap on any image in results to see the image **full-screen**
  * [ ] User can **scroll down to see more images**. The maximum number of images is 64 (limited by API).

  The following **optional** features are implemented:

  * [ ] Implements robust error handling, [check if internet is available](http://guides.codepath.com/android/Sending-and-Managing-Network-Requests#checking-for-network-connectivity), handle error cases, network failures
  * [x] Used the **ActionBar SearchView** or custom layout as the query box instead of an EditText
  * [ ] User can **share an image** to their friends or email it to themselves
  * [x] Replaced Filter Settings Activity with a lightweight modal overlay
  * [ ] Improved the user interface and experiment with image assets and/or styling and coloring

  The following **bonus** features are implemented:

  * [x] Used the [StaggeredGridView](https://github.com/f-barth/AndroidStaggeredGrid) to display improve the grid of image results
  * [ ] User can [zoom or pan images](https://github.com/MikeOrtiz/TouchImageView) displayed in full-screen detail view

  The following **additional** features are implemented:

  * [ ] List anything else that you can get done to improve the app functionality!
  filters are stored in shared preferences and will be maintained on app close/reopen. I also added an easy way to clear all of the filters. Also, I made sure you had to click the "save" button in order to store the filters - clicking outside of the dialog will not save the preferences.

## Video Walkthrough

  Here's a walkthrough of implemented user stories:

  <img src='https://cloud.githubusercontent.com/assets/161639/11232852/25e6dd44-8d6c-11e5-9d8f-c857b5cc2a15.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

  GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

  I was not able to get the infinite scrolling working. There does not appear to be any material from codepath on endless scrolling with a recyclerview and most solutions I found online use a deprecated api, and I was not able to get any of the other examples I found online working. I will talk again with a TA. Originally I had implemented the filters as a singleton, but I was eventually dissuaded from this approach. If searches were ever implemented as having a history, it would be necessary to attach an instance of the filters used on each specific search to maintain consistency across the history.
 
 The color and type filters no longer work. This is due to the age of the api and is unrelated to the implementation in the app.
  
  Most of the struggle was getting the recycler layout working, as I decided to implement the native android version rather than the deprecated fork of the etsy layout which was linked in the assignment. While it was a struggle, the next time I used a recycler layout, I was more familiar with the concepts and often referrred back to this project, though I wish I could get the endless scrolling working.

  I am storing the filter preferences in the internal shared preferences.

## Open-source libraries used

  - [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
  - [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

  Copyright [2015] [name of copyright owner]

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
