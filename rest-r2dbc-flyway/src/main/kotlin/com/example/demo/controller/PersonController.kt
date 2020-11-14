package com.example.demo.controller

import com.example.demo.Request
import com.example.demo.database.Person
import com.example.demo.database.PersonRepo
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/people")
class PersonController (
        private val personRepo: PersonRepo
) {

    // GET /people
    @GetMapping
    suspend fun getPeople(): List<Person> {
        return personRepo.findAll().toList()
    }

    // GET /people/search?name=tom
    @GetMapping("/search")
    suspend fun getPeopleByName(@RequestParam name: String): List<Person> {
        return personRepo.findByNameContains(name)
    }

    // GET /people/adults
    @GetMapping("/adults")
    suspend fun getAdults(): List<Person> {
        return personRepo.findByAgeGreaterThanEqual(18)
    }

    // POST /people <data>
    @PostMapping
    suspend fun createPerson(@RequestBody request: Request.CreatePerson) {
        val person = Person(0, request.name, request.age)
        personRepo.save(person)
    }

    // PUT /people <data>
    @PutMapping
    suspend fun updatePerson(@RequestBody request: Request.UpdatePerson): ResponseEntity<Void> {
        val person = personRepo.findById(request.id)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        person.name = request.name
        person.age = request.age
        personRepo.save(person)
        return ResponseEntity(HttpStatus.OK)
    }

    // DELETE /people/352
    @DeleteMapping("/{id}")
    suspend fun deletePerson(@PathVariable id: Int): ResponseEntity<Void> {
        val person = personRepo.findById(id)
                ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        personRepo.delete(person)
        return ResponseEntity(HttpStatus.OK)
    }
}