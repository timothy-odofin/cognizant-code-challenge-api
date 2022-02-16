package cognizant.com.codechallenge.repo;

import cognizant.com.task.model.Languages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LanguagesRepo extends CrudRepository<Languages, Long> {
    @Query("select st from Languages st")
    List<Languages> list();
    Optional<Languages> findByName(String name);
}
