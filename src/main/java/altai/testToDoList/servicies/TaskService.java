package altai.testToDoList.servicies;


import altai.testToDoList.entities.EntityTask;
import altai.testToDoList.repositories.RepositoryTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private RepositoryTask repositoryTask;

//    @Autowired
//    private EmailService emailService;

    public EntityTask createTask(EntityTask task){

        EntityTask createdTask = repositoryTask.save(task);

        String toEmail = "recipient@example.com";  // Укажите email получателя
        String subject = "Новая задача создана";
        String message = "Задача '" + task.getTitle() + "' успешно создана с описанием: " + task.getDescription();

//        emailService.sendTaskCreationEmail(toEmail, subject, message);

        return createdTask;
    }

    public List<EntityTask> getAllTask(){
        return repositoryTask.findAll();
    }

    public Optional<EntityTask> getTaskById (Long id){
        return repositoryTask.findById(id);
    }

        public EntityTask updateTask(Long id, EntityTask taskDetails) {
        EntityTask task = repositoryTask.findById(id).orElseThrow();
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        return repositoryTask.save(task);
    }

    public void deleteTask(Long id) {
        repositoryTask.deleteById(id);
    }
}
