package com.github.obase.webc.config;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.github.obase.WrappedException;
import com.github.obase.kit.StringKit;
import com.github.obase.webc.AuthType;

@JacksonXmlRootElement(localName = WebcConfig.ROOT)
public class WebcConfig {

	public static final String ROOT = "webc";
	public static final String NAMESPACE = "namespace";
	public static final String CONTEXT_CONFIG_LOCATION = ContextLoader.CONFIG_LOCATION_PARAM;
	public static final String ASYNC_LISTENER = "asyncListener";
	public static final String TIMEOUT_SECOND = "timeoutSecond"; // seconds
	public static final String SEND_ERROR = "sendError";
	public static final String CONTROL_PREFIX = "controlPrefix";
	public static final String CONTROL_SUFFIX = "controlSuffix";
	public static final String CONTROL_PROCESSOR = "controlProcessor";
	public static final String WSID_TOKEN_BASE = "wsidTokenBase";
	public static final String DEFAULT_AUTH_TYPE = "defaultAuthType";
	public static final String REFERER_DOMAIN = "refererDomain";

	public boolean withoutApplicationContext;
	public boolean withoutServletContext;
	public boolean withoutServiceContext;
	public String contextConfigLocation;

	@JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty(value = "servlet")
	public final List<FilterInitParam> servlets = new LinkedList<FilterInitParam>();

	@JacksonXmlElementWrapper(useWrapping = false)
	@JsonProperty(value = "service")
	public final List<FilterInitParam> services = new LinkedList<FilterInitParam>();

	public static class FilterInitParam {
		public String namespace;
		public String contextConfigLocation;
		public String asyncListener;
		public int timeoutSecond;
		public boolean sendError;
		public String controlProcessor;
		public String controlPrefix; // multi values by comma
		public String controlSuffix;// multi values by comma
		public int wsidTokenBase; // BKDRHash的base,默认为0
		public AuthType defaultAuthType;
		public String refererDomain; // multi values by comma
	}

	public static void encodeContextInitParam(ServletContext servletContext, WebcConfig config) {
		if (StringKit.isNotEmpty(config.contextConfigLocation)) {
			servletContext.setInitParameter(CONTEXT_CONFIG_LOCATION, config.contextConfigLocation);
		}
	}

	/**
	 * default init params and merge default values
	 */
	public static FilterInitParam decodeFilterInitParam(javax.servlet.FilterConfig filterConfig) {

		FilterInitParam ret = new FilterInitParam();
		ret.namespace = getStringParam(filterConfig, NAMESPACE, null);
		ret.contextConfigLocation = getStringParam(filterConfig, CONTEXT_CONFIG_LOCATION, null);
		ret.asyncListener = getStringParam(filterConfig, ASYNC_LISTENER, null);
		ret.timeoutSecond = getIntParam(filterConfig, TIMEOUT_SECOND, 0);
		ret.sendError = getBooleanParam(filterConfig, SEND_ERROR, false);
		ret.controlProcessor = getStringParam(filterConfig, CONTROL_PROCESSOR, null);
		ret.controlPrefix = getStringParam(filterConfig, CONTROL_PREFIX, null);
		ret.controlSuffix = getStringParam(filterConfig, CONTROL_SUFFIX, null);
		ret.wsidTokenBase = getIntParam(filterConfig, WSID_TOKEN_BASE, 0);
		String authTypeStr = getStringParam(filterConfig, DEFAULT_AUTH_TYPE, null);
		if (authTypeStr != null) {
			ret.defaultAuthType = AuthType.valueOf(authTypeStr);
		}
		ret.refererDomain = getStringParam(filterConfig, REFERER_DOMAIN, null);

		return ret;
	}

	public static void encodeFilterInitParam(FilterRegistration.Dynamic dynamic, FilterInitParam param) {

		if (StringKit.isNotEmpty(param.namespace)) {
			dynamic.setInitParameter(NAMESPACE, param.namespace);
		}

		if (StringKit.isNotEmpty(param.contextConfigLocation)) {
			dynamic.setInitParameter(CONTEXT_CONFIG_LOCATION, param.contextConfigLocation);
		}

		if (param.asyncListener != null) {
			dynamic.setInitParameter(ASYNC_LISTENER, param.asyncListener);
		}

		if (param.timeoutSecond != 0) {
			dynamic.setInitParameter(TIMEOUT_SECOND, String.valueOf(param.timeoutSecond));
		}

		if (param.sendError) {
			dynamic.setInitParameter(SEND_ERROR, String.valueOf(param.sendError));
		}

		if (param.controlProcessor != null) {
			dynamic.setInitParameter(CONTROL_PROCESSOR, param.controlProcessor);
		}

		if (StringKit.isNotEmpty(param.controlPrefix)) {
			dynamic.setInitParameter(CONTROL_PREFIX, param.controlPrefix);
		}

		if (StringKit.isNotEmpty(param.controlSuffix)) {
			dynamic.setInitParameter(CONTROL_PREFIX, param.controlSuffix);
		}

		if (param.wsidTokenBase != 0) {
			dynamic.setInitParameter(WSID_TOKEN_BASE, String.valueOf(param.wsidTokenBase));
		}

		if (param.defaultAuthType != null) {
			dynamic.setInitParameter(DEFAULT_AUTH_TYPE, param.defaultAuthType.name());
		}
		if (param.refererDomain != null) {
			dynamic.setInitParameter(REFERER_DOMAIN, param.refererDomain);
		}
	}

	public static final boolean getBooleanParam(javax.servlet.FilterConfig filterConfig, String name, boolean def) {
		String param = filterConfig.getInitParameter(name);
		if (StringKit.isEmpty(param)) {
			return def;
		}
		return Boolean.parseBoolean(param);
	}

	public static final int getIntParam(javax.servlet.FilterConfig filterConfig, String name, int def) {
		String param = filterConfig.getInitParameter(name);
		if (StringKit.isEmpty(param)) {
			return def;
		}
		return Integer.parseInt(param);
	}

	public static final String getStringParam(javax.servlet.FilterConfig filterConfig, String name, String def) {
		String param = filterConfig.getInitParameter(name);
		if (StringKit.isEmpty(param)) {
			return def;
		}
		return param;
	}

	public static final Class<?> getClassParam(javax.servlet.FilterConfig filterConfig, String name, Class<?> def) {
		String param = filterConfig.getInitParameter(name);
		if (StringKit.isEmpty(param)) {
			return def;
		}
		try {
			return Class.forName(param);
		} catch (ClassNotFoundException e) {
			throw new WrappedException(e);
		}
	}

}
