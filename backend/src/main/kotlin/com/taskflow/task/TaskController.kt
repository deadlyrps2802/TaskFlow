package com.taskflow.task

import com.taskflow.user.UserRepository
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val repository: TaskRepository,
    private val users: UserRepository
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
        return repository.save(Task(title = request.title, description = request.description, user = user))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: TaskRequest, principal: Principal): Task {
        val task = get(id, principal)
        task.title = request.title
        task.description = request.description
        return repository.save(task)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long, principal: Principal) {
        val task = get(id, principal)
        repository.delete(task)
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
class UserNotFoundException(email: String) : RuntimeException("User $email was not found")
