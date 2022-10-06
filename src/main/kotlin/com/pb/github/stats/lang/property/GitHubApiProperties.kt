package com.pb.github.stats.lang.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("api.github")
@ConstructorBinding
data class GitHubApiProperties(val initialUrl: String, val organization: String, val pat: String?) {
    init {
        require(initialUrl.isNotBlank()) { "GitHub list repositories url should be provided" }
        require(organization.isNotBlank()) { "GitHub organization name should be provided" }
        require(!pat.isNullOrBlank()) { "GitHub personal access token should be provided" }
    }
}
