package org.andstatus.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.mobbu.passwear.api.PasswearActivity;
import com.mobbu.passwear.api.PasswearXmlConfig;

import org.andstatus.app.msg.TimelineActivity;

/**
 * Created by olliee on 26/11/15.
 */
public class AuthActivity extends PasswearActivity {

    @Override
    public void finish() {
        super.finish();
        startActivity(new Intent(this, TimelineActivity.class));
    }
}
