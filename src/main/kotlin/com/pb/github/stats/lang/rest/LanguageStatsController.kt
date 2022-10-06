package com.pb.github.stats.lang.rest

import com.pb.github.stats.lang.service.LanguageService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/stats")
class LanguageStatsController(val languageService: LanguageService) {

    @GetMapping("/lang")
    fun getLanguagePercentage(): Map<String, Double> = languageService.getAllRecordsAsLanguagePercentageMap()
}
