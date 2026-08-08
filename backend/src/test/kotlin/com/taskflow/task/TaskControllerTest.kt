package com.taskflow.task

import com.taskflow.user.User
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class TaskControllerTest {
    @Test
    fun `new task defaults to todo`() {
        val user = User(email = "test@example.com", passwordHash = "test-hash")
        val task = Task(title = "Prepare interview", user = user)
        assertEquals(TaskStatus.TODO, task.status)
    }
}
