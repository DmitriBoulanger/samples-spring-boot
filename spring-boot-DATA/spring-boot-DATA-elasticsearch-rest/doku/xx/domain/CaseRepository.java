package XXXXXXXXXXXXXX.services.rcase.domain;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import XXXXXXXXXXXXXX.services.rcase.Case;

public interface CaseRepository extends ElasticsearchCrudRepository<Case, String> {

    List<Case> findByProjectIn(List<String> projects, Pageable pageable);

    Long countByProjectIn(List<String> projects);
}
