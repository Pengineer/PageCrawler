package hust.test;

import hust.crawler.Crawler;

import java.io.IOException;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

/**
 * HttpClient是一个实现了http协议的开源Java客户端工具库，可以通过程序发送http请求。
 * HttpClient最重要的功能是执行HTTP方法。一个HTTP方法的执行包含一个或多个HTTP请求/HTTP响应交换，通常由HttpClient的内部来处理。
 * 而期望用户提供一个要执行的请求对象，而HttpClient期望传输请求到目标服务器并返回对应的响应对象，或者当执行不成功时抛出异常。
 * 
 * HttpClient的使用方法：
 * 
 * 1. 创建HttpClient对象。
 * 2. 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
 * 3. 如果需要发送请求参数，可调用HttpGet、HttpPost共同的setParams(HetpParams params)方法来添加请求参数；对于HttpPost对象而言，也可调用setEntity(HttpEntity entity)方法来设置请求参数。
 * 4. 调用HttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个HttpResponse。
 * 5. 调用HttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头；调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容。
 * 6. 释放连接。无论执行方法是否成功，都必须释放连接。
 * 
 * @author liangjian
 *
 */

public class Test {
	
	private static String hostname = "isisn.nsfc.gov.cn";
	private static int port = 443;
	private static String scheme = "https";
	
	
	public static void main(String[] args) throws Exception {
		HttpClient client = Crawler.getHttpsClient();
		
		HttpGet request = new HttpGet("/egrantindex/funcindex/prjsearch-list");
		HttpHost target = new HttpHost(hostname, port, scheme);
		
		String html = "";
		HttpEntity entity = null;
		try {
			HttpResponse response = client.execute(target, request);
			System.out.println(response.getStatusLine());
			entity = response.getEntity();
			html = EntityUtils.toString(entity, Consts.UTF_8).trim();
			System.out.println(html);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			EntityUtils.consume(entity);
		}
	}
}
