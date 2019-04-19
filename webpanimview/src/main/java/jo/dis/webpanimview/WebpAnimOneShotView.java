package jo.dis.webpanimview;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * webp 动画播放只播一次
 */
public class WebpAnimOneShotView extends SimpleDraweeView {

    private final String TAG = WebpAnimOneShotView.class.getSimpleName();

    private boolean isRunning = false;
    // 是否设置停止后的最后画面
    private boolean mSetStopLast = false;
    // 提前关闭，否则会返回第一帧
    private int mLastMinus = 0;
    private int repeatTime = 1;//动画播放次数

    public IWebpAnimListener getWebpAnimListener() {
        return webpAnimListener;
    }

    public void setWebpAnimListener(IWebpAnimListener webpAnimListener) {
        this.webpAnimListener = webpAnimListener;
    }

    private IWebpAnimListener webpAnimListener;

    public int getRepeatTime() {
        return repeatTime;
    }

    public void setRepeatTime(int repeatTime) {
        this.repeatTime = repeatTime;
    }

    public WebpAnimOneShotView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public WebpAnimOneShotView(Context context) {
        super(context);
    }

    public WebpAnimOneShotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebpAnimOneShotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public WebpAnimOneShotView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setWebpAnimUri(Uri uri) {
        if (uri == null || isRunning) {
            return;
        }
        setVisibility(VISIBLE);

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (null != animatable) {
                            Log.d(TAG, "showWebpAnim onFinalImageSet");
                            int duration = 0;

                            animatable.start();
                            isRunning = true;

                            if (animatable instanceof AbstractAnimatedDrawable) {
                                duration = ((AbstractAnimatedDrawable) animatable).getDuration();
                            }
                            Log.d(TAG, "showWebpAnim duration=" + duration);
                            if (duration > 0) {
                                postDelayed(new AnimateRunnable(animatable), duration * repeatTime);
                            }

                        } else {
                            stopValuableAnim(null);
                        }
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        Log.d(TAG, "showWebpAnim onFailure");
                        stopValuableAnim(getController().getAnimatable());
                    }
                })
                .setAutoPlayAnimations(true)
                .build();
        setController(controller);
    }

    public void setWebpAnimUriOnce(Uri uri) {
        if (uri == null || isRunning) {
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        super.onFinalImageSet(id, imageInfo, animatable);
                        if (null != animatable) {
                            Log.d(TAG, "showWebpAnim onFinalImageSet");
                            int duration = 0;

                            animatable.start();
                            isRunning = true;

                            if (animatable instanceof AbstractAnimatedDrawable) {
                                duration = ((AbstractAnimatedDrawable) animatable).getDuration();
                            }
                            Log.d(TAG, "showWebpAnim duration=" + duration);
                            if (duration > 0) {
                                postDelayed(new AnimateRunnable(animatable), duration * repeatTime - mLastMinus);
                            }
                        } else {
                            stopValuableAnim(null);
                        }
                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {
                        super.onFailure(id, throwable);
                        Log.d(TAG, "showWebpAnim onFailure");
                        stopValuableAnim(getController().getAnimatable());
                    }
                })
                .setAutoPlayAnimations(false)
                .build();
        setController(controller);
    }

    private final class AnimateRunnable implements Runnable {
        private Animatable animatable;

        public AnimateRunnable(Animatable animatable) {
            this.animatable = animatable;
        }

        @Override
        public void run() {
            stopValuableAnim(animatable);
        }
    }

    private void stopValuableAnim(Animatable animatable) {
        if (!mSetStopLast) setVisibility(GONE);
        isRunning = false;
        if (null != animatable && animatable.isRunning()) {
            Log.d(TAG, "stopValuableAnim ok");
            animatable.stop();
        }
        if (webpAnimListener != null) {
            webpAnimListener.onStop();
        }
    }

    public void setStopLastFrame(boolean bset) {
        mSetStopLast = bset;
        if (bset)
            mLastMinus = 200;
        else
            mLastMinus = 0;

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

    public interface IWebpAnimListener {
        void onStop();
    }
}