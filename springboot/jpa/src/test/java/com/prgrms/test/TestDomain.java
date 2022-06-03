package com.prgrms.test;

import com.prgrms.test.domain.Member;
import com.prgrms.test.domain.Order;
import com.prgrms.test.domain.Orders;
import com.prgrms.test.domain.repository.MemberRepository;
import com.prgrms.test.domain.repository.OrderRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TestDomain {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager em;

    @Transactional
    @Test
    @DisplayName("단방향 테스트")
    void testLazyLoading(){

        Member member = new Member("jung");

        Order order1 = new Order(10000L);
        Order order2 = new Order(11000L);

        member.addOrder(order1);
        member.addOrder(order2);

        memberRepository.save(member);

        orderRepository.save(order1);
        orderRepository.save(order2);

        em.flush();

        Member savedMember = memberRepository.findById(1L).get();
        Orders orders = savedMember.getOrders();
        log.info(""+orders.size());

    }

}
