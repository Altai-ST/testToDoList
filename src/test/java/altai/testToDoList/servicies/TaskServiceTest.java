package altai.testToDoList.servicies;
import altai.testToDoList.entities.EntityTask;
import altai.testToDoList.repositories.RepositoryTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;



public class TaskServiceTest {

    @Mock
    private RepositoryTask taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnSavedTask() {
        EntityTask task = new EntityTask();
        task.setTitle("Test Task");

        when(taskRepository.save(any(EntityTask.class))).thenReturn(task);

        EntityTask createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals("Test Task", createdTask.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void getAllTasks_ShouldReturnTaskList() {
        EntityTask task1 = new EntityTask();
        task1.setTitle("Task 1");

        EntityTask task2 = new EntityTask();
        task2.setTitle("Task 2");

        List<EntityTask> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<EntityTask> allTasks = taskService.getAllTask();

        assertEquals(2, allTasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {
        EntityTask task = new EntityTask();
        task.setId(1L);
        task.setTitle("Existing Task");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<EntityTask> foundTask = taskService.getTaskById(1L);

        assertTrue(foundTask.isPresent());
        assertEquals("Existing Task", foundTask.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_ShouldReturnEmptyOptional_WhenTaskDoesNotExist() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<EntityTask> foundTask = taskService.getTaskById(1L);

        assertFalse(foundTask.isPresent());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenTaskExists() {
        EntityTask existingTask = new EntityTask();
        existingTask.setId(1L);
        existingTask.setTitle("Old Task");

        EntityTask updatedTask = new EntityTask();
        updatedTask.setTitle("Updated Task");

        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.save(any(EntityTask.class))).thenReturn(updatedTask);

        EntityTask result = taskService.updateTask(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskDoesNotExist() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        EntityTask task = new EntityTask();
        task.setTitle("Non-existing Task");

        assertThrows(RuntimeException.class, () -> taskService.updateTask(1L, task));
        verify(taskRepository, never()).save(task);
    }

    @Test
    void deleteTask_ShouldCallDeleteById_WhenTaskExists() {
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }

}
