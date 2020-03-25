
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Random;

public class App {
    public static EntityManagerFactory emf;
    public static EntityManager em;

    public static void main (String[] args) {
        emf = Persistence.createEntityManagerFactory("Students");
        em = emf.createEntityManager();
        Group group = new Group("alpha");
        flushGroup(group);
        try {
            em.getTransaction().begin();
            try {
                em.persist(group);
                em.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
                em.getTransaction().rollback();
            }
            System.out.println(countStudents("alpha"));
        } finally {
            em.close();
            emf.close();
        }
    }
    private static int countStudents(String group) {
        long id = (long) em.createQuery("SELECT g.id FROM Group g WHERE g.name = ?1").setParameter(1,group).getSingleResult();
        long count = (long) em.createQuery("SELECT COUNT (s.id) FROM Student s WHERE s.group.id=?1").setParameter(1,id).getSingleResult();
        return (int) count;
    }
    private static void flushGroup (Group group) {
        String[] namesMale = {"Andrey","Pavel","Anton","Alexander","Kirill","Ivan"};
        String[] namesFemale = {"Svetlana","Milana","Marina","Inessa","Natasha"};
        String[] sureNamesFemale = {"Dulina","Ivanova","Pavlova","Kulikova","Anchishina","Svataya"};
        String[] sureNamesMale = {"Pavlov","Abdulov","Ivanov","Krabov","Zagogulin","Kitaev","Maslaev"};
        String[][] names = {namesMale,namesFemale};
        String[][] sureNames = {sureNamesMale,sureNamesFemale};
        int[] ages = {23,42,32,25,26,43,36,28};

        for (int i = 0; i < 10;i++) {
               int number = new Random().nextInt(names.length);
               String[] name = names[number];
               String[] sureName = sureNames[number];
               Random n = new Random();
               group.addStudent(new Student(name[n.nextInt(name.length)],sureName[n.nextInt(sureName.length)],ages[n.nextInt(ages.length)]));
        }
    }
}
