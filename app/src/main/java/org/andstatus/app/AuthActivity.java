package org.andstatus.app;

import android.content.Intent;

import com.mobbu.passwear.api.ui.PasswearActivity;

import org.andstatus.app.msg.TimelineActivity;

/**
 * Created by olliee on 26/11/15.
 */
public class AuthActivity extends PasswearActivity {

    @Override
    public void finish() {
        startActivity(new Intent(this, TimelineActivity.class));
        super.finish();
    }
}
