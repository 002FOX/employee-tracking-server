package fox.server.utils;

public class TokenLoginResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public TokenLoginResponse(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}