# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep all Koin related classes and inject functions
-keepclassmembers class * {
    @org.koin.core.annotation.* <methods>;
}
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Required by Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.** { *; }
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder { *; }
-keep interface com.bumptech.glide.load.model.ModelLoader { *; }
-dontwarn com.bumptech.glide.**

# Navigation UI
-keep class androidx.navigation.** { *; }
-dontwarn androidx.navigation.**