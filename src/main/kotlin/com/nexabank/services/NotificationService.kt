package com.nexabank.services

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class NotificationService(private val mailSender: JavaMailSender) {

    fun sendTransactionNotification(email: String, subject: String, message: String) {
        val mail = SimpleMailMessage()
        mail.setTo(email)
        mail.subject = subject
        mail.text = message
        mailSender.send(mail)
    }
}
