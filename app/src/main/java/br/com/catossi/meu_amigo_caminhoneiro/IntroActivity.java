package br.com.catossi.meu_amigo_caminhoneiro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }

        addSlide(new IntroOneFragment());
        addSlide(new IntroTwoFragment());
        addSlide(new IntroThreeFragment());
        addSlide(new IntroFourFragment());

        showSkipButton(false);
        setIndicatorColor(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.colorSecondary));
        setNextArrowColor(getResources().getColor(R.color.colorPrimary));
        setColorDoneText(getResources().getColor(R.color.colorPrimary));
        setDoneText("LOGIN");

//        Timer verifySpeaking = new Timer();
//        TimerTask verifySpeakingObj = new TimerTask() {
//            public void run() {
//                IntroActivity.super.nextButton.callOnClick();
//            }
//        };
//
//        verifySpeaking.schedule(verifySpeakingObj, 2000, 5000);


    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, LoginActivity.class));
    }

}