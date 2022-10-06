package com.pb.github.stats.lang

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.scheduling.annotation.EnableScheduling

@EnableHypermediaSupport(type = [EnableHypermediaSupport.HypermediaType.HAL])
@ConfigurationPropertiesScan
@SpringBootApplication
@EnableScheduling
class RepoStatsApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<RepoStatsApplication>(*args)
}
