package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // Order 테이블에 있는 member 필드에의해 맵핑된거야, mappedBy 는 저것에 의해 맵핑된 거울일뿐야 라고 알려주는 것.
    private List<Order> orders = new ArrayList<>();

}