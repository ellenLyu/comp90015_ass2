package app;

public class UserInfo {

    private String username;

    private String rmiHost;

    public UserInfo(String username, String rmiHost) {
        this.username = username;
        this.rmiHost = rmiHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRmiHost() {
        return rmiHost;
    }

    public void setRmiHost(String rmiHost) {
        this.rmiHost = rmiHost;
    }
}
