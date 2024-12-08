package com.example.cleartrip_social_media.services;

import com.example.cleartrip_social_media.builders.PostInteractionResponseDTOBuilder;
import com.example.cleartrip_social_media.builders.PostResponseDTOBuilder;
import com.example.cleartrip_social_media.dtos.*;
import com.example.cleartrip_social_media.enums.PostInteractionType;
import com.example.cleartrip_social_media.exceptions.PostNotFoundException;
import com.example.cleartrip_social_media.exceptions.UserNotFoundException;
import com.example.cleartrip_social_media.exceptions.ValidationException;
import com.example.cleartrip_social_media.models.Post;
import com.example.cleartrip_social_media.models.PostInteraction;
import com.example.cleartrip_social_media.models.User;
import com.example.cleartrip_social_media.repositories.PostInteractionRepository;
import com.example.cleartrip_social_media.repositories.PostRepository;
import com.example.cleartrip_social_media.repositories.UserRepository;
import com.example.cleartrip_social_media.strategies.Validator;
import com.example.cleartrip_social_media.validators.FeedRequestDTOValidator;
import com.example.cleartrip_social_media.validators.PostInteractionRequestDTOValidator;
import com.example.cleartrip_social_media.validators.PostRequestDTOValidator;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostInteractionRepository postInteractionRepository;
    private final Validator<PostRequestDTO> postRequestDTOValidator;
    private final Validator<FeedRequestDTO> feedRequestDTOValidator;
    private final Validator<PostInteractionRequestDTO> postInteractionRequestDTOValidator;
    @Autowired
    public PostService(PostRepository postRepository,
                       UserRepository userRepository,
                       PostInteractionRepository postInteractionRepository,
                       @Lazy PostRequestDTOValidator postRequestDTOValidator,
                       @Lazy FeedRequestDTOValidator feedRequestDTOValidator,
                       @Lazy PostInteractionRequestDTOValidator postInteractionRequestDTOValidator
                       ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postInteractionRepository = postInteractionRepository;
        this.postRequestDTOValidator = postRequestDTOValidator;
        this.feedRequestDTOValidator = feedRequestDTOValidator;
        this.postInteractionRequestDTOValidator = postInteractionRequestDTOValidator;
    }
    public PostResponseDTO uploadPost(PostRequestDTO postRequestDTO) throws ValidationException {
        postRequestDTOValidator.validate(postRequestDTO);

        Optional<User> optionalUser = userRepository.getUserById(postRequestDTO.getUserId());
        if (optionalUser.isEmpty()) throw new UserNotFoundException("User '"+ postRequestDTO.getUserId() +"' does not exist!");
        User user = optionalUser.get();
        Post post = new Post();
        post.setId(Generators.timeBasedEpochGenerator().generate().toString());
        post.setUser(user);
        post.setContent(postRequestDTO.getContent());
        post.setLikes(0L);
        post.setDislikes(0L);
        post.setPostInteractions(new ArrayList<>());
        post.setPostedAt(LocalDateTime.now());

        Post savedPost = postRepository.save(post);
        user.getPosts().add(savedPost);
        User savedUser = userRepository.save(user);

        return PostResponseDTOBuilder.getBuilder()
                .setId(post.getId())
                .setUserName(savedUser.getFirstName().trim() + " " + savedUser.getLastName().trim())
                .setContent(post.getContent())
                .setLikes(post.getLikes())
                .setDislikes(post.getDislikes())
                .setPostedAt(post.getPostedAt())
                .build();
    }

    public FeedResponseDTO getFeed(FeedRequestDTO feedRequestDTO) throws ValidationException {
        feedRequestDTOValidator.validate(feedRequestDTO);
        User user = userRepository.getUserById(feedRequestDTO.getUserId()).get();
        List<Post> posts = postRepository.getAllPosts();
        List <Post> postsFromFollowees = findPostsFromFollowers(user, posts);
        List <Post> otherPosts = findOtherPosts(user, posts);

        postsFromFollowees.sort((post1, post2) -> post2.getPostedAt().compareTo(post1.getPostedAt()));
        otherPosts.sort((post1, post2) -> post2.getPostedAt().compareTo(post1.getPostedAt()));

        List <PostResponseDTO> postResponseDTOS = getPostResponseDTOs(user, postsFromFollowees, otherPosts);

        FeedResponseDTO feedResponseDTO = new FeedResponseDTO();
        feedResponseDTO.setPostResponseDTOS(postResponseDTOS);
        feedResponseDTO.setTotalPosts(postResponseDTOS.size());
//        System.out.println(feedResponseDTO);
        return feedResponseDTO;
    }

    public List<PostResponseDTO> getPostResponseDTOs(User user, List<Post> postsFromFollowees, List<Post> otherPosts) {
        List <PostResponseDTO> postResponseDTOS = new ArrayList <>();

        for (Post post : postsFromFollowees) {
            postResponseDTOS.add(
                    PostResponseDTOBuilder.getBuilder()
                            .setId(post.getId())
                            .setUserName(post.getUser().getFirstName().trim() + " " + post.getUser().getLastName().trim())
                            .setContent(post.getContent())
                            .setLikes(post.getLikes())
                            .setDislikes(post.getDislikes())
                            .setPostedAt(post.getPostedAt())
                            .build()
            );
        }

        for (Post post : otherPosts) {
            postResponseDTOS.add(
                    PostResponseDTOBuilder.getBuilder()
                            .setId(post.getId())
                            .setUserName(post.getUser().getFirstName().trim() + " " + post.getUser().getLastName().trim())
                            .setContent(post.getContent())
                            .setLikes(post.getLikes())
                            .setDislikes(post.getDislikes())
                            .setPostedAt(post.getPostedAt())
                            .build()
            );
        }

        return postResponseDTOS;
    }

    public PostInteractionResponseDTO interactWithPost(PostInteractionRequestDTO postInteractionRequestDTO) throws ValidationException {
        postInteractionRequestDTOValidator.validate(postInteractionRequestDTO);

        Optional<User> optionalUser = userRepository.getUserById(postInteractionRequestDTO.getUserId());
        if (optionalUser.isEmpty()) throw new UserNotFoundException(
                "user '" + postInteractionRequestDTO.getUserId() + "' does not exist!"
        );
        Optional<Post> optionalPost = postRepository.getPostById(postInteractionRequestDTO.getPostId());
        if (optionalPost.isEmpty()) throw new PostNotFoundException(
                "post '" + postInteractionRequestDTO.getPostId() + "' does not exist!"
        );

        User user = optionalUser.get();
        Post post = optionalPost.get();

        PostInteraction postInteraction = new PostInteraction();
        postInteraction.setId(Generators.timeBasedEpochGenerator().generate().toString());
        postInteraction.setPostId(post.getId());
        postInteraction.setUserId(user.getId());
        postInteraction.setType(postInteractionRequestDTO.getAction());
        postInteraction.setTime(LocalDateTime.now());

        PostInteraction savedPostInteraction = postInteractionRepository.save(postInteraction);

        // Using else if to make sure to handle more values in the enum in future (example: comments,shares etc...)
        if (savedPostInteraction.getType().equals(PostInteractionType.LIKE)) post.setLikes(post.getLikes()+1);
        else if (savedPostInteraction.getType().equals(PostInteractionType.DISLIKE)) post.setDislikes(post.getDislikes()+1);
        removeRedundantPostInteractions(user, post, savedPostInteraction);

        user.getActivity().add(savedPostInteraction);
        post.getPostInteractions().add(savedPostInteraction);

        Post savedPost = postRepository.save(post);
        User savedUser = userRepository.save(user);

        return PostInteractionResponseDTOBuilder.getBuilder()
                .setPostId(savedPost.getId())
                .setUserId(savedUser.getId())
                .setAction(postInteraction.getType())
                .setTime(postInteraction.getTime())
                .build();

    }

    public void removeRedundantPostInteractions(User user, Post post, PostInteraction savedPostInteraction) {
        if (checkIfInteractionAlreadyExists(
                user,
                savedPostInteraction.getPostId(),
                (savedPostInteraction.getType().equals(PostInteractionType.LIKE)) ? PostInteractionType.DISLIKE : PostInteractionType.LIKE)
        ) {
            removePostInteraction(
                    user,
                    savedPostInteraction.getPostId(),
                    (savedPostInteraction.getType().equals(PostInteractionType.LIKE)) ? PostInteractionType.DISLIKE : PostInteractionType.LIKE
            );
        }
        if (checkIfInteractionAlreadyExists(
                post,
                user.getId(),
                (savedPostInteraction.getType().equals(PostInteractionType.LIKE)) ? PostInteractionType.DISLIKE : PostInteractionType.LIKE)
        ) {
            removePostInteraction(
                    post,
                    user.getId(),
                    (savedPostInteraction.getType().equals(PostInteractionType.LIKE)) ? PostInteractionType.DISLIKE : PostInteractionType.LIKE
            );
            if (savedPostInteraction.getType().equals(PostInteractionType.LIKE)) post.setDislikes(post.getDislikes()-1);
            else if (savedPostInteraction.getType().equals(PostInteractionType.DISLIKE)) post.setLikes(post.getLikes()-1);
        }
        return;
    }

    public boolean checkIfInteractionAlreadyExists(Post post, String userId, PostInteractionType action) {
        for (PostInteraction postInteraction : post.getPostInteractions()) {
            if (!postInteraction.getUserId().equals(userId)) continue;
            if (postInteraction.getType().equals(action)) return true;
        }
        return false;
    }
    public void removePostInteraction(Post post, String userId, PostInteractionType action) {
        PostInteraction toBeDeleted = null;
        for (PostInteraction postInteraction : post.getPostInteractions()) {
            if (!postInteraction.getUserId().equals(userId)) continue;
            if (postInteraction.getType().equals(action)) {
                toBeDeleted = postInteraction;
                break;
            }
        }
        if (toBeDeleted != null) post.getPostInteractions().remove(toBeDeleted);
        return;
    }
    public boolean checkIfInteractionAlreadyExists(User user, String postId, PostInteractionType action) {
        for (PostInteraction postInteraction : user.getActivity()) {
            if (!postInteraction.getPostId().equals(postId)) continue;
            if (postInteraction.getType().equals(action)) return true;
        }
        return false;
    }

    public void removePostInteraction(User user, String postId, PostInteractionType action) {
        PostInteraction toBeDeleted = null;
        for (PostInteraction postInteraction : user.getActivity()) {
            if (!postInteraction.getPostId().equals(postId)) continue;
            if (postInteraction.getType().equals(action)) {
                toBeDeleted = postInteraction;
                break;
            }
        }
        if (toBeDeleted != null) user.getActivity().remove(toBeDeleted);
        return;
    }

    public List<Post> findPostsFromFollowers(User user, List<Post> posts) {
        List <Post> postsFromFollowees = new ArrayList <>();
        for (Post post : posts) {
            if (user.getFollowees().contains(post.getUser())) {
                postsFromFollowees.add(post);
            }
        }

        return postsFromFollowees;
    }

    public List<Post> findOtherPosts(User user, List<Post> posts) {
        List<Post> otherPosts = new ArrayList<>();
        for (Post post : posts) {
            if (!user.getFollowees().contains(post.getUser())) {
                otherPosts.add(post);
            }
        }
        return otherPosts;
    }

    public Optional<Post> getPostById(String postId) {
        return postRepository.getPostById(postId);
    }

}
