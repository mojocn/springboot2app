package springboot2app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot2app.entity.User;

import java.util.List;
import java.util.Optional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByEmail(String email);

    List<User> findByMobile(String mobile);

    Optional<User> findById(Integer id);

    Page<User> findAll(Pageable pageable);

    Page<User> findByEmail(String firstName, Pageable pageable);


}
