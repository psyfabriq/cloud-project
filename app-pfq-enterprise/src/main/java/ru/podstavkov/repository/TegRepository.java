package ru.podstavkov.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.podstavkov.entity.Category;
import ru.podstavkov.entity.Teg;


@Repository
public interface TegRepository extends JpaRepository<Teg, String> {

    List<Teg> findByIdIn(List<String> tegsIds);

    Optional<Teg> findByName(String name);

    Boolean existsByName(String name);
    
 //   @Query("select b from teg b where b.teg = :teg or b.id = :id ")
  //  Optional<Teg> findByTegOrID  (@Param("teg") String teg, @Param("id") String id);
    
}
