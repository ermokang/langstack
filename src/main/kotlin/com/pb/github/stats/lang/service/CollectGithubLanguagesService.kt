package com.pb.github.stats.lang.service

/**
 * Service collecting list of used languages and persisting their percentage for specified organization using GitHub API
 */
interface CollectGithubLanguagesService {

    fun collectLanguagesAndPersistPercentage()
}
