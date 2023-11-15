package org.np.tolet;

import org.np.tolet.domain.Area;
import org.np.tolet.domain.Post;
import org.np.tolet.domain.User;
import org.np.tolet.enumeration.Role;
import org.np.tolet.service.AreaService;
import org.np.tolet.service.PostService;
import org.np.tolet.service.UserService;
import org.np.tolet.service.impl.AreaServiceImpl;
import org.np.tolet.service.impl.PostServiceImpl;
import org.np.tolet.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.*;

public class ToLetApplication {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserServiceImpl();
    private static final PostService postService = new PostServiceImpl();

    public static void main(String[] args) {


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
                            houseOwnersActivity(user);
                        } else {
                            tenantActivity();
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

    private static void houseOwnersActivity(User user) {
        String houseOwnerMessage = """
                For previous post input (P).
                For new post input (N).
                Please input your choice:
                """;
        System.out.print(houseOwnerMessage);
        String userChoice = scanner.nextLine();
        switch (userChoice) {
            case "P" -> {
                try {
                    TreeSet<Post> usersPost = postService.getAllPostByUser(user.getUserName());
                    NavigableSet<Post> descendingSet = usersPost.descendingSet();
                    for (Post post : descendingSet) {
                        printPost(post);
                    }
                    if (!descendingSet.isEmpty()) {
                        System.out.print("Input post id to delete a post: ");
                        try {
                            long postId = scanner.nextLong();
                            scanner.nextLine();
                            for (Post post : usersPost) {
                                if (post.getId() == postId) {
                                    postService.deletePost(post);
                                } else {
                                    System.out.println("Invalid post id!");
                                }
                            }
                        } catch (NumberFormatException nfe) {

                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            case "N" -> {
                try {
                    postService.createPost(scanner, user);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void tenantActivity() {
        String tenantMessage = "List of area code!";
        System.out.println(tenantMessage);
        AreaService areaService = new AreaServiceImpl();
        Set<Area> areaSet = new HashSet<>();
        try {
            areaSet = areaService.getAllAreas();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Area area : areaSet) {
            System.out.println("For (" + area.getName() + ") input (" + area.getId() + ")");
        }
        System.out.print("Please input a code: ");
        int areaCode = scanner.nextInt();
        scanner.nextLine();

        TreeSet<Post> usersPost = null;
        try {
            usersPost = postService.getAllPostByArea(areaCode);
            NavigableSet<Post> descendingSet = usersPost.descendingSet();
            for (Post post : descendingSet) {
                printPost(post);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void printPost(Post post) {
        System.out.println("********************************");
        System.out.println("ID: " + post.getId());
        System.out.println("Area: " + post.getId());
        System.out.println("House Type: " + post.getHouseType().toString());
        System.out.println("Tenant Type: " + post.getTenantType().toString());
        System.out.println("Description: " + post.getDescription());
        System.out.println("Posted By: " + post.getPostedBy().getFullName());
        System.out.println("Contact Number: " + post.getContactNumber());
        System.out.println("Posted on: " + post.getAddDateTime());
        System.out.println("********************************");
    }
}
