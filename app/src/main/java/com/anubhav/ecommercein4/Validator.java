package com.anubhav.ecommercein4;

/**
 * Created by anubh on 31-Dec-16.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern emailpattern,mobilepattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String MOBILENO_PATTERN =
            "^[7-9][0-9]{9}$";

    public Validator() {
        emailpattern = Pattern.compile(EMAIL_PATTERN);
        mobilepattern = Pattern.compile(MOBILENO_PATTERN);
    }

    public boolean validateemail(final String hex) {

        matcher = emailpattern.matcher(hex);
        return matcher.matches();

    }

    public boolean validatemobileno(final String hex){
        matcher = mobilepattern.matcher(hex);
        return matcher.matches();
    }
}
