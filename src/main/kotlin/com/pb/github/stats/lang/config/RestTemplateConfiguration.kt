package com.pb.github.stats.lang.config

import com.pb.github.stats.lang.handler.ClientResponseErrorHandler
import com.pb.github.stats.lang.property.GitHubApiProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration(private val gitHubApiProperties: GitHubApiProperties) {
    @Bean
    fun customRestTemplateCustomizer(): GitHubRestTemplateCustomizer = GitHubRestTemplateCustomizer(gitHubApiProperties)

    @Bean
    @DependsOn(value = ["customRestTemplateCustomizer"])
    fun restTemplate(): RestTemplate = RestTemplateBuilder(customRestTemplateCustomizer())
        .errorHandler(ClientResponseErrorHandler())
        .build()
}
