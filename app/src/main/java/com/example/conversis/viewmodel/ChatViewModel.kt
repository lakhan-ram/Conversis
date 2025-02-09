package com.example.conversis.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.conversis.model.entities.Message
import com.example.conversis.model.repositories.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel: ViewModel() {
    private val chatRepository = ChatRepository()
    val messageList by lazy { mutableStateListOf<Message>() }


    fun sendMessage(question: String) {
        messageList.add(Message(question, "user"))
        messageList.add(Message("Typing...", "bot"))
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val answer = chatRepository.sendMessage(question)
                messageList.removeAt(messageList.lastIndex)
                messageList.add(Message(answer, "bot"))
            } catch (e: Exception) {
                messageList.removeAt(messageList.lastIndex)
                messageList.add(Message("Error: ${e.localizedMessage}", "bot"))
            }

        }
    }

}