package cognizant.com.codechallenge.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
@Data
public class Languages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name="language_name")
    private String name;
    @Column(name="passed_name")
    private String passedName;
    @Column(name="version_index")
    private int versionIndex;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
}
