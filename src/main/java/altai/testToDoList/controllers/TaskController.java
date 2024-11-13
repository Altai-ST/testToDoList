package altai.testToDoList.controllers;


import altai.testToDoList.entities.EntityTask;
import altai.testToDoList.servicies.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<EntityTask> createTask(@RequestBody EntityTask task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @GetMapping
    public List<EntityTask> getAllTasks() {
        return taskService.getAllTask();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityTask> getTaskById(@PathVariable Long id) {
        EntityTask entityTask = taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()).getBody();
        return ResponseEntity.ok(entityTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityTask> updateTask(@PathVariable Long id, @RequestBody EntityTask taskDetails) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
