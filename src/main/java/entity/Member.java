package entity;


import javax.persistence.*;


@Entity
public class Member {


    @Id @GeneratedValue
    private Long id;

    @Column(name = "USERNAME", nullable = false, length = 20)
    private String name;
    private int age;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

//    @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY) // 조회해도 멤버정보만 조회 //이 방법을 추천(원하는 것을 최적화해서 가져오자) //속단해서 최적화 하지 말자
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", team=" + team +
                '}';
    }
}
