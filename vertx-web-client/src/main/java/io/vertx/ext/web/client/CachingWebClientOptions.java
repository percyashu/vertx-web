/*
 * Copyright 2021 Red Hat, Inc.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  and Apache License v2.0 which accompanies this distribution.
 *
 *  The Eclipse Public License is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  The Apache License v2.0 is available at
 *  http://www.opensource.org/licenses/apache2.0.php
 *
 *  You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.ext.web.client;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.json.annotations.JsonGen;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.Http2Settings;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpVersion;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:craigday3@gmail.com">Craig Day</a>
 */
@DataObject
@JsonGen(publicConverter = false)
public class CachingWebClientOptions extends WebClientOptions {

  public static final Set<Integer> DEFAULT_CACHED_STATUS_CODES = buildDefaultStatusCodes();
  public static final Set<HttpMethod> DEFAULT_CACHED_METHODS = buildDefaultMethods();

  private boolean enableVaryCaching = false;
  private Set<Integer> cachedStatusCodes = DEFAULT_CACHED_STATUS_CODES;
  private Set<HttpMethod> cachedMethods = DEFAULT_CACHED_METHODS;

  public CachingWebClientOptions() {
  }

  public CachingWebClientOptions(boolean enableVaryCaching) {
    this.enableVaryCaching = enableVaryCaching;
  }

  public CachingWebClientOptions(WebClientOptions other) {
    super(other);
  }

  public CachingWebClientOptions(JsonObject json) {
    super(json);
    CachingWebClientOptionsConverter.fromJson(json, this);
  }

  void init(CachingWebClientOptions other) {
    super.init(other);
    this.enableVaryCaching = other.enableVaryCaching;
    this.cachedStatusCodes = other.cachedStatusCodes;
    this.cachedMethods = other.cachedMethods;
  }

  /**
   * Convert to JSON
   *
   * @return the JSON
   */
  public JsonObject toJson() {
    JsonObject json = super.toJson();
    CachingWebClientOptionsConverter.toJson(this, json);
    return json;
  }

  /**
   * Configure the client cache behavior for {@code Vary} responses.
   *
   * @param enabled true to enable caching varying responses
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions setEnableVaryCaching(boolean enabled) {
    this.enableVaryCaching = enabled;
    return this;
  }

  /**
   * @return the set of status codes to consider cacheable.
   */
  public Set<Integer> getCachedStatusCodes() {
    return cachedStatusCodes;
  }

  /**
   * Configure the status codes that can be cached.
   *
   * @param codes the cacheable status code numbers
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions setCachedStatusCodes(Set<Integer> codes) {
    this.cachedStatusCodes = codes;
    return this;
  }

  /**
   * Add a status code that is cacheable.
   *
   * @param code the additional code number
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions addCachedStatusCode(Integer code) {
    this.cachedStatusCodes.add(code);
    return this;
  }

  /**
   * Remove a status code that is cacheable.
   *
   * @param code the code number to remove
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions removeCachedStatusCode(Integer code) {
    this.cachedStatusCodes.remove(code);
    return this;
  }

  /**
   * @return the set of HTTP methods to consider cacheable.
   */
  public Set<HttpMethod> getCachedMethods() {
    return cachedMethods;
  }

  /**
   * Configure the HTTP methods that can be cached.
   *
   * @param methods the HTTP methods to cache
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions setCachedMethods(Set<HttpMethod> methods) {
    this.cachedMethods = methods;
    return this;
  }

  /**
   * Add an HTTP method that is cacheable.
   *
   * @param method the method to add
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions addCachedMethod(HttpMethod method) {
    this.cachedMethods.add(method);
    return this;
  }

  /**
   * Remove an HTTP method that is cacheable.
   *
   * @param method the method to remove
   * @return a reference to this, so the API can be used fluently
   */
  public CachingWebClientOptions removeCachedMethod(HttpMethod method) {
    this.cachedMethods.remove(method);
    return this;
  }

  /**
   * @return true if the client will cache responses with the {@code Vary} header, false otherwise
   */
  public boolean isVaryCachingEnabled() {
    return enableVaryCaching;
  }

  @Override
  public CachingWebClientOptions setUserAgentEnabled(boolean userAgentEnabled) {
    return (CachingWebClientOptions) super.setUserAgentEnabled(userAgentEnabled);
  }

  @Override
  public CachingWebClientOptions setUserAgent(String userAgent) {
    return (CachingWebClientOptions) super.setUserAgent(userAgent);
  }

  @Override
  public CachingWebClientOptions setFollowRedirects(boolean followRedirects) {
    return (CachingWebClientOptions) super.setFollowRedirects(followRedirects);
  }

  @Override
  public CachingWebClientOptions setMaxRedirects(int maxRedirects) {
    return (CachingWebClientOptions) super.setMaxRedirects(maxRedirects);
  }

  @Override
  public CachingWebClientOptions setSendBufferSize(int sendBufferSize) {
    return (CachingWebClientOptions) super.setSendBufferSize(sendBufferSize);
  }

