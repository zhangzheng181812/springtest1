package com.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpsUtils {

    private static CloseableHttpClient httpclient;

    static {
        try {
            // enable ssl
            TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };

            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

            // 关闭主机名验证
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslSocketFactory).build();

            // 连接池连接管理
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            connectionManager.setMaxTotal(200);
            connectionManager.setDefaultMaxPerRoute(200);

            httpclient = HttpClients.custom()
                    .setConnectionManager(connectionManager)
                    .evictExpiredConnections()
                    .evictIdleConnections(15L, TimeUnit.SECONDS)
                    .disableAutomaticRetries()
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String doGet(String url){
        HttpGet httpGet;
        String result = null;
        try{
            httpGet = new HttpGet(url);
            HttpResponse execute = httpclient.execute(httpGet);
            if(execute!=null){
                HttpEntity res = execute.getEntity();
                if(res!=null){
                    result = EntityUtils.toString(res,"utf-8");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    public static String sendPost(String url, String json, Map<String,String> requestHeaders) {
//        LOGGER.info("xmlStr | {}", xmlStr);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            for (Map.Entry<String,String> o : requestHeaders.entrySet()) {
                httppost.setHeader(o.getKey(),o.getValue());
            }

            StringEntity sendEntity = new StringEntity(json, "UTF-8");
            httppost.setEntity(sendEntity);

            //设置请求参数 连接超时3S 从连接池获取连接超时1S 读取超时10S
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(8000).setConnectionRequestTimeout(5000)
                    .setSocketTimeout(120000).build();
            httppost.setConfig(requestConfig);

            response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {

        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;

    }

}
