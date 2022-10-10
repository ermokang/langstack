package com.pb.github.stats.lang.scheduler

import com.ninjasquad.springmockk.SpykBean
import com.pb.github.stats.lang.common.SpringBootConfiguration
import com.pb.github.stats.lang.service.CollectGithubLanguagesServiceImpl
import io.mockk.verify
import org.awaitility.Awaitility.await
import org.awaitility.Duration
import org.junit.jupiter.api.Test

class ScheduledIntegrationTest : SpringBootConfiguration() {

    @SpykBean
    lateinit var collectGithubLanguagesService: CollectGithubLanguagesServiceImpl

    @Test
    fun `Assert one invocation happened`() {
        await().atMost(Duration.ONE_SECOND)
            .untilAsserted {
                verify(exactly = 1) { collectGithubLanguagesService.collectLanguagesAndPersistPercentage() }
            }
    }
}
