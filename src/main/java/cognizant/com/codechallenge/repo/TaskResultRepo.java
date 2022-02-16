package cognizant.com.codechallenge.repo;

import cognizant.com.task.model.TaskResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskResultRepo extends PagingAndSortingRepository<TaskResult,Long> {

    @Query("select r from TaskResult r where r.tasks.id=:taskId and r.username=:username")
    Optional<TaskResult> findByUsernameAndTaskId(@Param("username")String username,
                                                 @Param("taskId")Long taskId);
    @Query(value="select st.tasks.name from TaskResult st where st.username=:username and st.remark=:remark")
    List<String> listTaskNameByUsername(@Param("username")String username, @Param("remark")String remark);
}
