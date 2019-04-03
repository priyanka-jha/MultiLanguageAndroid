package com.android.priyanka.multilanguage;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Locale locale;
    String languageToLoad;
    String language = "en";
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        language = preferences.getString("language", "en");
        locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homepage, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changelanguage:

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.radio_btn_dialog);
                dialog.setTitle(getString(R.string.choose_language));
                dialog.setCancelable(true);
                RadioGroup group = (RadioGroup) dialog.findViewById(R.id.radiogroup);
                final RadioButton en = (RadioButton) dialog.findViewById(R.id.en);
                final RadioButton hi = (RadioButton) dialog.findViewById(R.id.hi);
                if (language.equals("en")) {
                    en.setChecked(true);
                    lang = "en";
                } else if (language.equals("hi")) {
                    hi.setChecked(true);
                    lang = "hi";
                }
                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                        if (en.isChecked()) {
                            lang = "en";
                        } else if (hi.isChecked()) {
                            lang = "hi";
                        }
                    }
                });

                Button done = (Button) dialog.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setLanguage(lang);
                        dialog.dismiss();
                    }
                });
                dialog.show();

                break;





            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void setLanguage(String languageToLoad) {
        locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", languageToLoad);
        editor.commit();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
