package hust.crawler;

import org.apache.http.client.HttpClient;
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

	
}
