package cognizant.com.codechallenge.repo;

import cognizant.com.codechallenge.model.Tasks;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TasksRepo extends PagingAndSortingRepository<Tasks,Long> {
}
