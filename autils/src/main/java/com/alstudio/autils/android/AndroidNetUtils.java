package com.alstudio.autils.android;

import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class AndroidNetUtils {

    public static HttpURLConnection buildHttpUrlConnectionUsingGetMethod(
            String urlString, HashMap<String, String> headers)
            throws IOException {

        disableConnectionReuseIfNecessary();

        HttpURLConnection conn = buildHttpUrlConnectionWithGet(urlString);

        if (headers != null && headers.size() > 0) {

            Set<String> keySet = headers.keySet();

            for (String s : keySet) {
                conn.setRequestProperty(s, headers.get(s));
            }
        }

        return conn;
    }

    /**
     * 创建一个GET方法的HTTP短连接
     *
     * @param urlString
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static HttpURLConnection buildHttpUrlConnectionWithGet(String urlString)
            throws MalformedURLException, IOException {
        disableConnectionReuseIfNecessary();

        URL url = new URL(urlString);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");

        conn.setRequestProperty("keepAlive", "false");
        conn.setRequestProperty("Connection", "close");

        return conn;
    }


    /**
     * 防止http请求重用
     */
    public static void disableConnectionReuseIfNecessary() {
        System.setProperty("http.keepAlive", "false");
    }

    /**
     * 创建一个socket连接。缺省超时时间为10S
     *
     * @param ip
     * @param port
     * @return
     * @throws IOException
     */
    public static Socket buildSocketConnection(String ip, String port)
            throws IOException {

        if (TextUtils.isEmpty(ip)) {
            throw new IllegalArgumentException("ip address should not null");
        }

        if (TextUtils.isEmpty(port)) {
            throw new IllegalArgumentException("ip address should not null");
        }

        if (!TextUtils.isDigitsOnly(port)) {
            throw new IllegalArgumentException(
                    "port should just need digits only");
        }

        InetSocketAddress sa = new InetSocketAddress(ip, Integer.parseInt(port));

        Socket socket = new Socket();

        try {
            socket.connect(sa, 10000);
        } catch (AssertionError e1) {

        } catch (Exception e) {

        }
        return socket;
    }

    /**
     * 创建一个自定义超时时间的socket连接
     * @param ip
     * @param port
     * @param timeout
     * @return
     * @throws NumberFormatException
     * @throws IOException
     */
    public static Socket buildSocketConnection(String ip, String port,
                                               String timeout) throws NumberFormatException, IOException {

        if (TextUtils.isEmpty(ip)) {
            throw new IllegalArgumentException("ip address should not null");
        }

        if (TextUtils.isEmpty(port)) {
            throw new IllegalArgumentException("ip address should not null");
        }

        if (TextUtils.isEmpty(timeout)) {
            throw new IllegalArgumentException("ip address should not null");
        }

        if (!TextUtils.isDigitsOnly(port)) {
            throw new IllegalArgumentException(
                    "port should just need digits only");
        }

        if (!TextUtils.isDigitsOnly(timeout)) {
            throw new IllegalArgumentException(
                    "timeout should just need digits only");
        }

        InetSocketAddress sa = new InetSocketAddress(ip, Integer.parseInt(port));

        Socket socket = new Socket();
        try {
            socket.connect(sa, Integer.parseInt(timeout));
        } catch (AssertionError e1) {

        } catch (Exception e) {

        }
        return socket;
    }


}
