package com.example;

import capstone_demo.Main;
import capstone_demo.domain.Id.ResidentId;
import capstone_demo.domain.Resident;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
public class DomainTest {

    @Autowired
    public EntityManagerFactory emf;
    @Autowired
    public EntityManager em;

    public DomainTest(){
        emf = Persistence.createEntityManagerFactory("test");
        em = emf.createEntityManager();
    }
    @Test
    @Transactional
    public void ResidentTest(){
        Resident resident = new Resident();
        resident.setName("user1");
        resident.setBirth("001231");
        resident.setAddress("101동101호");
        em.persist(resident);

        ResidentId findId = new ResidentId(resident.getName(), resident.getAddress());
        Resident findResident = em.find(Resident.class, findId);
        Assertions.assertThat(findResident).isEqualTo(resident);
    }

}
