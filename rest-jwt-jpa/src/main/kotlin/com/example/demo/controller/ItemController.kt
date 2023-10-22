package com.example.demo.controller

import com.example.demo.config.toUser
import com.example.demo.dto.*
import com.example.demo.model.Item
import com.example.demo.model.Role
import com.example.demo.service.ItemService
import jakarta.annotation.security.RolesAllowed
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

/**
 * This controller handles requests for managing items of the authenticated user.
 */
@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemService: ItemService,
) {

    @GetMapping("/v1/getAll")
    fun getItemsV1(): List<ItemDto> {
        return itemService.findAll().map { item -> item.toDto() }
    }

    @GetMapping("/v1/getAllByUser")
    fun getItemsByUserV1(authentication: Authentication): List<ItemDto> {
        val authUser = authentication.toUser()

        return itemService.findByUser(authUser).map { item -> item.toDto() }
    }

    @PostMapping("/v1/add")
    fun createItemV1(authentication: Authentication, @RequestBody payload: CreateItemDto) {
        val authUser = authentication.toUser()

        if (itemService.existsByNameAndUser(payload.name, authUser)) {
            throw ApiException(409, "Item name already exists")
        }

        val item = Item(
            user = authUser,
            name = payload.name,
            count = payload.count,
            note = payload.note,
        )

        itemService.save(item)
    }

    @PutMapping("/v1/update")
    fun updateItemV1(authentication: Authentication, @RequestBody payload: UpdateItemDto) {
        val authUser = authentication.toUser()

        val item = itemService.findById(payload.id) ?: throw ApiException(404, "Item not found")
        if (item.user.id != authUser.id) {
            throw ApiException(403, "Not your item")
        }

        val existingItem = itemService.findByNameAndUser(payload.name, authUser)
        if (existingItem != null && existingItem.id != payload.id) {
            throw ApiException(409, "Item name already exists")
        }

        item.name = payload.name
        item.count = payload.count
        item.note = payload.note

        itemService.save(item)
    }

    @DeleteMapping("/v1/deleteById")
    fun deleteItemV1(authentication: Authentication, @RequestParam itemId: Long) {
        val authUser = authentication.toUser()
        val item = itemService.findById(itemId) ?: throw ApiException(404, "Item not found")
        if (item.user.id != authUser.id) {
            throw ApiException(403, "Not your item")
        }

        itemService.delete(item)
    }
}
