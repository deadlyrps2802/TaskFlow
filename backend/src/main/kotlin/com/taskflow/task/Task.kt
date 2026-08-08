package com.taskflow.task

import com.taskflow.user.User
import jakarta.persistence.*

@Entity
@Table(name = "tasks")
class Task(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 200)
    var title: String,

    @Column(length = 2000)
    var description: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TaskStatus = TaskStatus.TODO,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User
)

enum class TaskStatus {
    TODO, IN_PROGRESS, DONE
}
