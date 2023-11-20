package org.np.tolet.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.np.tolet.domain.Area;
import org.np.tolet.domain.Post;
import org.np.tolet.domain.User;
import org.np.tolet.enumeration.HouseType;
import org.np.tolet.enumeration.TenantType;
import org.np.tolet.service.AreaService;
import org.np.tolet.service.PostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PostServiceImpl implements PostService {
    private final String postFileLocation = FileLocation.postFile;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AreaService areaService = new AreaServiceImpl();

    @Override
    public Post createPost(Scanner scanner, User user) throws IOException {
        TreeSet<Post> posts = objectMapper.readValue(new File(postFileLocation), new TypeReference<>() {
        });

        long newPostId = 1;
        if (posts != null && !posts.isEmpty()) {
            newPostId = posts.last().getId() + 1L;
        }

        Map<Integer, Area> areaMap = new HashMap<>();
        Set<Area> areaSet = new HashSet<>();
        try {
            areaSet = areaService.getAllAreas();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Area area : areaSet) {
            System.out.println("For (" + area.getName() + ") input (" + area.getId() + ")");
            areaMap.put(area.getId(), area);
        }

        Area area = null;

        while (area == null) {
            System.out.print("Please input a code: ");
            int areaCode = scanner.nextInt();
            scanner.nextLine();
            area = areaMap.get(areaCode);
            if (area == null) {
                System.out.println("Invalid input!");
            }
        }

        Post post = new Post();
        post.setId(newPostId);
        post.setPostedBy(user);
        post.setArea(area);
        post.setAddDateTime(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));
        post.setEnabled(true);

        while (post.getHouseType() == null) {
            System.out.print("Please input House type, (1) for full house, (2) for flat, (3) for sublet, (4) for seat: ");
            switch (scanner.nextLine()) {
                case "1" -> post.setHouseType(HouseType.FULL);
                case "2" -> post.setHouseType(HouseType.FLAT);
                case "3" -> post.setHouseType(HouseType.SUBLET);
                case "4" -> post.setHouseType(HouseType.SEAT);

            }
        }

        while (post.getTenantType() == null) {
            System.out.print("Please input tenant type, (1) for family, (2) for bachelor: ");
            switch (scanner.nextLine()) {
                case "1" -> post.setTenantType(TenantType.FAMILY);
                case "2" -> post.setTenantType(TenantType.BACHELOR);
            }
        }

        while (post.getContactNumber() == null) {
            System.out.print("Please provide your contact number: ");
            post.setContactNumber(scanner.nextLine());
        }

        while (post.getDescription() == null) {
            System.out.print("Please add description: ");
            post.setDescription(scanner.nextLine());
        }


        if (posts == null) posts = new TreeSet<>();

        posts.add(post);
        Files.deleteIfExists(Path.of(postFileLocation));
        Files.createFile(Path.of(postFileLocation));
        objectMapper.writeValue(new File(postFileLocation), posts);

        return post;
    }

    @Override
    public Post deletePost(Post post) throws IOException {
        Set<Post> posts = objectMapper.readValue(new File(postFileLocation), new TypeReference<>() {
        });
        posts.remove(post);
        Files.deleteIfExists(Path.of(postFileLocation));
        Files.createFile(Path.of(postFileLocation));
        objectMapper.writeValue(new File(postFileLocation), posts);
        return post;
    }

    @Override
    public TreeSet<Post> getAllPost() throws IOException {
        return objectMapper.readValue(new File(postFileLocation), new TypeReference<>() {
        });
    }

    @Override
    public TreeSet<Post> getAllPostByArea(int areaId) throws IOException {
        Set<Post> posts = getAllPost();
        TreeSet<Post> postsOfAnArea = new TreeSet<>();
        for (Post post : posts) {
            if (post.getArea().getId() == areaId) postsOfAnArea.add(post);
        }
        return postsOfAnArea;
    }

    @Override
    public TreeSet<Post> getAllPostByUser(String userName) throws IOException {
        Set<Post> posts = getAllPost();
        TreeSet<Post> usersPost = new TreeSet<>();
        for (Post post : posts) {
            if (post.getPostedBy().getUserName().equals(userName)) usersPost.add(post);
        }
        return usersPost;
    }
}
