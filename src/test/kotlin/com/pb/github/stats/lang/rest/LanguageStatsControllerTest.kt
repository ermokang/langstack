package com.pb.github.stats.lang.rest

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LanguageStatsControllerTest(private val testRestTemplate: TestRestTemplate) {
    @Test
    fun `Assert response content equals expected`() {
        val response: Map<String, Double>? = testRestTemplate.getForObject<Map<String, Double>>(
            "/api/stats/lang", Map::class.java
        )
        assertThat(response).isNotEmpty
        assertThat(response).isEqualTo(mapOf("Kotlin" to 0.7, "Typescript" to 0.3))
    }
}
