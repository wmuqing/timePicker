<html lang="en"><head>
    <meta charset="UTF-8">
<body marginheight="0"><h1>timePicker</h1>
<p>自定义的时间选择器

</p>
<p>Step 1. Add the JitPack repository to your build file

</p>
<p>Add it in your root build.gradle at the end of repositories:

</p>
<p>gradle:

</p>
<pre><code>allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}</code></pre>
<p>maven:

</p>
<pre><code>&lt;repositories&gt;
    &lt;repository&gt;
        &lt;id&gt;jitpack.io&lt;/id&gt;
        &lt;url&gt;https://jitpack.io&lt;/url&gt;
    &lt;/repository&gt;
&lt;/repositories&gt;</code></pre>
<p>Step 2. Add the dependency

</p>
<p>gradle:

</p>
<pre><code>dependencies {
        compile 'com.github.wmuqing:timePicker:v1.0'
      }</code></pre>
<p>maven: 

</p>
<pre><code>&lt;dependency&gt;
    &lt;groupId&gt;com.github.wmuqing&lt;/groupId&gt;
    &lt;artifactId&gt;timePicker&lt;/artifactId&gt;
    &lt;version&gt;v1.0&lt;/version&gt;
&lt;/dependency&gt;</code></pre>
<p>简单备注：时间格式为日周月季年，实现简单组合，如有其他需求可简单改动

</p>
<p>调用方式：

</p>
<pre><code>      TimePickerView timePickerView = 
      new TimePickerView(mContext, true, dateTime, queryType, mWeek,
      mSeason, calendar.get(Calendar.YEAR) - 50, calendar.get(Calendar.YEAR) + 50);
      timePickerView.setCyclic(false);
      timePickerView.setCancelable(true);
      //时间选择后回调
      timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

        @Override
        public void onTimeSelect(Date date0, int type, String title, String value) {
            dateTime = date0;//选择的时间
            date = value;//String格式的时间
            mTitle = title;//特殊处理的时间
            queryType = type;//记录选择的时间类型
        }
    });
      //显示timePickerView
      timePickerView.show();</code></pre>
<p>Edit By <a href="http://mahua.jser.me">MaHua</a></p>
</body></html>