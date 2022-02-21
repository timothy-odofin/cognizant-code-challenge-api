/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.repo.auth;

import cognizant.com.codechallenge.model.auth.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author JIDEX
 */
public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails, Long> {
OauthClientDetails findByClientId(String clientId);
}
