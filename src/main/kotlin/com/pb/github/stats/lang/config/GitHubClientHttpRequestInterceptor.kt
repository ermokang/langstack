package com.pb.github.stats.lang.config

import com.pb.github.stats.lang.property.GitHubApiProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import java.lang.invoke.MethodHandles

/**
 * Request interceptor to authorize and log requests
 */
class GitHubClientHttpRequestInterceptor(private val gitHubApiProperties: GitHubApiProperties) :
    ClientHttpRequestInterceptor {
    private val logger: Logger = LoggerFactory
        .getLogger(MethodHandles.lookup().lookupClass())

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse = let {
        gitHubApiProperties.pat?.let { prop -> request.headers.set(HttpHeaders.AUTHORIZATION, "token $prop") }
        val clientHttpResponse = execution.execute(request, body)
        clientHttpResponse
    }.also {
        logger.info("Request Method: {}", request.method)
        logger.info("Request URI: {}", request.uri)
    }
}
