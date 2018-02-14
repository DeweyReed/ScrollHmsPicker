# ScrollHmsPicker
一个简单的 时分秒 的 带滚动 的时间选择控件。

## 截图
| 默认对话框 | 自定义主题 | 用于XML |
|:-:|:-:|:-:|
| ![默认对话框](https://github.com/DeweyReed/ScrollHmsPicker/blob/master/art/default.png?raw=true) | ![自定义主题](https://github.com/DeweyReed/ScrollHmsPicker/blob/master/art/theme.png?raw=true) | ![用于XML](https://github.com/DeweyReed/ScrollHmsPicker/blob/master/art/xml.png?raw=true) |

## 安装
1. 在根build.gradle添加jitpack.io:
```
allprojects {
	repositories {
        ...
		maven { url 'https://jitpack.io' }
	}
}
```
2. 
```
dependencies {
	implementation 'com.github.DeweyReed:ScrollHmsPicker:1.0.0'
}
```
[![](https://jitpack.io/v/DeweyReed/ScrollHmsPicker.svg)](https://jitpack.io/#DeweyReed/ScrollHmsPicker)

## 使用
### XML
```
<io.github.deweyreed.scrollhmspicker.ScrollHmsPicker
    android:id="@+id/scrollHmsPicker"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```
用```scrollHmsPicker.getHours()```获取输入。
### 显示Dialog
为Activity或其他实现```ScrollHmsPickerDialog.HmsPickHandler```。
```
class MainActivity : AppCompatActivity(), ScrollHmsPickerDialog.HmsPickHandler {
```
```
override fun onHmsPick(reference: Int, hours: Int, minutes: Int, seconds: Int) {
    longToast("reference: $reference, hours: $hours, minutes: $minutes, seconds: $seconds")
}
```
Build
```
ScrollHmsPickerBuilder(supportFragmentManager, this)
    .setReference(255)
    .setTime(1, 23, 45)
    .setAutoStep(true)
    .setColorNormal(android.R.color.holo_blue_light)
    .setColorSelected(android.R.color.black)
    .setColorBackground(android.R.color.holo_orange_light)
    .setDismissListener(DialogInterface.OnDismissListener {
        toast("Dismiss")
    })
    .show()
```

## 属性

|属性|xml|默认|用法|
|:-:|:-:|:-:|:-:|
|setHours|shp_hours|0(Int)|设置小时|
|setMinutes|shp_minutes|0(Int)|设置分钟|
|setSeconds|shp_seconds|0(Int)|设置秒|
|setColorNormal|shp_normal_color|android.R.color.darker_gray(color resource)|设置未选择条目的颜色|
|setColorSelected|shp_selected_color|android.R.color.holo_red_light(color resource)|设置已选择条目的颜色|
|setAutoStep|shp_auto_step|false(Boolean)|当分钟从59到00时，自动增加1小时，或者当秒从59到00时，自动增加1分钟|

## License
[MIT License](https://github.com/DeweyReed/ScrollHmsPicker/blob/master/LICENSE)