package com.example.demo.database

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

@Table
data class Person(@Id var id: Int = 0,
                  var name: String = "",
                  var age: Int = 0)

interface PersonRepo: CoroutineCrudRepository<Person, Int> {

    // SELECT * FROM person WHERE name LIKE '%tom%'
    suspend fun findByNameContains(name: String): List<Person>

    // SELECT * FROM person WHERE age >= 42
    suspend fun findByAgeGreaterThanEqual(age: Int): List<Person>
}