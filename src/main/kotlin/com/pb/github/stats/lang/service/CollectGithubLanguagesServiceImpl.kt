package com.pb.github.stats.lang.service

import com.pb.github.stats.lang.property.GitHubApiProperties
import java.lang.invoke.MethodHandles
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class CollectGithubLanguagesServiceImpl(
    private val languagesLoader: LanguagesLoader,
    private val languageService: LanguageService,
    private val gitHubApiProperties: GitHubApiProperties
) : CollectGithubLanguagesService {
    private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    @Scheduled(fixedDelayString = "\${schedule.fixedDelay}")
    override fun collectLanguagesAndPersistPercentage() {
        logger.info("Starting scheduled job to download organization repositories and used languages")
        languagesLoader.downloadAllLanguagesPerOrgRepo(
            gitHubApiProperties.organization, gitHubApiProperties.initialUrl
        ).let { languageService.mapAndPersist(it) }
    }
}
