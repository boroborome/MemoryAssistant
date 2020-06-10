package com.happy3w.memoryassistant.repository;

import com.happy3w.memoryassistant.model.MAKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MAKeywordRepository extends JpaRepository<MAKeyword, Long> {
    List<MAKeyword> findAllByKeywordLike(String keyword);

    List<MAKeyword> findAllByKeywordIn(Collection<String> keywords);
}
