package com.example.ayadzeino.appesiea;
import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;

/**
 * Created by Rachid on 29/12/2015.
 */
public class Menu extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ButterKnife.bind(this);

    }

}
