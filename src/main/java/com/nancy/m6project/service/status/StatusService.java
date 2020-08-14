package com.nancy.m6project.service.status;

import com.nancy.m6project.model.status.Status;
import org.springframework.stereotype.Service;


public interface StatusService {
    Status save(Status status);
    Status findOne(Long id);
    Iterable<Status> findStatusByAccount_IdOrderByCreateDateDesc(Long id);
}
