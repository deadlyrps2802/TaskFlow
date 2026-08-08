package com.taskflow.task

import com.taskflow.user.UserNotFoundException
import com.taskflow.user.UserRepository
import com.taskflow.websocket.TaskEvent
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val repository: TaskRepository,
    private val users: UserRepository,
    private val messaging: SimpMessagingTemplate
) {
    @GetMapping
    fun list(principal: Principal): List<Task> = repository.findAllByUserEmail(principal.name)

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long, principal: Principal): Task =
        repository.findByIdAndUserEmail(id, principal.name) ?: throw TaskNotFoundException(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: TaskRequest, principal: Principal): Task {
        val user = users.findByEmail(principal.name).orElseThrow { UserNotFoundException(principal.name) }
        val task = repository.save(Task(title = request.title, description = request.description, user = user))
        publish("CREATED", task, principal.name)
        return task
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: TaskRequest, principal: Principal): Task {
        val task = get(id, principal)
        task.title = request.title
        task.description = request.description
        val saved = repository.save(task)
        publish("UPDATED", saved, principal.name)
        return saved
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long, principal: Principal) {
        val task = get(id, principal)
        repository.delete(task)
        messaging.convertAndSend("/topic/tasks/${principal.name}", TaskEvent("DELETED", id, principal.name))
    }

    private fun publish(type: String, task: Task, email: String) {
        messaging.convertAndSend("/topic/tasks/$email", TaskEvent(type, task.id!!, email))
    }
}

data class TaskRequest(
    @field:NotBlank(message = "Title is required")
    @field:Size(max = 200, message = "Title must be at most 200 characters")
    val title: String,
    @field:Size(max = 2000, message = "Description must be at most 2000 characters")
    val description: String? = null
)

class TaskNotFoundException(id: Long) : RuntimeException("Task $id was not found")
