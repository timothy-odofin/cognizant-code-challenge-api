/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.repo.auth;

import cognizant.com.codechallenge.model.auth.Roles;
import cognizant.com.codechallenge.model.auth.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 *
 * @author Nandom Gusen
 */
public interface UsersRepo extends JpaRepository<Users, Long>{
    Optional<Users> findByUsername(String username);
}
