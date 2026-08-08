package com.taskflow.websocket

data class TaskEvent(
    val type: String,
    val taskId: Long,
    val userEmail: String
)
