/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cognizant.com.codechallenge.model.auth;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author JIDEX
 */
@Entity
@Table(name = "oauth_client_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OauthClientDetails.findAll", query = "SELECT o FROM OauthClientDetails o")})
@Data
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //
    //(min = 1, max = 255)
    @Column(name = "client_id")
    private String clientId;
    //(max = 255)
    @Column(name = "resource_ids")
    private String resourceIds;
    //(max = 255)
    @Column(name = "client_secret")
    private String clientSecret;
    //(max = 255)
    @Column(name = "scope")
    private String scope;
    @Column(name="platform")
    private String platform;
    //(max = 255)
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;
    //(max = 255)
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    //(max = 255)
    @Column(name = "authorities")
    private String authorities;
    @Column(name = "access_token_validity")
    private Integer accessTokenValidity;
    @Column(name = "refresh_token_validity")
    private Integer refreshTokenValidity;
    //(max = 4096)
    @Column(name = "additional_information")
    private String additionalInformation;
    //(max = 100)
    @Column(name = "autoapprove")
    private String autoapprove;

    public OauthClientDetails() {

    }

    public OauthClientDetails(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(String autoapprove) {
        this.autoapprove = autoapprove;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clientId != null ? clientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OauthClientDetails)) {
            return false;
        }
        OauthClientDetails other = (OauthClientDetails) object;
        if ((this.clientId == null && other.clientId != null) || (this.clientId != null && !this.clientId.equals(other.clientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "swipe.net.ng.model.OauthClientDetails[ clientId=" + clientId + " ]";
    }
    
}
