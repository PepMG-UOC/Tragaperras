package com.example.tragaperras;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tragaperras.ImageViewScrolling.IEventEnd;
import com.example.tragaperras.ImageViewScrolling.ImageViewScrolling;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements IEventEnd {

    ImageView btn_up,btn_down;
    ImageViewScrolling image,image2,image3;
    TextView txt_score;

    int count_done=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        btn_down = (ImageView) findViewById(R.id.btn_down);
        btn_up = (ImageView) findViewById(R.id.btn_up);

        image = (ImageViewScrolling) findViewById(R.id.image);
        image2 = (ImageViewScrolling) findViewById(R.id.image2);
        image3 = (ImageViewScrolling) findViewById(R.id.image3);

        txt_score = (TextView) findViewById(R.id.txt_score);

        //el evento
        image.setEventEnd(MainActivity.this);
        image2.setEventEnd(MainActivity.this);
        image3.setEventEnd(MainActivity.this);

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Common.SCORE >= 50 ) // Precio minimo de partida
                {
                    btn_up.setVisibility(View.GONE);
                    btn_down.setVisibility(View.VISIBLE);

                    image.setValorRandom(new Random().nextInt(6),new Random().nextInt((15-5)+1)+5);
                    image2.setValorRandom(new Random().nextInt(6),new Random().nextInt((15-5)+1)+5);
                    image3.setValorRandom(new Random().nextInt(6),new Random().nextInt((15-5)+1)+5);

                    Common.SCORE-= 50;
                    txt_score.setText(String.valueOf(Common.SCORE));
                }
                else {
                    Toast.makeText(MainActivity.this,"No tienes suficiente dinero", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void eventEnd(int result, int count) {
        if(count_done<2)
            count_done++;
        else {
            btn_down.setVisibility(View.GONE);
            btn_up.setVisibility(View.VISIBLE);

            count_done =0;
            
            if(image.getValue()==image2.getValue() && image2.getValue()==image3.getValue()){
                Toast.makeText(this,"Has ganado un premio grande",Toast.LENGTH_SHORT).show();
                Common.SCORE +=300;
                txt_score.setText(String.valueOf(Common.SCORE));
            } else if (image.getValue()==image2.getValue() || image2.getValue()==image3.getValue()) {
                Toast.makeText(this,"Has ganado un premio pequeño",Toast.LENGTH_SHORT).show();
                Common.SCORE +=300;
                txt_score.setText(String.valueOf(Common.SCORE));
            } else {
                Toast.makeText(this,"Has perdido",Toast.LENGTH_SHORT).show();
            }

        }
    }
}