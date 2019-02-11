package com.alliance.jumpstart.repository;

import com.alliance.jumpstart.entities.JobHiring;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JobHiringRepository extends CrudRepository<JobHiring, Integer> {

    //@Query("from Task t where t.status=:status")
    List<JobHiring> findByStatus(String status);

    List<JobHiring> findByUserIdAndStatus(int userId,  String status);

 /*   @Query("from Task t where t.userId=:userId and  t.status=:status")
    List<Task> findByUserIdStatus(@Param("userId") int userId, @Param("status") String status);*/

    @Query("from JobHiring t where t.id BETWEEN  :start and :end")
    List<JobHiring> findBetween(@Param("start") int start, @Param("end") int end);

}