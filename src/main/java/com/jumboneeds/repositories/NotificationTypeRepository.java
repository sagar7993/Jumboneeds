package com.jumboneeds.repositories;

import com.jumboneeds.entities.NotificationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationTypeRepository extends CrudRepository<NotificationType, String> {

    @Transactional
    NotificationType findByType(@Param("type") int type);

}