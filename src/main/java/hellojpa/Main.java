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
            member.setTeam(team);
            em.persist(member);

            em.flush();//쿼리 보냄
            em.clear();//캐시 비움

            tx.commit();

            Member findMember = em.find(Member.class,member.getId());
            Team findTeam = findMember.getTeam();

//            findTeam.getName();

            List<Member> members = findTeam.getMembers();
            for(Member member1: members){
                System.out.println("member1 = " + member1);
            }

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
