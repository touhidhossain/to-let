package org.np.tolet.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.np.tolet.domain.User;
import org.np.tolet.enumeration.Role;
import org.np.tolet.service.UserService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class UserServiceImpl implements UserService {
    private final String usersFileLocation = FileLocation.usersFile;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public User login(Scanner scanner) throws Exception {
        System.out.println("Please input your user details.");
        System.out.print("username: ");
        String userName = scanner.nextLine();
        System.out.print("password: ");
        String password = scanner.nextLine();

        Set<User> userList = objectMapper.readValue(new File(usersFileLocation), new TypeReference<>() {
        });
        Map<String, User> userMap = new HashMap<>();
        for (User u : userList) {
            userMap.put(u.getUserName(), u);
        }
        User user = userMap.get(userName);
        if (user != null) {
            if (user.getPassword().equals(password)) return user;
        }
        throw new Exception("Username or password does not match!");
    }

    @Override
    public User registration(Scanner scanner) throws Exception {

        User user = new User();

        while (user.getFullName() == null) {
            System.out.print("Please input your full name: ");
            user.setFullName(scanner.nextLine());
        }
        while (user.getRole() == null) {
            System.out.print("Are you house owner or Tenant?\n" +
                    " Please input (1) if you are house owner or (2) if you are a tenant: ");
            int userInput = scanner.nextInt();
            scanner.nextLine();
            if (userInput == 1) {
                user.setRole(Role.HOUSE_OWNER);
            } else if (userInput == 2) {
                user.setRole(Role.TENANT);
            }
        }
        while (user.getUserName() == null) {
            System.out.print("Please input your username: ");
            user.setUserName(scanner.nextLine());
        }
        while (user.getPassword() == null) {
            System.out.print("Please input your password: ");
            user.setPassword(scanner.nextLine());
        }


        Set<User> userList = objectMapper.readValue(new File(usersFileLocation), new TypeReference<>() {
        });
        if (userList.contains(user)) {
            throw new Exception("User already exists!");
        }
        userList.add(user);
        objectMapper.writeValue(new File(usersFileLocation), userList);
        return user;
    }


}
