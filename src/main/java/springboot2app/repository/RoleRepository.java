package springboot2app.repository;

import springboot2app.model.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findById(Integer id);
    Optional<RoleEntity> findByName(String name);

    @Query(value = "select r.id,r.created_at, r.updated_at, r.name, r.permission ,count(u.id) as user_count from roles r left join users u on r.id = u.role_id group by r.id order by r.id desc /* #pageable# */ ", countQuery = "select count(*) from roles", nativeQuery = true)
    Page<RoleEntity> findAll(Pageable pageable);


}
