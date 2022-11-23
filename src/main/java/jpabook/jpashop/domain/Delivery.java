package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP, ENUM 타입에는 @Enumerate 어노테이션을 넣어야한다. , EnumType 은 꼭 STRING 으로 해줘야한다. (ORDINAL 로 하면 안된다.)
}
