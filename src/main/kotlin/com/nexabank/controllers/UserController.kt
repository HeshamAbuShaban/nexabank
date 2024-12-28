package com.nexabank.controllers

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.models.User
import com.nexabank.models.dto.update.UpdateUserRequest
import com.nexabank.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@Tag(name = "Users", description = "APIs for handling users")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {


    @Operation(summary = "Get a User by it's Id", description = "Fetch a user using the user id.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.findUserById(id)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @Operation(summary = "Get Active User Details", description = "Fetch user's data (must be actual user that access).")
    @GetMapping("/username")
    @ValidateAccess(checkCurrentUser = true)
    fun getUserByUsername(@RequestParam username: String): ResponseEntity<User> {
        val user = userService.findUserByUsername(username)
        return if (user != null) ResponseEntity.ok(user) else ResponseEntity.notFound().build()
    }

    @Operation(summary = "Get all Users", description = "Fetch all users.")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @Operation(summary = "Update Active User Details", description = "update user's data (must be actual user that access).")
    @PutMapping("/{username}")
    fun updateUserInfo(
        @PathVariable username: String,
        @RequestBody updateRequest: UpdateUserRequest
    ): ResponseEntity<User> {
        return ResponseEntity.ok(userService.updateUserInfo(username, updateRequest))
    }

    @Operation(summary = "Delete Active User", description = "wipe user's data from bank!.")
    @DeleteMapping("/{username}")
    fun deleteUserAccount(@PathVariable username: String): ResponseEntity<String> {
        userService.deleteUserAccount(username)
        return ResponseEntity.ok("User $username deleted successfully.")
    }

}
