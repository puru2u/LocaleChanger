/*
 * Copyright (c)  2017  Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.franmontiel.localechanger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityRestarter {
    private static final Map<String, Locale> localesOnActivities = new HashMap<>();

    public static void restartActivityIfLocaleChanged(Activity activity) {
        Locale previousLocale = localesOnActivities.remove(activity.toString());
        boolean shouldRestartActivity = previousLocale != null && !previousLocale.equals(Locale.getDefault());
        if (shouldRestartActivity) {
            restartActivity(activity, false);
        } else {
            localesOnActivities.put(activity.toString(), Locale.getDefault());
        }
    }

    public static void restartActivity(Activity activity, boolean animate) {
        if (animate) {
            Intent restartIntent = new Intent(activity, activity.getClass());

            Bundle extras = activity.getIntent().getExtras();
            if (extras != null) {
                restartIntent.putExtras(extras);
            }
            ActivityCompat.startActivity(
                    activity,
                    restartIntent,
                    ActivityOptionsCompat
                            .makeCustomAnimation(activity, android.R.anim.fade_in, android.R.anim.fade_out)
                            .toBundle()
            );
            activity.finish();
        } else {
            activity.recreate();
        }
    }
}