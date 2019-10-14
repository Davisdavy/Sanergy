package com.moringa.sanergyapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.moringa.sanergyapp.R;

public class IssueAssetActivity extends AppCompatActivity {
    private TextView emp_id_view;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_asset);

        emp_id_view = (TextView) findViewById(R.id.emp_id_view);
        mUserId = getIntent().getStringExtra("emp_id");
        emp_id_view.setText(mUserId);
    }
}
