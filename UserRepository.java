package org.example.Repositories;


import org.example.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}

/*import org.springframework.stereotype.Repository;
import org.example.suntravel.Models.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class UserRepository {
   private List<User> users=new ArrayList<>();

   public UserRepository(){
       users.add(new User(1L, "Sara", "Šljivo","sara.sljivo.22@size.ba"));
   }
   public List<User> findAll() { return users;}
    public Optional<User> findById(long id) {
       return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public void save(User user) {users.add(user);}
    public void deleteById(long id) {users.removeIf(user -> user.getId() == id);}
}*/
