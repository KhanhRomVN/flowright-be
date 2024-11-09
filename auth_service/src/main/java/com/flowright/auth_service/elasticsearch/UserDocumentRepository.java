package com.flowright.auth_service.elasticsearch;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, String> {
    List<UserDocument> findByUsernameContaining(String username);

    List<UserDocument> findByEmailContaining(String email);
}
