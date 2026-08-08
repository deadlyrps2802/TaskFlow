package com.taskflow.task

import org.springframework.data.jpa.repository.JpaRepository

interface TaskRepository : JpaRepository<Task, Long> {
    fun findAllByUserEmail(email: String): List<Task>
    fun findByIdAndUserEmail(id: Long, email: String): Task?
    fun existsByIdAndUserEmail(id: Long, email: String): Boolean
}
