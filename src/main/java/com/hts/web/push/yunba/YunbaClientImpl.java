package com.hts.web.push.yunba;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.LineParser;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("HTSYunbaClient")
public class YunbaClientImpl implements YunbaClient {

	private static Logger log = Logger.getLogger(YunbaClientImpl.class);
	
	private static final String HOST = "http://rest.yunba.io";
	private static final Integer PORT = 8080;
	private static final String CONNECT_URL = HOST + ":" + PORT;
	private static final int MAX_CONNECTION = 150; //最大连接数
	private static final int MAX_ROUTE = 40; //目标主机的最大连接数
	private static final int MAX_PER_ROUTE = 20; //每个路由基础的连接
	
	public static void main(String[] args) {
		System.out.println(CONNECT_URL);
	}
	
	private PoolingHttpClientConnectionManager connManager;
	private RequestConfig defaultRequestConfig;
	private RequestConfig commonTopicRequestConfig;
	
	@Value("${yunba.appkey}")
	private String appkey;
	
	@Value("${yunba.seckey}")
	private String seckey;
	
	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getSeckey() {
		return seckey;
	}

	public void setSeckey(String seckey) {
		this.seckey = seckey;
	}

	public YunbaClientImpl() {
		init();
	}

	/**
	 * 初始化请求配置和http连接池
	 */
	private void init() {
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

			@Override
			public HttpMessageParser<HttpResponse> create(
					SessionInputBuffer buffer, MessageConstraints constraints) {
				LineParser lineParser = new BasicLineParser() {

					@Override
					public Header parseHeader(final CharArrayBuffer buffer) {
						try {
							return super.parseHeader(buffer);
						} catch (ParseException ex) {
							return new BasicHeader(buffer.toString(), null);
						}
					}
				};
				return new DefaultHttpResponseParser(buffer, lineParser,
						DefaultHttpResponseFactory.INSTANCE, constraints) {

					@Override
					protected boolean reject(final CharArrayBuffer line,
							int count) {
						return false;
					}

				};
			}
		};

		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
				requestWriterFactory, responseParserFactory);

		SSLContext sslcontext = SSLContexts.createSystemDefault();
		X509HostnameVerifier hostnameVerifier = new BrowserCompatHostnameVerifier();

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext,
								hostnameVerifier))
								.build();

		connManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry, connFactory);

		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
		connManager.setDefaultSocketConfig(socketConfig);
		connManager.setSocketConfig(new HttpHost(HOST, PORT), socketConfig);

		MessageConstraints messageConstraints = MessageConstraints.custom()
				.setMaxHeaderCount(200)
				.setMaxLineLength(3000)
				.build();
		
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE)
				.setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setConnectionConfig(new HttpHost(HOST, PORT),
				ConnectionConfig.DEFAULT);
		connManager.setMaxTotal(MAX_CONNECTION);
		connManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		connManager.setMaxPerRoute(new HttpRoute(new HttpHost(HOST, PORT)), MAX_ROUTE);
		
		defaultRequestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BEST_MATCH)
				.setExpectContinueEnabled(true)
				.setStaleConnectionCheckEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.setSocketTimeout(5000)
				.setConnectTimeout(5000)
				.setConnectionRequestTimeout(5000)
				.build();
		
		
		commonTopicRequestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.BEST_MATCH)
				.setExpectContinueEnabled(true)
				.setStaleConnectionCheckEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.setSocketTimeout(60*60*1000)
				.setConnectTimeout(60*60*1000)
				.setConnectionRequestTimeout(60*60*1000)
				.build();
	}

	/**
	 * 
	 * @return
	 */
	private CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpclient = HttpClients.custom()
				.setConnectionManager(connManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();
		return httpclient;
	}
	
	private void emit(String method, String topicAlias, String topic, String msg,
			JSONObject apnsJSON) throws YunbaException, ClientProtocolException, IOException, JSONException {
		emit(method, topicAlias, topic, msg,
				apnsJSON, defaultRequestConfig);
	}
	
	private void emit2CommonTopic(String method, String topicAlias, String topic, String msg,
			JSONObject apnsJSON) throws YunbaException, ClientProtocolException, IOException, JSONException{
		emit(method, topicAlias, topic, msg,
				apnsJSON, commonTopicRequestConfig);
	}

	private void emit(String method, String topicAlias, String topic, String msg,
			JSONObject apnsJSON, RequestConfig requestConfig) throws YunbaException, ClientProtocolException, IOException, JSONException {
		CloseableHttpClient httpclient = getHttpClient();
		HttpPost httppost = new HttpPost(CONNECT_URL);
		httppost.setHeader("Content-type", "application/json");
		httppost.setConfig(requestConfig);
		JSONObject json = new JSONObject();
		JSONObject opts = new JSONObject();
		json.put("method", method);
		json.put("appkey", appkey);
		json.put("seckey",seckey);
		json.put(topicAlias, topic);
		json.put("msg", msg);

		json.put("opts", opts);
		opts.put("qos", 1);
		if (apnsJSON != null) {
			opts.put("apn_json", apnsJSON);
		}
		String reqStr = json.toString();
		HttpEntity reqEntity = new StringEntity(reqStr, Consts.UTF_8);
		httppost.setEntity(reqEntity);
		CloseableHttpResponse response = httpclient.execute(httppost);;
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					JSONObject res = new JSONObject(EntityUtils.toString(
							entity, Consts.UTF_8));
					int pubStatus = res.getInt("status");
					if(pubStatus != 0) {
						String errmsg;
						switch (pubStatus) {
						case 1:
							errmsg = "参数错误";
							break;
						case 2:
							errmsg = "内部服务错误";
							break;
						case 3:
							errmsg = "没有应用";
							break;
						case 4:
							errmsg = "发布超时";
							break;
						case 5:
							errmsg = "topic:" + topic + "未被订阅";
							break;
						default:
							errmsg = "未知错误";
							break;
						}
						throw new YunbaException(errmsg + ", status=" + pubStatus);
					} else {
//						log.info(reqStr); // 打印消息日志信息
					}
				}
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		} finally {
			response.close();
		}
	}
  
	@Override
	public void publishToAlias(String toAlias, String msg, JSONObject apnsJSON) throws YunbaException{
		try {
			emit("publish_to_alias", "alias", toAlias, msg, apnsJSON);
		} catch(Exception e) {
			throw new YunbaException(e);
		}
	}

	@Override
	public void publishToAlias(String toAlias, String msg) throws YunbaException{
		try {
			emit("publish_to_alias", "alias", toAlias, msg, null);
		} catch(Exception e) {
			throw new YunbaException(e);
		}
	}

	@Override
	public void publishToCommonTopic(String toTopic, String msg, JSONObject apnsJSON) throws YunbaException{
		try {
			emit2CommonTopic("publish", "topic", toTopic, msg, apnsJSON);
		} catch(Exception e) {
			throw new YunbaException(e);
		}
	}
}
