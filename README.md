# MaterialBanner

## ScreenShot:
![](https://github.com/rtugeek/MaterialBanner/blob/master/screenshot/GIF.gif)


## Idea from google trips:
![](https://github.com/rtugeek/MaterialBanner/blob/master/screenshot/googletrips.jpg)

### Attrs
|attr|format|default|
|---|---|---|
|indicatorMargin|dimension|10dp|
|indicatorInside|boolean|true|
|indicatorGravity|flag:left,center,right|left|
|match|boolean|false|


### Gradle:
[![](https://jitpack.io/v/rtugeek/materialbanner.svg)](https://jitpack.io/#rtugeek/materialbanner) [![API](https://img.shields.io/badge/API-8%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=9) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialBanner-green.svg?style=true)](https://android-arsenal.com/details/1/3118)

Step 1. Add the JitPack repository in your root build.gradle at the end of repositories:
```
  allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
Step 2. Add the dependency
```
  compile 'com.github.rtugeek:materialbanner:1.0.0'
```

XML
```xml
  <com.freegeek.android.materialbanner.MaterialBanner
      android:id="@+id/material_banner"
      android:layout_width="match_parent"
      app:match="true"
      android:layout_height="200dp"/>
```

```java
  MaterialBanner materialBanner = (MaterialBanner) findViewById(R.id.material_banner);
  materialBanner.setPages(new ViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new ImageHolderView();
            }
        },bannerData);
  //set circle indicator
  materialBanner.setIndicator(new CirclePageIndicator(this));
  //indicators:
  //CirclePageIndicator,IconPageIndicator,LinePageIndicator
  //Custom indicator view needs to implement com.freegeek.android.materialbanner.view.indicator.PageIndicator
  
```
[More usage](https://github.com/rtugeek/MaterialBanner/blob/master/app/src/main/java/com/freegeek/android/materialbanner/demo/MainActivity.java)

Listener
```java
  
  materialBanner.setOnItemClickListener(new MaterialBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                
            }
        });
  
  materialBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                textView.setText("My hometown: page " + ++position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        
        
```


**Spread the word**

<a href="https://twitter.com/intent/tweet?text=Check%20out%20the%MaterialBanner%20library%20on%20Github:%20https://github.com/rtugeek/MaterialBanner/" target="_blank" title="share to twitter" style="width:100%"><img src="https://github.com/PhilJay/MPAndroidChart/blob/master/design/twitter_icon.png" title="Share on Twitter" width="35" height=35 />
<a href="https://plus.google.com/share?url=https://github.com/rtugeek/MaterialBanner/" target="_blank" title="share to Google+" style="width:100%"><img src="https://github.com/PhilJay/MPAndroidChart/blob/master/design/googleplus_icon.png" title="Share on Google+" width="35" height=35 />
<a href="https://www.facebook.com/sharer/sharer.php?u=https://github.com/rtugeek/MaterialBanner/" target="_blank" title="share to facebook" style="width:100%"><img src="https://github.com/PhilJay/MPAndroidChart/blob/master/design/facebook_icon.png" title="Share on Facebook" width="35" height=35 />

## Thanks to:
[Android-ConvenientBanner](https://github.com/saiwu-bigkoo/Android-ConvenientBanner)	
[ViewPagerIndicator](https://github.com/JakeWharton/ViewPagerIndicator)

## License

            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
                    Version 2, December 2004
   
    Copyright (C) 2004 Leon Fu <rtugeek@gmail.com>
   
    Everyone is permitted to copy and distribute verbatim or modified
    copies of this license document, and changing it is allowed as long
    as the name is changed.
   
            DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
    TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
   
     0. You just DO WHAT THE FUCK YOU WANT TO.
