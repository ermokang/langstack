package com.pb.github.stats.lang.service

import com.pb.github.stats.lang.model.Language

interface LanguageService {
    fun mapAndPersist(langMap: MutableMap<String, Int>): MutableList<Language>
    fun getAllRecordsAsLanguagePercentageMap(): Map<String, Double>
}
