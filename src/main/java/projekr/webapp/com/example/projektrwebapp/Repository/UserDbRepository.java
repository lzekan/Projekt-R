package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.UserDb;

@Repository
public interface UserDbRepository extends CrudRepository<UserDb, Long> {

    @Query(
            value = "select * from user_db u where u.id_user= :iduser"
    )
    UserDb findUserDbById(@Param("iduser") long idUser);
}
