/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.repo.auth;

import cognizant.com.codechallenge.model.auth.RoleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 *
 * @author JIDEX
 */
public interface RoleUserRepo extends JpaRepository<RoleUser,Integer> {
    @Query("select st from RoleUser st where st.userId.id=?1")
    List<RoleUser> findByAccount(Long id);

}
