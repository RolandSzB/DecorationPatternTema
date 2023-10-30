import java.io.*;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class Main {
    public static void main(String[] args) {
        // Create a FileDataSource
        DataSource fileDataSource = new FileDataSource();

        // Create an EncryptionDecorator
        DataSource encryptedFileDataSource = new EncryptionDecorator(fileDataSource);

        // Create a CompressionDecorator
        DataSource encryptedCompressedFileDataSource = new CompressionDecorator(encryptedFileDataSource);

        // Write and read data using the decorators
        encryptedCompressedFileDataSource.write("encrypted_compressed.txt", "This is a test message.");
        String content = encryptedCompressedFileDataSource.read("encrypted_compressed.txt");

        System.out.println("Decrypted and decompressed content: " + content);
    }
}





