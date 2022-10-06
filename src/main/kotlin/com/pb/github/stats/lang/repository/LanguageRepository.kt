package com.pb.github.stats.lang.repository

import com.pb.github.stats.lang.model.Language
import org.springframework.data.repository.CrudRepository

interface LanguageRepository : CrudRepository<Language, String>
