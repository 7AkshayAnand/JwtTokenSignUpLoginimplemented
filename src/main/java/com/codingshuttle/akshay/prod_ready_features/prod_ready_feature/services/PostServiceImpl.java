package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.PostDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.PostEntity;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.exeptions.ResourceNotFoundException;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements  PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inptuPost) {
        PostEntity postEntity = modelMapper.map(inptuPost, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
//below we are getting the User details from the SecurityContextHolder as from previous user login we have already
//        the user details
       User user= (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       log.info("user : {user}"+user);
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id= " + postId));

        return modelMapper.map(postEntity, PostDTO.class);

    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
       PostEntity olderPost=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post not found"));
        inputPost.setId(postId);
       modelMapper.map(inputPost,olderPost);
       PostEntity savedPostEntity=postRepository.save(olderPost);
       return modelMapper.map(savedPostEntity,PostDTO.class);

    }
}
