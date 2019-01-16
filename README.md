笑脸点赞控件

首先感谢作者：@zenglingchao 

我是在该项目控件

https://github.com/zenglingchao/SmileView

的基础上

自己做了更改 

第一次提交项目的小菜鸡，

有什么问题也请大家多指教


![image](https://github.com/zeroBugL/lpz/blob/master/gif/ezgif.com-video-to-gif.gif)





集成方法：
===========gradle方式 ======================================

第一步：
Step 1. Add the JitPack repository to your build file

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


第二步：
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.zeroBugL:lpz:V1.0'
	}
	
============================================================




使用方法

XMl

 <com.lpz.library.SmileView
 
        android:id="@+id/smileView"
	
        android:layout_width="wrap_content"
	
        android:layout_height="wrap_content"
	
        app:lpz_iv_background_nor="@drawable/white_background"
	
        app:lpz_iv_background_sel="@drawable/yellow_background"
	
        app:lpz_iv_height="400"
	
        app:lpz_iv_resource="@drawable/animation_like"
	
        app:lpz_iv_size="25"
	
        app:lpz_iv_type="0"
	
        app:lpz_top_txt="满意"
	
        app:lpz_top_txt_color="#ffffff" />
	



  <attr name="lpz_iv_resource" format="reference" />   <!--笑脸图片资源 -->
  
        <attr name="lpz_iv_size" format="integer" />   <!--笑脸图片大小 -->
	
        <attr name="lpz_iv_background_sel" format="reference|color" />   <!--选中情况下的背景 -->
	
        <attr name="lpz_iv_background_nor" format="reference|color" />   <!--未选中情况下的背景 -->
	
        <attr name="lpz_iv_height" format="integer" />    <!--选中动画时 笑脸崛地而起的高度 -->
	
        <attr name="lpz_iv_type" format="integer" />      <!--动画 过程的方向  0-上下摆动   1-左右摆动 -->
	
        <attr name="lpz_top_txt" format="string" />       <!--笑脸顶部的文字内容 -->
	
        <attr name="lpz_top_txt_color" format="color|reference" /><!--笑脸顶部的文字的颜色 -->
	
