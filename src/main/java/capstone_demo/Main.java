package capstone_demo;


import capstone_demo.domain.*;
import capstone_demo.repository.ParcelHistoryRepository;
import capstone_demo.repository.ResidentRepository;
import capstone_demo.service.ResidentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {

//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
//        EntityManager em = emf.createEntityManager();
//        EntityTransaction tx = em.getTransaction();

        SpringApplication.run(Main.class, args);
//
//        try{
//
//            tx.begin();
//
//
//
//            tx.commit();
//        }catch (Exception e){
//            e.printStackTrace();
//            tx.rollback();
//        }
//        finally {
//            em.close();
//        }
//        emf.close();
   }




}