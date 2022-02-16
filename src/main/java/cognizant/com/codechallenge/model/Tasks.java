package cognizant.com.codechallenge.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Data
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name="task_name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="input_param")
    private String input_param;
    @Column(name="output_param")
    private String output_param;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
}
