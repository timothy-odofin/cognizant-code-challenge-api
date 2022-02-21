package cognizant.com.codechallenge.dto.user;

import lombok.Data;

@Data
public class AuthTokenInfo {

	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;

	@Override
	public String toString() {
		return "AuthTokenInfo [access_token=" + access_token + ", token_type=" + token_type + ", refresh_token="
				+ refresh_token + ", expires_in=" + expires_in + ", scope=" + scope + "]";
	}
	
	
}
