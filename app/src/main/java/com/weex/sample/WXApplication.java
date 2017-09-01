package com.weex.sample;

import android.app.Application;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.taobao.weex.InitConfig;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.common.WXException;
import com.weex.sample.extend.compontent.RichText;
import com.weex.sample.extend.module.PhoneInfoModule;

/**
 * 注意要在Manifest中启用
 * 参考manifest，否则会抛出ExceptionInInitializerError
 * 要实现ImageAdapter 否则图片不能下载
 * gradle 中一定要添加一些依赖，否则初始化会失败。
 * compile 'com.android.support:recyclerview-v7:23.1.1'
 * compile 'com.android.support:support-v4:23.1.1'
 * compile 'com.android.support:appcompat-v7:23.1.1'
 * compile 'com.alibaba:fastjson:1.1.45'
 */
public class WXApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    INSTANCE = this;
    InitConfig config = new InitConfig.Builder().setImgAdapter(new ImageAdapter()).build();
    WXSDKEngine.initialize(this, config);
    try {
      WXSDKEngine.registerModule("poneInfo", PhoneInfoModule.class);
      WXSDKEngine.registerComponent("rich", RichText.class, false);
    } catch (WXException e) {
      e.printStackTrace();
    }
  }
	public boolean useExtensionRenderers() {
		return BuildConfig.FLAVOR.equals("withExtensions");
	}

  public DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
    return new DefaultDataSourceFactory(this, bandwidthMeter,
            buildHttpDataSourceFactory(bandwidthMeter));
  }

  public HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
    return new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "ExoPlayerDemo"), bandwidthMeter);
  }

  private static WXApplication INSTANCE;
  public static WXApplication getInstance() {
    return INSTANCE;
  }
}
