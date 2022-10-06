package com.pb.github.stats.lang.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Repository(
    val id: Int,
    val name: String,
    @JsonProperty("languages_url")
    val languagesUrl: String
)
