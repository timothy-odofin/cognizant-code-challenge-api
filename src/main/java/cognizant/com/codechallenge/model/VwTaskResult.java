package cognizant.com.codechallenge.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="vw_task_result")
@Entity
@Data
public class VwTaskResult {
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name="username")
    private String username;
    @Column(name="success_count")
    private Long successCount;
}
