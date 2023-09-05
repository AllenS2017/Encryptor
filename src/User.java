import java.security.PrivateKey;
import java.security.PublicKey;

public class User {

    private String username;
    private String password;
    private PrivateKey[] privateKey;
    private PublicKey[] publicKey;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PrivateKey[] getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = new PrivateKey[]{privateKey};
    }

    public PublicKey[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = new PublicKey[]{publicKey};
    }
}
