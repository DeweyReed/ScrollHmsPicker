[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-ScrollHmsPicker-green.svg?style=flat)]( https://android-arsenal.com/details/1/6805)
[![Newest version](https://jitpack.io/v/DeweyReed/ScrollHmsPicker.svg)](https://jitpack.io/#DeweyReed/ScrollHmsPicker)

# ScrollHmsPicker

A simple HMS time picker with scrolling.

## Screenshots

| In the XML | In the Dialog |  |
|:-:|:-:|:-:|
| ![In the XML](art/xml.png) | ![In the Dialog](art/dialog.png) |

## Install

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```Groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. Add the dependency => [![Newest version](https://jitpack.io/v/DeweyReed/ScrollHmsPicker.svg)](https://jitpack.io/#DeweyReed/ScrollHmsPicker)

```Groovy
dependencies {
    implementation 'com.github.DeweyReed:ScrollHmsPicker:$version'
}
```

**2.X.X requires AndroidX. 1.0.3 is the last version using the support library.**

## Usage

```XML
<io.github.deweyreed.scrollhmspicker.ScrollHmsPicker
    android:id="@+id/scrollHmsPicker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

Then, use `scrollHmsPicker.getHours()` and other getters to get users' input.

## Attributes

|attribute|xml|default|means|
|:-:|:-:|:-:|:-:|
|setHours|shp_hours|0(Int)|set picker's hours|
|setMinutes|shp_minutes|0(Int)|set picker's minutes|
|setSeconds|shp_seconds|0(Int)|set picker's seconds|
|setColorNormal|shp_normal_color|android.R.color.darker_gray(color resource)|set picker's not selected text color|
|setColorIntNormal|shp_normal_color|-|accepts a color int|
|setColorSelected|shp_selected_color|android.R.color.holo_red_light(color resource)|set picker's selected text color|
|setColorIntSelected|shp_selected_color|-|accepts a color int|
|setAutoStep|shp_auto_step|false(Boolean)|let picker automatically increment 1 minute if seconds move from 59 to 00 or increment 1 hour if minutes move from 59 to 00|
|setHoursVisibility|shp_show_hours|`true`|set hours picker visibility|
|setMinutesVisibility|shp_show_minutes|`true`|set minutes picker visibility|
|setSecondsVisibility|shp_show_seconds|`true`|set seconds picker visibility|
|set99Hours|shp_enable_99_hours|`false`|Change max hours from 23 to 99|
|setTypeface|||Set all texts typeface|
|getTypeface|||Get current text typeface|

## License

[MIT License](https://github.com/DeweyReed/ScrollHmsPicker/blob/master/LICENSE)
