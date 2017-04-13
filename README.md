# timePicker
自定义的时间选择器

Step 1. Add the JitPack repository to your build file


Add it in your root build.gradle at the end of repositories:
gradle:
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
  
maven:
  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
  
  
Step 2. Add the dependency
gradle:
	dependencies {
	        compile 'com.github.wmuqing:timePicker:v1.0'
          }
maven: 
  <dependency>
	    <groupId>com.github.wmuqing</groupId>
	    <artifactId>timePicker</artifactId>
	    <version>v1.0</version>
	</dependency>
	
