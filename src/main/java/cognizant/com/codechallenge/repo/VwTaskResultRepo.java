package cognizant.com.codechallenge.repo;

import cognizant.com.codechallenge.model.VwTaskResult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VwTaskResultRepo extends PagingAndSortingRepository<VwTaskResult,Long> {
    @Query(value="select * from vw_task_result order by success_count desc limit 3", nativeQuery = true)
    List<VwTaskResult> listTopThree();
}