  @Override
  public CachingWebClientOptions setReceiveBufferSize(int receiveBufferSize) {
    return (CachingWebClientOptions) super.setReceiveBufferSize(receiveBufferSize);
  }

  @Override
  public CachingWebClientOptions setReuseAddress(boolean reuseAddress) {
    return (CachingWebClientOptions) super.setReuseAddress(reuseAddress);
  }

  @Override
  public CachingWebClientOptions setTrafficClass(int trafficClass) {
    return (CachingWebClientOptions) super.setTrafficClass(trafficClass);
  }

  @Override
  public CachingWebClientOptions setTcpNoDelay(boolean tcpNoDelay) {
    return (CachingWebClientOptions) super.setTcpNoDelay(tcpNoDelay);
  }

  @Override
  public CachingWebClientOptions setTcpKeepAlive(boolean tcpKeepAlive) {
    return (CachingWebClientOptions) super.setTcpKeepAlive(tcpKeepAlive);
  }

  @Override
  public CachingWebClientOptions setSoLinger(int soLinger) {
    return (CachingWebClientOptions) super.setSoLinger(soLinger);
  }

  @Override
  public CachingWebClientOptions setIdleTimeout(int idleTimeout) {
    return (CachingWebClientOptions) super.setIdleTimeout(idleTimeout);
  }

  @Override
  public CachingWebClientOptions setIdleTimeoutUnit(TimeUnit idleTimeoutUnit) {
    return (CachingWebClientOptions) super.setIdleTimeoutUnit(idleTimeoutUnit);
  }

  @Override
  public CachingWebClientOptions setSsl(boolean ssl) {
    return (CachingWebClientOptions) super.setSsl(ssl);
  }

  @Override
  public CachingWebClientOptions setKeyCertOptions(KeyCertOptions options) {
    return (CachingWebClientOptions) super.setKeyCertOptions(options);
  }

  @Override
  public CachingWebClientOptions setTrustOptions(TrustOptions options) {
    return (CachingWebClientOptions) super.setTrustOptions(options);
  }

  @Override
  public CachingWebClientOptions addEnabledCipherSuite(String suite) {
    return (CachingWebClientOptions) super.addEnabledCipherSuite(suite);
  }

  @Override
  public CachingWebClientOptions addCrlPath(String crlPath) throws NullPointerException {
    return (CachingWebClientOptions) super.addCrlPath(crlPath);
  }

  @Override
  public CachingWebClientOptions addCrlValue(Buffer crlValue) throws NullPointerException {
    return (CachingWebClientOptions) super.addCrlValue(crlValue);
  }

  @Override
  public CachingWebClientOptions setConnectTimeout(int connectTimeout) {
    return (CachingWebClientOptions) super.setConnectTimeout(connectTimeout);
  }

  @Override
  public CachingWebClientOptions setTrustAll(boolean trustAll) {
    return (CachingWebClientOptions) super.setTrustAll(trustAll);
  }

  @Override
  public CachingWebClientOptions setHttp2MultiplexingLimit(int limit) {
    return (CachingWebClientOptions) super.setHttp2MultiplexingLimit(limit);
  }

  @Override
  public CachingWebClientOptions setHttp2ConnectionWindowSize(int http2ConnectionWindowSize) {
    return (CachingWebClientOptions) super.setHttp2ConnectionWindowSize(http2ConnectionWindowSize);
  }

  @Override
  public CachingWebClientOptions setKeepAlive(boolean keepAlive) {
    return (CachingWebClientOptions) super.setKeepAlive(keepAlive);
  }

  @Override
  public CachingWebClientOptions setPipelining(boolean pipelining) {
    return (CachingWebClientOptions) super.setPipelining(pipelining);
  }

  @Override
  public CachingWebClientOptions setPipeliningLimit(int limit) {
    return (CachingWebClientOptions) super.setPipeliningLimit(limit);
  }

  @Override
  public CachingWebClientOptions setVerifyHost(boolean verifyHost) {
    return (CachingWebClientOptions) super.setVerifyHost(verifyHost);
  }

  @Override
  public CachingWebClientOptions setDecompressionSupported(boolean tryUseCompression) {
    return (CachingWebClientOptions) super.setDecompressionSupported(tryUseCompression);
  }

  @Override
  public CachingWebClientOptions setDefaultHost(String defaultHost) {
    return (CachingWebClientOptions) super.setDefaultHost(defaultHost);
  }

  @Override
  public CachingWebClientOptions setDefaultPort(int defaultPort) {
    return (CachingWebClientOptions) super.setDefaultPort(defaultPort);
  }

  @Override
  public CachingWebClientOptions setMaxChunkSize(int maxChunkSize) {
    return (CachingWebClientOptions) super.setMaxChunkSize(maxChunkSize);
  }

  @Override
  public CachingWebClientOptions setProtocolVersion(HttpVersion protocolVersion) {
    return (CachingWebClientOptions) super.setProtocolVersion(protocolVersion);
  }

  @Override
  public CachingWebClientOptions setMaxHeaderSize(int maxHeaderSize) {
    return (CachingWebClientOptions) super.setMaxHeaderSize(maxHeaderSize);
  }

