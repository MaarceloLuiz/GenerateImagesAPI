package com.project.generating.api;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImagesRepository extends MongoRepository<Informations, String>{

    List<Informations> findByOrderByRanking();
}
