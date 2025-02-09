package com.example.conversis.model.repositories

import com.example.conversis.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

class ChatRepository {
    private val generativeModel: GenerativeModel = GenerativeModel (
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )

    suspend fun sendMessage(question: String): String {
        return generativeModel.startChat().sendMessage(question).text.toString()
    }
}