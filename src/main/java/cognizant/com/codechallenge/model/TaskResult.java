package cognizant.com.codechallenge.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name="task_result")
@Entity
@Data
public class TaskResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name="username")
    private String username;
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @ManyToOne
    Tasks tasks;
    @Column(name="cpu_time")
    private float cpuTime;
    @Column(name="memory_used")
    private float memoryUsed;
    @Column(name="status_code")
    private Integer statusCode;
    @Column(name="submitted_payload")
    private String submittedPayload;
    @Column(name="remark")
    private String remark;
    @Column(name="output")
    private String output;
    @Column(name="expected_output")
    private String expectedOutput;
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
}
