package com.quicklistv_01.SegundaPantallas;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import com.quicklistv_01.R;

public class Config extends PreferenceFragment {
    @Override
    public void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setPreferenceScreen(null);
        addPreferencesFromResource(R.xml.preferencias);
        getPreferenceManager().setSharedPreferencesName("Red");
    }
}
