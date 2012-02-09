package Helpdesk.java.helpdesk.lib;
/******************
 * Imports
 ******************/
import Helpdesk.java.helpdesk.mvc.View.Error_Frame;
import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncrypter implements IDesEncrypter {
    Cipher ecipher;
    Cipher dcipher;
    private static String secretSalt = "mySecretSalt123456789";

    DesEncrypter(SecretKey key) {
        try {
            
            // Create cipher and initialize it for encryption.
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);

        } catch (javax.crypto.NoSuchPaddingException e) {
        } catch (java.security.NoSuchAlgorithmException e) {
        } catch (java.security.InvalidKeyException e) {
        }
    }
    
    
/*********************
 *  encrypt SQL password
 **********************/
   public static void SQLencrypted (String host, String port, String db, String user, char [] pw) {
                try {
                
                 String newPW = String.valueOf(pw);
                 // See also Encrypting with DES Using a Pass Phrase.
                 SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                 DESKeySpec keySpec = new DESKeySpec(secretSalt.getBytes());
                 SecretKey key = keyFactory.generateSecret(keySpec);
                 // Create encrypter/decrypter class
                 DesEncrypter encrypter = new DesEncrypter(key);
                 // Encrypt
                 String encrypted = encrypter.encrypt(newPW);
                 LoginData.writeSQL (host,port,db,user,encrypted);
                
                } catch (Exception e) {
                    Error_Frame.Error(e.toString());
                }
    }
    
  /**********************
 *  decrypt SQL password
 **************************/
   public static String SQLdecrypted (String decrypt) {
                String decrypted = "";
                try {
                // Encryption used DES. We can also use AES
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
                DESKeySpec keySpec = new DESKeySpec(secretSalt.getBytes());
                SecretKey key = keyFactory.generateSecret(keySpec);
                // Create encrypter/decrypter class
                DesEncrypter encrypter = new DesEncrypter(key);
                // Decrypt
                decrypted = encrypter.decrypt(decrypt);
                } catch (Exception e) {
                    Error_Frame.Error(e.toString());
                }
                return decrypted;
    }
    
    
    @Override
    public String encrypt(String str) throws IllegalBlockSizeException {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }



    @Override
    public String decrypt(String str) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }
}