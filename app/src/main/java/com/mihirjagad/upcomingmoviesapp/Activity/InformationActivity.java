package com.mihirjagad.upcomingmoviesapp.Activity;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihirjagad.upcomingmoviesapp.R;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        View view =getSupportActionBar().getCustomView();

        TextView title = (TextView) view.findViewById(R.id.action_bar_title);
        title.setText("Information");

        ImageView information= (ImageView) view.findViewById(R.id.information);
        information.setVisibility(View.GONE);
    }
}
