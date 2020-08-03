package com.example.orientation;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.OrientationEventListener;

public class MainActivity extends AppCompatActivity {
    OrientationEventListener mOrientationListener;
    int rotation ;
    int ChangeOrientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rotation = getWindowManager().getDefaultDisplay().getRotation();
       // Log.v("zmz","rotation changed to " + rotation);
        mOrientationListener=new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                int dist = Math.abs(orientation - rotation);
                dist = Math.min(dist, 360 - dist);

                boolean isOrientationChanged = (dist >= 45 + 5);
                orientation=((orientation + 45) / 90 * 90) % 360;
                if (isOrientationChanged) {
                    int newRoundedOrientation = ((orientation + 45) / 90 * 90) % 360;
                    String Orientation =String.valueOf(newRoundedOrientation);
                    Log.d("zmz","zmz--newRoundedOrientation="+Orientation);
                    switch (newRoundedOrientation) {
                        case 0:
                            ChangeOrientation=0;break;
                        case 90:
                            ChangeOrientation=90;break;
                        case 180:
                            ChangeOrientation=180;break;
                        case 270:
                            ChangeOrientation=270;break;
                    }
                }
               if (ChangeOrientation==rotation){
                   return;
               }
               rotation=ChangeOrientation;


           //     Log.v("zmz","Orientation changed to " + orientation);
            }
            };

        if (mOrientationListener.canDetectOrientation()) {
        //    Log.v("zmz", "Can detect orientation");
            mOrientationListener.enable();
        } else {
         //   Log.v("zmz", "Cannot detect orientation");
            mOrientationListener.disable();
        }

        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }
}

