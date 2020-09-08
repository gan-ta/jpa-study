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
            //권장되는 사항은 두 군데 다 넣어버리는 것이 권장사항
//            member.setTeam(team);

            em.flush();//쿼리 보냄
            em.clear();//캐시 비움

            Member findMember = em.find(Member.class,member.getId());

            //지연로딩시 영속성채context관리하지 않으면 프록시객체가 진짜 객체로 바꿔지지 않음
            //(LazyInitializeException)
            em.close();
            Team findTeam = findMember.getTeam();
            findTeam.getName();
            System.out.println("findTeam = "+ findTeam);

            //영속성 관리 안하겠다
//            em.detach(findMember);

            //영속성 컨텍스트를 완전히 초기화(1차 캐시를 비워줌)
            //이전 데이터가 들어가는 이유는 flush를 해서 쿼리문을 일단 db로 보내 주웠기 때문
//            em.clear();

//            findMember.setName("member2");

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }

}
