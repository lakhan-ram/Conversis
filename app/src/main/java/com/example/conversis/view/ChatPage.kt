package com.example.conversis.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.conversis.R
import com.example.conversis.model.entities.Message
import com.example.conversis.ui.theme.botChatColor
import com.example.conversis.ui.theme.userChatColor
import com.example.conversis.viewmodel.ChatViewModel

@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    Column(
        modifier = modifier
    ) {
        AppHeader()
        ChatMessageList(modifier = Modifier.weight(1f), messageList = viewModel.messageList)
        MessageInput(onMessageSent = {
            viewModel.sendMessage(it)
        })
    }
}


@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "Conversis",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

@Composable
fun ChatMessageList(modifier: Modifier, messageList: List<Message>) {
    if (messageList.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = "icon",
                tint = Gray
            )
            Text(
                text = "Ask me anything",
                fontSize = 16.sp,
            )
        }
    } else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                ChatMessage(message = it)
            }
        }
    }
}

@Composable
fun ChatMessage(message: Message) {
    val isBot = message.role == "bot"
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.align(if(isBot) Alignment.BottomStart else Alignment.BottomEnd)
                    .padding(
                        start = if (isBot) 10.dp else 60.dp,
                        end = if (isBot) 60.dp else 10.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                    .clip(if(isBot) RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp) else RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp))
                    .background(if (isBot) botChatColor else userChatColor)
                    .padding(16.dp)

            ) {
                SelectionContainer {
                    Text(
                        text = message.message,
                        fontWeight = FontWeight.W300,
                        color = if (isBot) Black else White
                    )
                }
            }
        }
    }
}

@Composable
fun MessageInput(onMessageSent: (String) -> Unit) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message,
            label = { Text("Type a message") },
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            onValueChange = {
                message = it
            }
        )

        IconButton(
            onClick = {
                if (message.isNotEmpty()) {
                    onMessageSent(message)
                    message = ""
                }
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
        }
    }
}