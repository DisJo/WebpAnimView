package jo.dis.webpanimview;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * webp 动画播放
 */
public class WebpAnimView extends SimpleDraweeView {

    public WebpAnimView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public WebpAnimView(Context context) {
        super(context);
    }

    public WebpAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebpAnimView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WebpAnimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setWebpAnimUrl(String url) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setOldController(getController())
                .setAutoPlayAnimations(true)
                .build();
        setController(controller);
    }

    public void setWebpAnimRes(int resId) {
        if (resId <= 0) return;
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        setWebpAnimUri(uri);
    }

    public void setWebpAnimUri(Uri uri){
        if(uri == null){
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(getController())
                .setAutoPlayAnimations(true)
                .build();
        setController(controller);
    }

    public void setResizeImageUri(Uri uri, int width, int height) {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(getController())
                .setAutoPlayAnimations(true)
                .build();
        setController(controller);
    }

    public void setWebpAnimUrl(String url, ControllerListener<? super ImageInfo> listener) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(url))
                .setOldController(getController())
                .setControllerListener(listener)
                .build();
        setController(controller);
    }

    public void setWebpAnimUrl(Uri uri, ControllerListener<? super ImageInfo> listener) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(getController())
                .setControllerListener(listener)
                .build();
        setController(controller);
    }

}