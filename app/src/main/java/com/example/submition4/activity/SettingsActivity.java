package com.example.submition4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.submition4.alarm.AlarmReceiver;
import com.example.submition4.R;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "ASSUU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference preference = getPreferenceManager().findPreference("signature");
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            if (preference != null) {
                preference.setIntent(intent);
            }
            SwitchPreference release = getPreferenceManager().findPreference(getString(R.string.daily_release));
            if (release != null) {
                release.setOnPreferenceChangeListener((preference1, newValue) -> {
                    Log.d(TAG, "onCreatePreferences: release "+newValue);
                    AlarmReceiver alarmReceiver = new AlarmReceiver();
                    if (newValue.equals(true)){
                        alarmReceiver.setReleaseAlarm(Objects.requireNonNull(getActivity()));
                    }else {
                        alarmReceiver.cancelAlarm(Objects.requireNonNull(getActivity()),AlarmReceiver.RELEASE_ALARM);
                    }
                    return true;
                });
            }
            SwitchPreference daily = getPreferenceManager().findPreference(getString(R.string.daily_notification));
            if (daily != null) {
                daily.setOnPreferenceChangeListener((preference1, newValue) -> {
                    Log.d(TAG, "onCreatePreferences: daily "+newValue);
                    AlarmReceiver alarmReceiver = new AlarmReceiver();
                    if (newValue.equals(true)){
                        alarmReceiver.setDailyAlarm(Objects.requireNonNull(getActivity()));
                    }else {
                        alarmReceiver.cancelAlarm(Objects.requireNonNull(getActivity()),AlarmReceiver.DAILY_ALARM);
                    }
                    return true;
                });
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }else return super.onOptionsItemSelected(item);
    }
}