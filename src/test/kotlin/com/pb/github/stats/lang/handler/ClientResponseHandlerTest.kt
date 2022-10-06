package com.pb.github.stats.lang.handler

import com.pb.github.stats.lang.exception.ResourceNotFoundException
import com.pb.github.stats.lang.service.LanguagesLoader
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate

@RestClientTest(LanguagesLoader::class)
@ExtendWith(SpringExtension::class)
@AutoConfigureWebClient(registerRestTemplate = true)
internal class ClientResponseHandlerTest(
    private var server: MockRestServiceServer
) {
    @Test
    fun `Assert error response not found`() {
        val restTemplate: RestTemplate = RestTemplateBuilder()
            .errorHandler(ClientResponseErrorHandler())
            .build()

        server.expect(ExpectedCount.once(), requestTo("https://api.github.com/orgs/find/your/way"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.NOT_FOUND))

        assertThrows<ResourceNotFoundException> {
            restTemplate.getForEntity("https://api.github.com/orgs/find/your/way", Any::class.java)
        }
    }
}
