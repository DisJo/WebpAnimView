package jo.dis.webpanimview.demo;

import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(this));
    }
}
