package org.andstatus.app;

import android.content.Intent;
import com.mobbu.passwear.api.PasswearActivity;
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
