/* Copyright Airship and Contributors */

package com.urbanairship.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.urbanairship.Autopilot;
import com.urbanairship.Logger;
import com.urbanairship.ResultCallback;
import com.urbanairship.UAirship;

/**
 * An activity that handles notification intents.
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class NotificationProxyActivity extends Activity {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Autopilot.automaticTakeOff(this);

        if (!UAirship.isTakingOff() && !UAirship.isFlying()) {
            Logger.error("NotificationProxyActivity - unable to receive intent, takeOff not called.");
            return;
        }

        Intent intent = getIntent();


        if (intent == null || intent.getAction() == null) {
            return;
        }

        Logger.verbose("NotificationProxyActivity - Received intent: %s", intent.getAction());

        new NotificationIntentProcessor(this, intent)
                .process()
                .addResultCallback(new ResultCallback<Boolean>() {
                    @Override
                    public void onResult(@Nullable Boolean result) {
                        Logger.verbose("NotificationProxyActivity - Finished processing notification intent with result %s.", result);
                    }
                });

        finish();
    }
}
