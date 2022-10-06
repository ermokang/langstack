package com.pb.github.stats.lang.service

import com.pb.github.stats.lang.dto.Repository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.hateoas.Link
import org.springframework.hateoas.Links
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.lang.invoke.MethodHandles
import java.util.*

@Component
class LanguagesLoaderImpl(
    val restTemplate: RestTemplate
) : LanguagesLoader {
    private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    override fun downloadAllLanguagesPerOrgRepo(org: String, sourceUrl: String): MutableMap<String, Int> {
        val repositoryList = mutableListOf<Repository>()
        val languagesMap = mutableMapOf<String, Int>()

        logger.info("Start loading repositories pages")
        collectAllRepositoryPages(restTemplate, repositoryList, sourceUrl, org)
        logger.info("Loaded ${repositoryList.size} $org repositories. Start loading each repo language...")
        collectAndMergeAllOrgLanguages(repositoryList, restTemplate, languagesMap)

        return languagesMap
    }

    /**
     * Access repo language resource using hypermedia
     */
    private fun collectAndMergeAllOrgLanguages(
        repositoryList: MutableList<Repository>,
        restTemplate: RestTemplate,
        languagesMap: MutableMap<String, Int>
    ) = repositoryList.map { repo ->
        restTemplate.getForObject<Map<String, Int>>(repo.languagesUrl, Map::class.java)
            .let { responseMap -> responseMap.forEach { languagesMap.merge(it.key, it.value, Int::plus) } }
    }

    /**
     * Paginated requests, follow next link until exists
     */
    private fun collectAllRepositoryPages(
        restTemplate: RestTemplate,
        repositoryList: MutableList<Repository>,
        initialUrl: String,
        organization: String
    ) {
        var repoUrl = initialUrl.replace("{org}", organization)

        do {
            val nextLink = getRepoWithNextLink(restTemplate, repositoryList, repoUrl)
            nextLink?.href?.also { repoUrl = it }
        } while (nextLink != null)
    }

    private fun getRepoWithNextLink(
        restTemplate: RestTemplate,
        mutableRepositoryList: MutableList<Repository>,
        url: String
    ): Link? =
        restTemplate.getForEntity(
            url,
            Array<Repository>::class.java,
            mapOf("type" to "public")
        )
            .also { mutableRepositoryList.addAll(it.body) }
            .let { getNextLinkFromHeader(it) }

    private fun getNextLinkFromHeader(response: ResponseEntity<Array<Repository>>): Link? =
        response.headers.getFirst(HttpHeaders.LINK)
            .let { Links.parse(it) }
            .getLink("next").orElse(null)
}

private fun <E> MutableList<E>.addAll(optionalArray: Array<E>?) {
    if (optionalArray != null) {
        addAll(optionalArray.asList())
    }
}
