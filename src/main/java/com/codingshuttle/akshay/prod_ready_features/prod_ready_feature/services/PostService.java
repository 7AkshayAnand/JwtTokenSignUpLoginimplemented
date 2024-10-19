package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();
    PostDTO createNewPost(PostDTO inptuPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);
}
