package com.pb.github.stats.lang.config

import com.pb.github.stats.lang.property.GitHubApiProperties
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.web.client.RestTemplate

class GitHubRestTemplateCustomizer(private val gitHubApiProperties: GitHubApiProperties) : RestTemplateCustomizer {
    override fun customize(restTemplate: RestTemplate) {
        restTemplate.interceptors.add(GitHubClientHttpRequestInterceptor(gitHubApiProperties))
    }
}
