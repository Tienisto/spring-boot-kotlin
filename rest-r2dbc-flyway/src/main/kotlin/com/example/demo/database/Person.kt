package com.example.demo.database

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Table
data class Person(@Id var id: Int = 0,
                  var name: String = "",
                  var age: Int = 0)

interface PersonRepo: CoroutineCrudRepository<Person, Int> {

    suspend fun findByNameContains(name: String): List<Person>
    suspend fun findByAgeGreaterThanEqual(age: Int): List<Person>
}