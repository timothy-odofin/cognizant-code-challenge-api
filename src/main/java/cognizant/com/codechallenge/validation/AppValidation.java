package cognizant.com.codechallenge.validation;

import cognizant.com.codechallenge.dto.user.Login;
import cognizant.com.codechallenge.dto.user.SignUp;

import static cognizant.com.codechallenge.utils.ApiMessages.INVALID_ENTRY;

public class AppValidation {

    public  static String validateSignUp(SignUp payload) {
        if (payload.getUsername() == null || payload.getUsername().trim().isEmpty())
            return String.format(INVALID_ENTRY, "Username");
        else if (payload.getName() == null || payload.getName().trim().isEmpty())
            return String.format(INVALID_ENTRY, "Name");
        else if (payload.getPassword() == null || payload.getPassword().trim().isEmpty())
            return String.format(INVALID_ENTRY, "Password");
        else
            return null;

    }

  public  static String validateLogin(Login payload) {
        if (payload.getUsername() == null || payload.getUsername().trim().isEmpty())
            return String.format(INVALID_ENTRY, "Username");
        else if (payload.getPassword() == null || payload.getPassword().trim().isEmpty())
            return String.format(INVALID_ENTRY, "Password");
        else
            return null;

    }

}
