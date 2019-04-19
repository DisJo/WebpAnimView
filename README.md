# WebpAnimView

xml
____

```
    <jo.dis.webpanimview.WebpAnimView
        android:id="@+id/webp_anim_view"
        android:layout_width="34dp"
        android:layout_height="70dp"/>
    
    <jo.dis.webpanimview.WebpAnimOneShotView
        android:id="@+id/webp_anim_one_shot_view"
        android:layout_width="300dp"
        android:layout_height="130dp"/>
```

java
----

```
    WebpAnimView webpAnimView = findViewById(R.id.webp_anim_view);
    webpAnimView.setWebpAnimRes(R.drawable.home_game_pop_anim);

    WebpAnimOneShotView webpAnimOneShotView = findViewById(R.id.webp_anim_one_shot_view);
    webpAnimOneShotView.setStopLastFrame(true);
    webpAnimOneShotView.setWebpAnimRes(R.drawable.game_over_anim_win);
```
