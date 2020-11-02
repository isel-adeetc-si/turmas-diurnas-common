import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CipherExample {
  public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
    // creates object for symmetric encryption/decryption
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    // creates random key for AES algorithm
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    SecretKey key = keyGen.generateKey();

    cipher.init(Cipher.ENCRYPT_MODE, key /*, new IvParameterSpec(random byte[]) */);
    byte[] ten = new byte[]{1,2,3,4,5,6,7,8,9,10};
    byte[] crypto = cipher.doFinal(ten);
    System.out.println(Arrays.toString(crypto));
    System.out.println("IV = " + Arrays.toString(cipher.getIV()));

    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
    byte[] original = cipher.doFinal(crypto);
    System.out.println(Arrays.toString(original));

  }
}