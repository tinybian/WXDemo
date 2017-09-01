package com.weex.sample;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.taobao.weex.WXEnvironment;
import com.taobao.weex.adapter.IWXImgLoaderAdapter;
import com.taobao.weex.common.WXImageStrategy;
import com.taobao.weex.dom.WXImageQuality;
import com.weex.sample.common.Constant;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by lixinke on 16/6/1.
 *
 */
public class ImageAdapter implements IWXImgLoaderAdapter {
    private static String TAG = "ImageAdapter";
	private Timer timer = new Timer();
    @Override
    public void setImage(String url, ImageView view, WXImageQuality quality, WXImageStrategy strategy) {
	    int tactics = PreferenceManager.getDefaultSharedPreferences(WXApplication.getInstance())
	            .getInt(Constant.PREF_IMAGE_CACHE, Constant.USE_CACHE);
	    Log.d(TAG, "cache = " + tactics);

	    if (tactics == Constant.USE_CACHE) {
		    Glide.with(WXEnvironment.getApplication())
				    .setDefaultRequestOptions(new RequestOptions()
						    .diskCacheStrategy(DiskCacheStrategy.DATA)
						    .skipMemoryCache(false))
				    .load(url)
				    .into(view);
	    } else if (tactics == Constant.NO_CACHE) {
		    Glide.with(WXEnvironment.getApplication())
				    .setDefaultRequestOptions(new RequestOptions()
						    .diskCacheStrategy(DiskCacheStrategy.NONE)
						    .skipMemoryCache(true))
				    .load(url)
				    .into(view);
	    } else if (tactics == Constant.NATIVE_METHOD) {
//		    Glide.with(WXEnvironment.getApplication())
//				    .setDefaultRequestOptions(new RequestOptions()
//						    .diskCacheStrategy(DiskCacheStrategy.NONE)
//						    .skipMemoryCache(true))
//				    .load(url)
//				    .into(view);
		    new MyTask().execute(url, view);
	    }
    }

    private class MyTask extends AsyncTask<Object, Integer, ImageView> {
	    private RequestBuilder<Drawable> builder;
	    @Override
	    protected ImageView doInBackground(Object... params) {
		    try {
			    Thread.sleep((long)new Random().nextInt(80));
		    } catch (InterruptedException e) {
			    e.printStackTrace();
		    }
		    builder = Glide.with(WXEnvironment.getApplication())
				    .setDefaultRequestOptions(new RequestOptions()
						    .diskCacheStrategy(DiskCacheStrategy.NONE)
						    .skipMemoryCache(true))
				    .load(params[0]);
		    return (ImageView) params[1];
	    }

	    @Override
	    protected void onPostExecute(ImageView view) {
			builder.into(view);
	    }
    }
}
