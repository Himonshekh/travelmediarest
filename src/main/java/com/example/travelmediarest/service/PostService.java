package com.example.travelmediarest.service;

import com.example.travelmediarest.dto.PostDto;
import com.example.travelmediarest.model.Location;
import com.example.travelmediarest.model.Post;
import com.example.travelmediarest.repository.LocationRepository;
import com.example.travelmediarest.repository.PostRepository;
import com.example.travelmediarest.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PostService {
    @Autowired
    @Qualifier("postRepository")
    private PostRepository postRepository;
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    @Autowired
    @Qualifier("locationRepository")
    private LocationRepository locationRepository;


    public void deletePostById(Long id){
        postRepository.deleteById(id);
    }

    public void updatePostByPin(Long id, String mail) {
        resetPinPost(mail);
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.get();
        post.setPined(1L);
        postRepository.save(post);
        log.info("pinned successfully");
    }

    public void resetPinPost(String mail) {
        List<Post> posts = postRepository.findAll();
        Optional<com.example.travelmediarest.model.User> userOptional = userRepository.findByMail(mail);
        com.example.travelmediarest.model.User user = null;
        user = userOptional.get();
        Long id = user.getId();
        for (Post post : posts) {
            if (post.getUser().getId().equals(id)) {
                post.setPined(0L);
            }
        }
        postRepository.saveAll(posts);
    }

    public void updateThePost(PostDto postDto) {
        Optional<Post> postOptional = postRepository.findById(postDto.getId());
        Post post = postOptional.get();
        post.setStatus(postDto.getStatus());
        post.setLocation(getLocation(postDto.getLocation()));
        post.setPrivacy(postDto.getPrivacy());
        postRepository.save(post);
    }

    public PostDto fetchPostById(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.get();
        return new PostDto(post.getId(), post.getUser(), post.getStatus(), post.getLocation().getName(), post.getPrivacy(), post.getPined());
    }

    public void saveThisPost(PostDto postDto, String mail) {
        Post post = new Post(toUser(mail), postDto.getStatus(), getLocation(postDto.getLocation()), postDto.getPrivacy());
        postRepository.save(post);

        log.info("post save successfully");

    }

    public com.example.travelmediarest.model.User toUser(String mail) {

        Optional<com.example.travelmediarest.model.User> userOptional = userRepository.findByMail(mail);
        com.example.travelmediarest.model.User user1 = null;

        if (userOptional.isPresent()) {
            user1 = userOptional.get();
        }

        return user1;
    }

    public Location getLocation(String location) {

        Location location1 = locationRepository.findByName(location);

        return location1;
    }


    public List<PostDto> fetchPostByUser(String mail) {
        List<PostDto> postDtoList = new ArrayList<>();

        List<Post> posts = postRepository.findAllByUser_Mail(mail);

        Collections.sort(posts, new customSort());

        for (Post post : posts) {
            postDtoList.add(new PostDto(post.getId(), post.getUser(), post.getStatus(), post.getLocation().getName(), post.getPrivacy(), post.getPined()));
        }

        return postDtoList;
    }

    public List<PostDto> fetchForHomePage(String mail) {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();

        Optional<com.example.travelmediarest.model.User> userOptional = userRepository.findByMail(mail);
        com.example.travelmediarest.model.User user = null;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            return postDtoList;
        }

        Long id = user.getId();

        Collections.sort(posts, new customSort());

        for (Post post : posts) {
            if (post.getUser().getId() == id || post.getPrivacy().charAt(1) == 'u') {
                postDtoList.add(new PostDto(post.getId(), post.getUser(), post.getStatus(), post.getLocation().getName(), post.getPrivacy(), post.getPined()));
            }
        }

        return postDtoList;
    }

    static class customSort implements Comparator<Post> {

        @Override
        public int compare(Post o1, Post o2) {
            int ret = o2.getPined().compareTo(o1.getPined());

            if (ret != 0) {
                return ret;
            } else {
                return o2.getPlacedAt().compareTo(o1.getPlacedAt());
            }
        }
    }
}
