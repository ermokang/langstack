package com.pb.github.stats.lang.service

interface LanguagesLoader {
    /**
     * Retrieve list of languages for each organization repo from GitHub REST API client
     */
    fun downloadAllLanguagesPerOrgRepo(org: String, sourceUrl: String): MutableMap<String, Int>
}
