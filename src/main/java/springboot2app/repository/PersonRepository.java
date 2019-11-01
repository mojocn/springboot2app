package springboot2app.repository;

import springboot2app.model.PersonDomain;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends MongoRepository<PersonDomain, String> {

    List<PersonDomain> findByLastName(@Param("name") String name);

}