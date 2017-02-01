package org.vbm.iceandflame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static MainActivity Me;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSplash();
        Me = this;
    }

    private void initSplash() {
        textView = (TextView) findViewById(R.id.outout);
        BgHTTPread bRead = new BgHTTPread();
        bRead.execute("houses");
    }
}
