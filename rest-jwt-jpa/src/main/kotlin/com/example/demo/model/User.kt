package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(
    name = "api_user",
    indexes = [
        Index(name = "username_index", columnList = "username", unique = true),
    ],
)
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_user_id_seq")
    @SequenceGenerator(name = "api_user_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column(unique = true)
    var username: String,

    var firstName: String?,

    var lastName: String?,

    var hashedPassword: String,

    @Enumerated(EnumType.STRING)
    var role: Role = Role.ADMIN,
)
