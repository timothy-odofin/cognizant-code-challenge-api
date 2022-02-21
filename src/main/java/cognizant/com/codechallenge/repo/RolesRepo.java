/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.repo;

import cognizant.com.codechallenge.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nandom Gusen
 */
public interface RolesRepo extends JpaRepository<Roles, Integer>{
    
}
