package com.nexabank.services

import com.nexabank.models.User
import com.nexabank.repositories.UserRepository
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
}
