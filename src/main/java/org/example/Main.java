package org.example;

import org.example.dao.User;
import org.example.dao.UserRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception{

        ApplicationContext container = new ClassPathXmlApplicationContext("application-context.xml");
        UserRepository userRepository = container.getBean("userRepo",UserRepository.class);

        List<User> users = userRepository.selectAll();
        for (User user : users){
            System.out.println(user.toString());
        }


    }
}