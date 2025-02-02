package online.muydinov.userservice.utils;

public class EmailUtils {
    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name + ",\n\nYour new account has been created. Please click the link below to verify your account. " +
                "\n\n" + getVerificationUrL(host, token) + "\n\nThe support team";
    }

    public static String getVerificationUrL(String host, String token) {
        return host + "/api/users?token=" + token;
    }

}
