package com.example.demo.model

import jakarta.persistence.*

@Entity
data class Item (
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    @SequenceGenerator(name = "item_id_seq", allocationSize = 1)
    var id: Long = 0,

    @ManyToOne
    var user: User = User(),

    @Column
    var name: String = "",

    @Column
    var count: Int = 0,

    @Column
    var note: String? = null,
)
