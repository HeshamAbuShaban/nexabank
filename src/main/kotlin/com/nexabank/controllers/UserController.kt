package com.nexabank.controllers

import com.nexabank.models.User
import com.nexabank.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

// TODO : This Controller Isn't really useful unless an admin role was founded
@Tag(name = "Users", description = "APIs for handling users")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @Operation(summary = "Create a User", description = "Create a user using the user model.")
    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userService.saveUser(user)
        return ResponseEntity.ok(savedUser)
    }

    @Operation(summary = "Get a User by it's Id", description = "Fetch a user using the user id.")
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.findUserById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @Operation(summary = "Get all Users", description = "Fetch all users.")
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }
}
