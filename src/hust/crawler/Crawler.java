package hust.crawler;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;

public class Crawler {
	
	/**
	 * Http连接池：多连接的线程安全的管理器 ，适合多线程环境
	 */
	protected static PoolingClientConnectionManager cm;

	protected static HttpClient httpClient;
	
	/**
	 * 获取HttpClient实例
	 * @return httpClient
	 */
	public static HttpClient getHttpClient() {
		if (httpClient == null) {
			cm = new PoolingClientConnectionManager();
			httpClient = new DefaultHttpClient(cm);
			
			//客户端总并行连接最大数
			cm.setMaxTotal(200);
			//每个主机（URL）的最大并行连接数
			cm.setDefaultMaxPerRoute(100);
			
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000); //设置等待数据超时时间1分钟
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000); //设置请求超时1分钟
		}
		return httpClient;
	}

	private static X509TrustManager tm = new X509TrustManager() {  
		public void checkClientTrusted(X509Certificate[] xcs, String string) 
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] xcs, String string)  
				throws CertificateException {  
		}  
  
		public X509Certificate[] getAcceptedIssuers() {  
			return null;  
		}  
	};
	
	/**  
	 * 获取一个针对https的HttpClient  
	 */  
	public static HttpClient getHttpsClient() throws KeyManagementException, NoSuchAlgorithmException {  
		HttpClient httpclient = getHttpClient();  
		SSLContext sslcontext = SSLContext.getInstance("TLS");  
		sslcontext.init(null, new TrustManager[] { tm }, null);  
		SSLSocketFactory ssf = new SSLSocketFactory(sslcontext,  
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
		httpclient.getConnectionManager().getSchemeRegistry()  
				.register(new Scheme("https", 443, ssf));  
		return httpclient;  
	}
}
