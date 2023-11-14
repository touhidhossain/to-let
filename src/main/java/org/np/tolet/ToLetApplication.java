package org.np.tolet;

import org.np.tolet.domain.User;
import org.np.tolet.enumeration.Role;
import org.np.tolet.service.UserService;
import org.np.tolet.service.impl.UserServiceImpl;

import java.util.Scanner;

public class ToLetApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserServiceImpl();

        while (true) {
            String welcomeMessage = """
                    Welcome to np ToLet
                    """;
            System.out.print(welcomeMessage);
            System.out.print("Input (L) for Login and (R) for Registration: ");
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "L", "l" -> {
                    try {
                        User user = userService.login(scanner);
                        System.out.println("Welcome " + user.getFullName());
                        if (user.getRole().equals(Role.HOUSE_OWNER)) {
                            houseOwnersActivity(scanner);
                        } else {
                            tenantActivity(scanner);
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                case "R", "r" -> {
                    try {
                        userService.registration(scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    private static void houseOwnersActivity(Scanner scanner) {
        String houseOwnerMessage = """
                For previous post input (P).
                For new post input (N).
                Please input your choice:
                """;
        System.out.print(houseOwnerMessage);
        String userChoice = scanner.nextLine();
    }

    private static void tenantActivity(Scanner scanner) {

        String tenantMessage = """
                Please input area code!
                All Area code will be displayed here
                """;
        System.out.print(tenantMessage);
        String areaCode = scanner.nextLine();
    }
}
