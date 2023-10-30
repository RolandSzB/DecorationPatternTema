import java.io.*;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

class EncryptionDecorator implements DataSource {
    private DataSource source;

    public EncryptionDecorator(DataSource source) {
        this.source = source;
    }

    @Override
    public String read(String filePath) {
        // Decrypt the content after reading
        String encryptedContent = source.read(filePath);
        String decryptedContent = decrypt(encryptedContent);
        return decryptedContent;
    }

    @Override
    public void write(String filePath, String content) {
        // Encrypt the content before writing
        String encryptedContent = encrypt(content);
        source.write(filePath, encryptedContent);
    }


    private String encrypt(String content) {
        byte[] encryptedBytes = Base64.getEncoder().encode(content.getBytes());
        return new String(encryptedBytes);
    }

    private String decrypt(String encryptedContent) {
        byte[] decryptedBytes = Base64.getDecoder().decode(encryptedContent);
        return new String(decryptedBytes);
    }
}