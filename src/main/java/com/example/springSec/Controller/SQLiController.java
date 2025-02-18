package com.example.springSec.Controller;


import com.example.springSec.Entity.User;
import com.example.springSec.Repository.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("/sqli")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SQLiController {
    UserRepo userRepository;

    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

//    @Value("${spring.datasource.url}")
    static String url = "jdbc:sqlserver://localhost:1433;databaseName=CtyA;encrypt=true;trustServerCertificate=true";
//    jdbc:sqlserver://localhost:1433;databaseName=SinhVien;encrypt=true;trustServerCertificate=true

//    @Value("${spring.datasource.username}")
    static String user = "sa";

//    @Value("${spring.datasource.password}")
    static String password = "Vietanh204@";
    private final HttpSession httpSession;
    private final LocalContainerEntityManagerFactoryBean entityManagerFactory;
    //Vuln code
    @GetMapping("/vuln")
        public String vuln(@RequestParam String username) {
        StringBuilder result = new StringBuilder();
        try{
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);

            if(!con.isClosed()){
                System.out.println("Connected");
            }
            String sql = "select * from users where username='" + username + "'";
            log.info(sql);
            ResultSet rs = con.createStatement().executeQuery(sql);

            while(rs.next()){
                String res_name = rs.getString("username");
                String res_pwd = rs.getString("password");
                String infomation = String.format("%s: %s\n", res_name, res_pwd);
                result.append(infomation);
                log.info(infomation);
            }

            rs.close();
            con.close();

        } catch (ClassNotFoundException e) {
            log.error("Can not connect");
        } catch (SQLException e) {
            log.error(e.toString());
        }

        return result.toString();
    }

    //safe code: truyền tham số với preparestatement
//    @GetMapping("/vuln")
//    public String vuln(@RequestParam String username) {
//        StringBuilder result = new StringBuilder();
//        try{
//            Class.forName(driver);
//            Connection con = DriverManager.getConnection(url, user, password);
//
//            if(!con.isClosed()){
//                System.out.println("Connected");
//            }
//            String sql = "select * from users where username = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, username);
//            log.info(ps.toString());
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                String res_name = rs.getString("username");
//                String res_password = rs.getString("password");
//                String info = String.format("%s: %s\n", res_name, res_password);
//                result.append(info);
//                log.info(info);
//            }
//
//            rs.close();
//            ps.close();
//        } catch (ClassNotFoundException e) {
//            log.error("Can not connect");
//        } catch (SQLException e){
//            log.error(e.toString());
//        }
//        return result.toString();
//    }



    //vuln code
//    @GetMapping("/vuln")
//    public String vuln(@RequestParam String username) {
//        StringBuilder result = new StringBuilder();
//        try{
//            Class.forName(driver);
//            Connection con = DriverManager.getConnection(url, user, password);
//
//            if(!con.isClosed()){
//                System.out.println("Connected");
//            }
//            String sql = "select * from users where username = '" + username + "'";
//            PreparedStatement ps = con.prepareStatement(sql);
//            log.info(ps.toString());
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                String res_name = rs.getString("username");
//                String res_password = rs.getString("password");
//                String info = String.format("%s: %s\n", res_name, res_password);
//                result.append(info);
//                log.info(info);
//            }
//
//            rs.close();
//            ps.close();
//        } catch (ClassNotFoundException e) {
//            log.error("Can not connect");
//        } catch (SQLException e){
//            log.error(e.toString());
//        }
//        return result.toString();
//    }


//    @GetMapping("/findUserByName")
//    public List<User> findUserByName(@RequestParam String name) {
//        String sql = "SELECT * FROM users WHERE username = '" + name + "'";
//        EntityManager entityManager = null;
//        Query query = (Query) entityManager.createNativeQuery(sql);
//        List<User> result = query.getResultList();
//        return result;
//
//
//    }



}
