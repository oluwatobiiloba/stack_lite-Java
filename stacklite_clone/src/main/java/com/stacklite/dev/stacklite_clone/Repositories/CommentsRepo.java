package com.stacklite.dev.stacklite_clone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacklite.dev.stacklite_clone.Model.Comment;

public interface CommentsRepo extends JpaRepository<Comment, Integer> {

}
