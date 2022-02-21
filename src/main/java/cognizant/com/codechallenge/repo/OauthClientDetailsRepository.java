/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.repo;

import cognizant.com.codechallenge.model.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nandom Gusen
 */
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {
OauthClientDetails findByClientId(String clientId);
}
