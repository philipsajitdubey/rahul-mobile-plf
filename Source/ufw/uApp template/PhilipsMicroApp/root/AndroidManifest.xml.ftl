<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="${packageName}library">

    <application <#if minApiLevel gte 4 && buildApi gte 4>android:allowBackup="true"</#if>>
   		 <activity android:name="${packageName}.${escapeXmlString(appTitle)}Activity"/>
    </application>

</manifest>
