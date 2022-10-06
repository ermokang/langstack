package com.pb.github.stats.lang.service

import com.pb.github.stats.lang.model.Language
import com.pb.github.stats.lang.repository.LanguageRepository
import java.lang.invoke.MethodHandles
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LanguageServiceImpl(val repository: LanguageRepository) : LanguageService {
    private val logger: Logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

    override fun mapAndPersist(langMap: MutableMap<String, Int>): MutableList<Language> =
        langMap.values.sum().let { sum ->
            langMap.map { mapToLanguageStats(it, sum) }
        }.also { repository.deleteAll() }
            .let { repository.saveAll(it) }
            .toMutableList()
            .also { logger.info("New lang stats are updated!") }

    override fun getAllRecordsAsLanguagePercentageMap(): Map<String, Double> =
        repository.findAll().filterNot { it.percentage == 0.0 }.associate { it.name to it.percentage }

    private fun mapToLanguageStats(
        it: Map.Entry<String, Int>,
        sum: Int
    ) = Language(
        it.key,
        @Suppress("ImplicitDefaultLocale")
        String.format("%.1f", it.value.toDouble().div(sum)).toDouble()
    )
}
