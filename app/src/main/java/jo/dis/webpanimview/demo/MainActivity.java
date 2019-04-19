package jo.dis.webpanimview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jo.dis.webpanimview.R;
import jo.dis.webpanimview.WebpAnimOneShotView;
import jo.dis.webpanimview.WebpAnimView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebpAnimView webpAnimView = findViewById(R.id.webp_anim_view);
        webpAnimView.setWebpAnimRes(R.drawable.home_game_pop_anim);

        WebpAnimOneShotView webpAnimOneShotView = findViewById(R.id.webp_anim_one_shot_view);
        webpAnimOneShotView.setStopLastFrame(true);
        webpAnimOneShotView.setWebpAnimRes(R.drawable.game_over_anim_win);
    }
}
