package altai.testToDoList.repositories;

import altai.testToDoList.entities.EntityTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryTask extends JpaRepository<EntityTask, Long> {
}
