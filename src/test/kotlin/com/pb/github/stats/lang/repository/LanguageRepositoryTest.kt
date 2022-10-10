package com.pb.github.stats.lang.repository

import com.pb.github.stats.lang.common.SpringBootConfiguration
import com.pb.github.stats.lang.model.Language
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LanguageRepositoryTest(private val repository: LanguageRepository) : SpringBootConfiguration() {
    @Test
    fun `Should return not empty list`() {
        assertThat(repository.findAll().toList()).isNotEmpty
    }

    @Test
    fun `Should save and remove new entity`() {
        val save = repository.save(Language("Scala", 0.1))
        assertThat(repository.findById(save.id)).isNotNull
        repository.delete(save)
        assertThat(repository.findById(save.id).isPresent).isFalse
    }
}
