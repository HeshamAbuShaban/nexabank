package com.nexabank.services

import com.nexabank.aop.security.ValidateAccess
import com.nexabank.models.User
import com.nexabank.models.dto.update.UpdateUserRequest
import com.nexabank.repositories.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun saveUser(user: User): User {
        return userRepository.save(user)
    }

    fun findUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun findUserByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @ValidateAccess(checkCurrentUser = true)
    fun updateUserInfo(username: String, updateRequest: UpdateUserRequest): User {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        user.email = updateRequest.email ?: user.email
        user.username = updateRequest.username ?: user.username

        return userRepository.save(user)
    }

    @PreAuthorize("hasRole('ADMIN')")
    fun deleteUserAccount(username: String) {
        val user = userRepository.findByUsername(username)
            ?: throw UsernameNotFoundException("User not found: $username")

        userRepository.delete(user)
    }

}
