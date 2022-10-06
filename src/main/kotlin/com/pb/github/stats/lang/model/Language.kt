package com.pb.github.stats.lang.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table
data class Language(
    @Id
    val name: String,
    val percentage: Double
) : Persistable<String> {
    @Transient
    var isUpdate: Boolean = false
    override fun getId(): String = name
    override fun isNew(): Boolean = !isUpdate
}
