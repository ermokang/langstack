package com.pb.github.stats.lang.handler

import com.pb.github.stats.lang.exception.HitRateLimitException
import com.pb.github.stats.lang.exception.ResourceNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.ResponseErrorHandler
import java.lang.invoke.MethodHandles

class ClientResponseErrorHandler : ResponseErrorHandler {
    private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    override fun hasError(response: ClientHttpResponse): Boolean =
        (
            response.statusCode.series() == HttpStatus.Series.CLIENT_ERROR ||
                response.statusCode.series() == HttpStatus.Series.SERVER_ERROR
            )

    override fun handleError(response: ClientHttpResponse) {
        if (response.statusCode.series() == HttpStatus.Series.SERVER_ERROR) {
            throw HttpClientErrorException(response.statusCode)
        } else if (response.statusCode.series() == HttpStatus.Series.CLIENT_ERROR) {
            when {
                response.statusCode === HttpStatus.NOT_FOUND -> {
                    throw ResourceNotFoundException(response.statusCode.reasonPhrase)
                }
                response.statusCode === HttpStatus.FORBIDDEN -> {
                    logger.error("${HttpStatus.FORBIDDEN} rate limit exceeded")
                    throw HitRateLimitException(response.statusCode.reasonPhrase)
                }
            }
        }
    }
}
