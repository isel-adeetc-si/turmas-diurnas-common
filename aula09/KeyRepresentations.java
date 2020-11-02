
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

public class KeyRepresentations {
  public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException {
    // assymetric key pair generator
    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    KeyPair pair = keyGen.generateKeyPair();
    PublicKey pubKey = pair.getPublic();
    PrivateKey privateKey = pair.getPrivate();
    System.out.println(pubKey);
    System.out.println(privateKey);

    // get key specifications
    KeyFactory factory = KeyFactory.getInstance("RSA");
    RSAPrivateKeySpec privSpec = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
    System.out.println(privSpec.getModulus());
    System.out.println(privSpec.getPrivateExponent());

    
  }
}
