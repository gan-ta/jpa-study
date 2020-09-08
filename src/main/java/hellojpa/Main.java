package hellojpa;

import entity.Member;
import entity.MemberType;
import entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();


        try {

            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //멤버 저장
            Member member = new Member();
            member.setName("member1");
            em.persist(member);
            //null이 들어가는 경우
            team.getMembers().add(member);

            //페치 조인 연습
            String jpql = "select m From Member m join fetch m.team where m.name like '%hello%'";
            List<Member> result = em.createQuery(jpql,Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
