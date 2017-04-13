<html lang="en"><head>
    <meta charset="UTF-8">
    <title></title>
<style id="system" type="text/css">h1,h2,h3,h4,h5,h6,p,blockquote {    margin: 0;    padding: 0;}body {    font-family: "Helvetica Neue", Helvetica, "Hiragino Sans GB", Arial, sans-serif;    font-size: 13px;    line-height: 18px;    color: #737373;    margin: 10px 13px 10px 13px;}a {    color: #0069d6;}a:hover {    color: #0050a3;    text-decoration: none;}a img {    border: none;}p {    margin-bottom: 9px;}h1,h2,h3,h4,h5,h6 {    color: #404040;    line-height: 36px;}h1 {    margin-bottom: 18px;    font-size: 30px;}h2 {    font-size: 24px;}h3 {    font-size: 18px;}h4 {    font-size: 16px;}h5 {    font-size: 14px;}h6 {    font-size: 13px;}hr {    margin: 0 0 19px;    border: 0;    border-bottom: 1px solid #ccc;}blockquote {    padding: 13px 13px 21px 15px;    margin-bottom: 18px;    font-family:georgia,serif;    font-style: italic;}blockquote:before {    content:"C";    font-size:40px;    margin-left:-10px;    font-family:georgia,serif;    color:#eee;}blockquote p {    font-size: 14px;    font-weight: 300;    line-height: 18px;    margin-bottom: 0;    font-style: italic;}code, pre {    font-family: Monaco, Andale Mono, Courier New, monospace;}code {    background-color: #fee9cc;    color: rgba(0, 0, 0, 0.75);    padding: 1px 3px;    font-size: 12px;    -webkit-border-radius: 3px;    -moz-border-radius: 3px;    border-radius: 3px;}pre {    display: block;    padding: 14px;    margin: 0 0 18px;    line-height: 16px;    font-size: 11px;    border: 1px solid #d9d9d9;    white-space: pre-wrap;    word-wrap: break-word;}pre code {    background-color: #fff;    color:#737373;    font-size: 11px;    padding: 0;}@media screen and (min-width: 768px) {    body {        width: 748px;        margin:10px auto;    }}</style><style id="custom" type="text/css"></style></head>
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