import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();

    public Group() {
    }
    public void addStudent (Student student) {
        student.setGroup(this);
        students.add(student);
    }
    public long getId() {
        return id;
    }

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudent() {
        return students;
    }

    public void setStudent(List<Student> student) {
        this.students = student;
    }

}
