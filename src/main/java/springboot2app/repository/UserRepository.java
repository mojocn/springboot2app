package springboot2app.repository;

import springboot2app.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByEmail(String email);

    List<UserEntity> findByMobile(String mobile);

    Optional<UserEntity> findById(Integer id);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findByEmail(String firstName, Pageable pageable);


}
