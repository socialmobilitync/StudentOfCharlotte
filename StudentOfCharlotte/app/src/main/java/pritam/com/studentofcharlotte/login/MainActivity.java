package pritam.com.studentofcharlotte.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import pritam.com.studentofcharlotte.LoginActivity;
import pritam.com.studentofcharlotte.R;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener{

    public static String userType;
    ImageButton btn_schl_login,btn_stu_login;
    ImageView imgView;
    Animation animeEffectUptoDown,animeEffectDownToUp,animeOtherEffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prime_activity_main);

        btn_schl_login=(ImageButton)findViewById(R.id.btn_schl_login);
        imgView=(ImageView)findViewById(R.id.login_form);
        btn_stu_login=(ImageButton)findViewById(R.id.btn_stu_login);


        /*animeEffectUptoDown= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        animeEffectUptoDown.setAnimationListener(MainActivity.this);

        animeEffectDownToUp= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        animeEffectDownToUp.setAnimationListener(MainActivity.this);*/

        animeOtherEffect=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_other);
        animeOtherEffect.setAnimationListener(MainActivity.this);

        btn_schl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userType="schools";
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

                /*imgView.setVisibility(View.VISIBLE);
                imgView.setAnimation(animeOtherEffect);
                btn_schl_login.setVisibility(View.GONE);
                if(btn_stu_login.getVisibility()==View.GONE)
                {
                    btn_stu_login.setVisibility(View.VISIBLE);
                }*/
            }
        });

        btn_stu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userType = "students";
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                /*imgView.setVisibility(View.VISIBLE);
                imgView.setAnimation(animeOtherEffect);
                btn_stu_login.setVisibility(View.GONE);
                if(btn_schl_login.getVisibility()==View.GONE)
                {
                    btn_schl_login.setVisibility(View.VISIBLE);
                }*/
            }
        });
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
