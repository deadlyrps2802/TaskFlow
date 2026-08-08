package com.taskflow.task

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val repository: TaskRepository) {

    @GetMapping
    fun list(): List<Task> = repository.findAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Task = repository.findById(id)
        .orElseThrow { TaskNotFoundException(id) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@Valid @RequestBody request: TaskRequest): Task =
        repository.save(Task(title = request.title, description = request.description))

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody request: TaskRequest): Task {
        val task = get(id)
        task.title = request.title
        task.description = request.description
        return repository.save(task)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        if (!repository.existsById(id)) throw TaskNotFoundException(id)
        repository.deleteById(id)
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
