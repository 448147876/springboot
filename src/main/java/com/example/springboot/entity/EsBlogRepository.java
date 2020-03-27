package com.example.springboot.entity;

import com.example.springboot.entity.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {

    //分页查询博客
    Page<EsBlog> findByTitleOrSummaryOrContent(String title, String summary, String content, Pageable pageable);


}
