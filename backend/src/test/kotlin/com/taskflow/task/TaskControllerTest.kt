package com.taskflow.task

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class TaskControllerTest {
    @Test
    fun `new task defaults to todo`() {
        val task = Task(title = "Prepare interview")
        assertEquals(TaskStatus.TODO, task.status)
    }
}
