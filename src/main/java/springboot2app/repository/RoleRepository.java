package springboot2app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot2app.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findById(Integer id);

    Optional<Role> findByName(String name);

    @Query(value = "select r.id,r.created_at, r.updated_at, r.name, r.permission ,count(u.id) as user_count from role r left join user u on r.id = u.role_id group by r.id order by r.id desc /* #pageable# */ ", countQuery = "select count(*) from roles", nativeQuery = true)
    Page<Role> findAll(Pageable pageable);


}
