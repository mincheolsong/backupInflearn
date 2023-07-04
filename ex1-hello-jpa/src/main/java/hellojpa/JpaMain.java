package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);
            em.persist(member);

//          team.getMembers().add(member); // 양방향 연관관계의 경우 team member 양쪽 다 셋팅 해주는 것이 객체지향적으로 옳은 방법이다. 안 하면 flush , clear 해야 함

//          em.flush();
//          em.clear();

            Team findTeam = em.find(Team.class,team.getId());
            List<Member> members = findTeam.getMembers();

            System.out.println("=========");
            for(Member m: members){
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("=========");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();

    }
}
