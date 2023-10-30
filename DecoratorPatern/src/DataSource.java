import java.io.*;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;


interface DataSource {
    String read(String filePath);
    void write(String filePath, String content);
}