import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class Cipher_KeyStore {
  public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, KeyStoreException, IOException, CertificateException, UnrecoverableEntryException {
    // creates object for symmetric encryption/decryption
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

    // creates random key for AES algorithm
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    SecretKey key = keyGen.generateKey();

    cipher.init(Cipher.ENCRYPT_MODE, key /*, new IvParameterSpec(random byte[]) */);
    byte[] ten = new byte[]{1,2,3,4,5,6,7,8,9,10};
    // encrypt
    byte[] crypto = cipher.doFinal(ten);
    System.out.println(Arrays.toString(crypto));
    System.out.println("IV = " + Arrays.toString(cipher.getIV()));

    // decrypt
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(cipher.getIV()));
    byte[] original = cipher.doFinal(crypto);
    System.out.println(Arrays.toString(original));

    // store key in keystore
    KeyStore store = KeyStore.getInstance("PKCS12");
    // init in memory store
    store.load(null);
    // create secret key entry
    KeyStore.Entry entry = new KeyStore.SecretKeyEntry(key);
    // TODO: ask password to the user
    store.setEntry("alias-26out", entry, new KeyStore.PasswordProtection("ausersecret".toCharArray()));
    // save keystore in file
    FileOutputStream storeFile = new FileOutputStream("secret.pkcs12");
    // empty integrity password
    store.store(storeFile, new char[]{});
    storeFile.close();

  }
}