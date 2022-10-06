package com.pb.github.stats.lang.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.io.File
import java.nio.file.Files

@RestClientTest(LanguagesLoader::class)
@ExtendWith(SpringExtension::class)
@AutoConfigureWebClient(registerRestTemplate = true)
internal class LanguagesLoaderImplTest(
    private val languagesLoader: LanguagesLoader,
    private var server: MockRestServiceServer
) {
    @BeforeEach
    internal fun setUp() {
        mockResponse()
    }

    @Test
    fun `Assert response content equals expected`() {
        val map = languagesLoader.downloadAllLanguagesPerOrgRepo(
            "productboard",
            "https://api.github.com/orgs/{org}/repos"
        )
        val testMap = mapOf<String, Int>(
            "JavaScript" to 3903390,
            "C++" to 89902,
            "TypeScript" to 31448,
            "CoffeeScript" to 24088,
            "Python" to 16578,
            "C" to 10676,
            "Shell" to 1050,
            "Makefile" to 378
        )
        assertThat(map).isEqualTo(testMap)
    }

    private fun mockResponse() {
        val repos: File = ClassPathResource(
            "responses/repositoriesList.json"
        ).file
        val reposResponse = String(
            Files.readAllBytes(repos.toPath())
        )
        val lang: File = ClassPathResource(
            "responses/repoLangResponse.json"
        ).file
        val langResponse = String(
            Files.readAllBytes(lang.toPath())
        )
        server.expect(ExpectedCount.once(), requestTo("https://api.github.com/orgs/productboard/repos"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(reposResponse, MediaType.APPLICATION_JSON))
        server.expect(ExpectedCount.once(), requestTo("https://api.github.com/repos/productboard/react/languages"))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(langResponse, MediaType.APPLICATION_JSON))
        server.expect(
            ExpectedCount.once(),
            requestTo("https://api.github.com/repos/productboard/webpack-deploy/languages")
        )
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess(langResponse, MediaType.APPLICATION_JSON))
    }
}
