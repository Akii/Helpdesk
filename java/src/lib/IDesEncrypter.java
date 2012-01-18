package lib;
/******************
 * Imports
 ******************/
import javax.crypto.IllegalBlockSizeException;

/******************
 * DesEncrypter
 ******************/
public interface IDesEncrypter {
    public String encrypt(String str) throws IllegalBlockSizeException;
    public String decrypt(String str);
}
