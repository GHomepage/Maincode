
package com.baidu.duer.dcs.oauth.api;

import android.content.Intent;
import android.text.TextUtils;

import com.baidu.duer.dcs.androidapp.DcsSampleApplication;
import com.baidu.duer.dcs.androidapp.DcsSampleOAuthActivity;

public class OauthImpl implements IOauth {
    @Override
    public String getAccessToken() {
        return OauthPreferenceUtil.getAccessToken(DcsSampleApplication.getInstance());
    }

    @Override
    public void authorize() {
        Intent intent = new Intent(DcsSampleApplication.getInstance(), DcsSampleOAuthActivity.class);
        intent.putExtra("START_TAG", "RESTART");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        DcsSampleApplication.getInstance().startActivity(intent);
    }

    @Override
    public boolean isSessionValid() {
        String accessToken = getAccessToken();
        long createTime = OauthPreferenceUtil.getCreateTime(DcsSampleApplication.getInstance());
        long expires = OauthPreferenceUtil.getExpires(DcsSampleApplication.getInstance()) + createTime;
        return !TextUtils.isEmpty(accessToken) && expires != 0 && System.currentTimeMillis() < expires;
    }
}
