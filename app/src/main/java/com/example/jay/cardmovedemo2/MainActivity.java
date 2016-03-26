package com.example.jay.cardmovedemo2;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        relativeLayout.addView(new GameScene(this, relativeLayout, -162));

        setContentView(relativeLayout);

    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);

        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;

        View v = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        int contentTop = v.getTop();

        Log.d("touchCorrection:",String.valueOf(-(contentTop+statusBarHeight)));
    }

}