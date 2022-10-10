package com.pb.github.stats.lang.rest

import com.pb.github.stats.lang.common.SpringBootConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForObject

class LanguageStatsControllerTest(private val testRestTemplate: TestRestTemplate) : SpringBootConfiguration() {
    @Test
    fun `Assert response content equals expected`() {
        val response: Map<String, Double>? = testRestTemplate.getForObject<Map<String, Double>>(
            "/api/stats/lang", Map::class.java
        )
        assertThat(response).isNotEmpty
        assertThat(response).isEqualTo(mapOf("Kotlin" to 0.7, "Typescript" to 0.3))
    }
}
