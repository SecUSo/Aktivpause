package org.secuso.aktivpause.activities.tutorial;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.annotation.RequiresApi;

import org.secuso.aktivpause.database.SQLiteHelper;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Class structure taken from tutorial at http://www.androidhive.info/2016/05/android-build-intro-slider-app/
 */
public class FirstLaunchManager {
    private static final String TAG = FirstLaunchManager.class.getSimpleName();

    public static final String PREF_PICKER_SECONDS          = TAG + ".PREF_PICKER_SECONDS";
    public static final String PREF_PICKER_MINUTES          = TAG + ".PREF_PICKER_MINUTES";
    public static final String PREF_PICKER_HOURS            = TAG + ".PREF_PICKER_HOURS";
    public static final String PREF_BREAK_PICKER_SECONDS    = TAG + ".PREF_BREAK_PICKER_SECONDS";
    public static final String PREF_BREAK_PICKER_MINUTES    = TAG + ".PREF_BREAK_PICKER_MINUTES";

    public static final String DEFAULT_EXERCISE_SET         = "DEFAULT_EXERCISE_SET";
    public static final String PAUSE_TIME                   = "PAUSE TIME";
    public static final String REPEAT_STATUS                = "REPEAT_STATUS";
    public static final String REPEAT_EXERCISES             = "REPEAT_EXERCISES";
    public static final String EXERCISE_DURATION            = "pref_exercise_time";
    public static final String KEEP_SCREEN_ON_DURING_EXERCISE = "pref_keep_screen_on_during_exercise";
    public static final String PREF_SCHEDULE_EXERCISE_ENABLED = "pref_schedule_exercise";
    public static final String PREF_SCHEDULE_EXERCISE_DAYS_ENABLED = "pref_schedule_exercise_daystrigger";
    public static final String PREF_SCHEDULE_EXERCISE_DAYS  = "pref_schedule_exercise_days";
    public static final String PREF_SCHEDULE_EXERCISE_TIME  = "pref_schedule_exercise_time";
    public static final String PREF_EXERCISE_CONTINUOUS     = "pref_exercise_continuous";
    public static final String PREF_HIDE_DEFAULT_SETS       = "pref_hide_default_exercise_sets";
    public static final String WORK_TIME                    = "WORK_TIME";
    public static final String PREF_SCHEDULE_RANDOM_EXERCISE= "pref_schedule_random_exercise";

    private final SQLiteHelper dbHandler;
    private Context context;
    private SharedPreferences pref;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public FirstLaunchManager(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        dbHandler = new SQLiteHelper(context);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        pref.edit().putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime).apply();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void initFirstTimeLaunch() {
        if(pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)) {
            pref.edit()
                    .putLong(DEFAULT_EXERCISE_SET, 0L)
                    .putLong(PAUSE_TIME, 5 * 60 * 1000) // 5 minutes
                    .putBoolean(REPEAT_STATUS, false)
                    .putBoolean(REPEAT_EXERCISES, true)
                    .putBoolean(PREF_HIDE_DEFAULT_SETS, false)
                    .putInt(PREF_BREAK_PICKER_SECONDS, 0)
                    .putInt(PREF_BREAK_PICKER_MINUTES, 5)
                    .putInt(PREF_PICKER_SECONDS, 0)
                    .putInt(PREF_PICKER_MINUTES, 0)
                    .putInt(PREF_PICKER_HOURS, 2)
                    .putLong(WORK_TIME, 1000L * 60L * 60L) // 1 hour
                    .putString(EXERCISE_DURATION, "30")
                    .putBoolean(PREF_SCHEDULE_EXERCISE_DAYS_ENABLED, false)
                    .putBoolean(PREF_SCHEDULE_EXERCISE_ENABLED, false)
                    .putLong(PREF_SCHEDULE_EXERCISE_TIME, 32400000L)
                    .putBoolean(KEEP_SCREEN_ON_DURING_EXERCISE, true)
                    .putBoolean(PREF_EXERCISE_CONTINUOUS, false)
                    .putBoolean(PREF_SCHEDULE_RANDOM_EXERCISE, false)
                    .putStringSet(PREF_SCHEDULE_EXERCISE_DAYS, new HashSet<String>(Arrays.asList("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")))
                    .apply();

            //TODO loadInitialExerciseSets();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannels();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannels() {
        String groupId = "timer_group";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // group
        notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, "Timer Notifications"));

        // channels
        NotificationChannel timerRunningChannel = new NotificationChannel("timer_running", "Timer Running Notification", NotificationManager.IMPORTANCE_DEFAULT);
        timerRunningChannel.setVibrationPattern(new long[] { 0 });
        timerRunningChannel.setSound(null, null);
        timerRunningChannel.setGroup(groupId);

        NotificationChannel timerDoneChannel = new NotificationChannel("timer_done", "Timer Done Notification", NotificationManager.IMPORTANCE_HIGH);
        timerDoneChannel.setVibrationPattern(new long[] { 0, 1000, 1000, 1000, 1000, 1000, 1000 });
        timerDoneChannel.setGroup(groupId);

        notificationManager.createNotificationChannel(timerRunningChannel);
        notificationManager.createNotificationChannel(timerDoneChannel);
    }
}
