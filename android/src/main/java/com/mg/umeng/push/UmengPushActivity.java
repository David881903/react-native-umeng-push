package com.mg.umeng.push;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Iterator;

public class UmengPushActivity extends UmengNotifyClickActivity {
    private static String TAG = "UmengPushActivity";
    public static WritableMap param = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        try {
            param = Arguments.createMap();

            JSONObject object = new JSONObject(body);
            JSONObject extra = object.optJSONObject("extra");
            Iterator<String> it = extra.keys();
            while (it.hasNext()) {
                String key = it.next();
                String value = extra.getString(key);
                param.putString(key, value);
            }

            Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage(getApplication().getPackageName());
            LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(LaunchIntent);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }


}
