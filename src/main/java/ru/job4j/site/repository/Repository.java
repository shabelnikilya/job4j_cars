package ru.job4j.site.repository;

import ru.job4j.site.Post;

import java.util.List;

public interface Repository {

    List<Post> findAll();

    List<Post> lastDayPost();

    List<Post> postWithPhoto();

    List<Post> findPostByBrand(String brand);
}
