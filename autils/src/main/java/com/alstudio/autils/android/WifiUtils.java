package com.alstudio.autils.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * WifiUtils，封装了常用的获取wifi网络信息的方法。如： 获取Mac地址、获取BSSID、获取SSID、 获取附近可用的wifi热点、
 * 获取wifi模块启用状态、获取wifi模块当前状态
 *
 * @author Alonso Lee
 *
 */
public class WifiUtils {


	// 此用于回掉通知获取到扫描结果
	private static WifiScanResultCallback mCallback;

	public static boolean isWifiEnable(Context ctx) {
		WifiManager mWifiManager = AndroidUtils.getWifiManager(ctx);
		if (mWifiManager != null) {
			return mWifiManager.isWifiEnabled();
		}
		return false;
	}

	public static int getWifiState(Context ctx) {
		WifiManager mWifiManager = AndroidUtils.getWifiManager(ctx);
		if (mWifiManager != null) {
			return mWifiManager.getWifiState();
		}
		return -1;
	}

	/**
	 * 获取Mac地址
	 * 
	 * @param ctx
	 * @return null表示当前无法获取到mac地址
	 */
	public static String getMacAddress(Context ctx) {
		WifiManager mWifiManager = AndroidUtils.getWifiManager(ctx);
		if (mWifiManager != null && mWifiManager.getConnectionInfo() != null) {
			return mWifiManager.getConnectionInfo().getMacAddress();
		}
		return "";
	}

	/**
	 * 获取附近的wifi热点
	 * 
	 * @param ctx
	 * @param callback
	 *            接收扫描wifi热点的回掉方法
	 */
	public static void getLocalWifiAP(Context ctx,
			WifiScanResultCallback callback) {
		mCallback = callback;
		startScanWifiAp(ctx);
	}

	/**
	 * 扫描wifi热点
	 * 
	 * @param ctx
	 */
	private static void startScanWifiAp(Context ctx) {
		WifiManager mWifiManager = AndroidUtils.getWifiManager(ctx);

		if (mWifiManager != null) {
			if (mWifiManager.isWifiEnabled()) {
				// 注册监听wifi扫描结果的接收器
				registerWifiScanResultReceiver(ctx);
				// 开始扫描热点
				if (mWifiManager.startScan()) {
				} else {
					unregisterWifiScanResultReceiver(ctx);
				}
			} else {
			}
		} else {
		}
	}

	/**
	 * 获取当前bssid信息
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getCurrentBssid(Context ctx) {
		WifiManager mWifiManager = AndroidUtils.getWifiManager(ctx);
		String bssid = null;
		if (mWifiManager != null) {
			if (mWifiManager.isWifiEnabled()) {
				WifiInfo mInfo = mWifiManager.getConnectionInfo();
				if (mInfo != null) {
					bssid = mInfo.getBSSID();
				} else {
				}
			} else {
			}
		} else {
		}
		return bssid;
	}

	/**
	 * 获取当前wifi链接的ssid
	 * 
	 * @param ctx
	 * @return
	 */
	public static String getCurrentSsid(Context ctx) {

		WifiManager mWifiManager = AndroidUtils.getWifiManager(ctx);
		String ssid = null;
		if (mWifiManager != null) {
			if (mWifiManager.isWifiEnabled()) {
				WifiInfo mInfo = mWifiManager.getConnectionInfo();
				if (mInfo != null) {
					ssid = mInfo.getSSID();
				}
			}
		}
		return ssid;

	}

	private static BroadcastReceiver wifiScanResultReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			List<ScanResult> result = parseWifiScanResult(context, intent);
			if (mCallback != null) {
				mCallback.onGetWifiScanResult(result);
			}
		}
	};

	/**
	 * 注册wifi扫描结果广播接收器
	 * 
	 * @param ctx
	 */
	private static void registerWifiScanResultReceiver(Context ctx) {
		ctx.registerReceiver(wifiScanResultReceiver, new IntentFilter(
				WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}

	/**
	 * 接触wifi扫描结果广播接收器
	 * 
	 * @param ctx
	 */
	private static void unregisterWifiScanResultReceiver(Context ctx) {
		//Mylog.d(TAG, "unregister wifi scan result receiver");
		ctx.unregisterReceiver(wifiScanResultReceiver);
	}

	/**
	 * 解析扫描结果
	 * 
	 * @param context
	 * @param intent
	 * @return
	 */
	private static List<ScanResult> parseWifiScanResult(Context context,
			Intent intent) {
		WifiManager mWifiManager = AndroidUtils.getWifiManager(context);
		List<ScanResult> list = null;
		unregisterWifiScanResultReceiver(context);
		if (mWifiManager != null) {
			list = mWifiManager.getScanResults();
			int size = list.size();

			if (size == 0) {
				return null;
			}

			ScanResult info = null;

			for (int i = 0; i < size; i++) {
				info = list.get(i);
				if (info != null) {
				}
			}
		}
		return list;
	}

	public interface WifiScanResultCallback {
		public void onGetWifiScanResult(List<ScanResult> resultList);
	}

}