  @Override
  public CachingWebClientOptions setUseAlpn(boolean useAlpn) {
    return (CachingWebClientOptions) super.setUseAlpn(useAlpn);
  }

  @Override
  public CachingWebClientOptions setSslEngineOptions(SSLEngineOptions sslEngineOptions) {
    return (CachingWebClientOptions) super.setSslEngineOptions(sslEngineOptions);
  }

  @Override
  public CachingWebClientOptions setHttp2ClearTextUpgrade(boolean value) {
    return (CachingWebClientOptions) super.setHttp2ClearTextUpgrade(value);
  }

  @Override
  public CachingWebClientOptions setAlpnVersions(List<HttpVersion> alpnVersions) {
    return (CachingWebClientOptions) super.setAlpnVersions(alpnVersions);
  }

  @Override
  public CachingWebClientOptions setMetricsName(String metricsName) {
    return (CachingWebClientOptions) super.setMetricsName(metricsName);
  }

  @Override
  public CachingWebClientOptions setProxyOptions(ProxyOptions proxyOptions) {
    return (CachingWebClientOptions) super.setProxyOptions(proxyOptions);
  }

  @Override
  public CachingWebClientOptions setLocalAddress(String localAddress) {
    return (CachingWebClientOptions) super.setLocalAddress(localAddress);
  }

  @Override
  public CachingWebClientOptions setLogActivity(boolean logEnabled) {
    return (CachingWebClientOptions) super.setLogActivity(logEnabled);
  }

  @Override
  public CachingWebClientOptions addEnabledSecureTransportProtocol(String protocol) {
    return (CachingWebClientOptions) super.addEnabledSecureTransportProtocol(protocol);
  }

  @Override
  public CachingWebClientOptions removeEnabledSecureTransportProtocol(String protocol) {
    return (CachingWebClientOptions) super.removeEnabledSecureTransportProtocol(protocol);
  }

  @Override
  public CachingWebClientOptions setEnabledSecureTransportProtocols(Set<String> enabledSecureTransportProtocols) {
    return (CachingWebClientOptions) super.setEnabledSecureTransportProtocols(enabledSecureTransportProtocols);
  }

  @Override
  public CachingWebClientOptions setReusePort(boolean reusePort) {
    return (CachingWebClientOptions) super.setReusePort(reusePort);
  }

  @Override
  public CachingWebClientOptions setTcpFastOpen(boolean tcpFastOpen) {
    return (CachingWebClientOptions) super.setTcpFastOpen(tcpFastOpen);
  }

  @Override
  public CachingWebClientOptions setTcpCork(boolean tcpCork) {
    return (CachingWebClientOptions) super.setTcpCork(tcpCork);
  }

  @Override
  public CachingWebClientOptions setTcpQuickAck(boolean tcpQuickAck) {
    return (CachingWebClientOptions) super.setTcpQuickAck(tcpQuickAck);
  }

  @Override
  public CachingWebClientOptions setHttp2KeepAliveTimeout(int keepAliveTimeout) {
    return (CachingWebClientOptions) super.setHttp2KeepAliveTimeout(keepAliveTimeout);
  }

  @Override
  public CachingWebClientOptions setForceSni(boolean forceSni) {
    return (CachingWebClientOptions) super.setForceSni(forceSni);
  }

  @Override
  public CachingWebClientOptions setDecoderInitialBufferSize(int decoderInitialBufferSize) {
    return (CachingWebClientOptions) super.setDecoderInitialBufferSize(decoderInitialBufferSize);
  }

  @Override
  public CachingWebClientOptions setKeepAliveTimeout(int keepAliveTimeout) {
    return (CachingWebClientOptions) super.setKeepAliveTimeout(keepAliveTimeout);
  }

  @Override
  public CachingWebClientOptions setMaxInitialLineLength(int maxInitialLineLength) {
    return (CachingWebClientOptions) super.setMaxInitialLineLength(maxInitialLineLength);
  }

  @Override
  public CachingWebClientOptions setInitialSettings(Http2Settings settings) {
    return (CachingWebClientOptions) super.setInitialSettings(settings);
  }

  @Override
  public CachingWebClientOptions setSslHandshakeTimeout(long sslHandshakeTimeout) {
    return (CachingWebClientOptions) super.setSslHandshakeTimeout(sslHandshakeTimeout);
  }

  @Override
  public CachingWebClientOptions setSslHandshakeTimeoutUnit(TimeUnit sslHandshakeTimeoutUnit) {
    return (CachingWebClientOptions) super.setSslHandshakeTimeoutUnit(sslHandshakeTimeoutUnit);
  }

  private static Set<Integer> buildDefaultStatusCodes() {
    Set<Integer> codes = new HashSet<>(3);
    Collections.addAll(codes, 200, 301, 404);
    return codes;
  }

  private static Set<HttpMethod> buildDefaultMethods() {
    Set<HttpMethod> methods = new HashSet<>(1);
    methods.add(HttpMethod.GET);
    return methods;
  }
}
