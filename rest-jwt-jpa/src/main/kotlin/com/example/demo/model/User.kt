package com.example.demo.model

import jakarta.persistence.*

@Entity
@Table(name = "api_user")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "api_user_id_seq")
    @SequenceGenerator(name = "api_user_id_seq", allocationSize = 1)
    var id: Long = 0,

    @Column
    var name: String = "",

    @Column
    var password: String = "",
)
