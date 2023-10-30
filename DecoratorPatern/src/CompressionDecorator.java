import java.io.*;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

class CompressionDecorator implements DataSource {
    private DataSource source;

    public CompressionDecorator(DataSource source) {
        this.source = source;
    }

    @Override
    public String read(String filePath) {
        // Decompress the content after reading
        String compressedContent = source.read(filePath);
        String decompressedContent = decompress(compressedContent);
        return decompressedContent;
    }

    @Override
    public void write(String filePath, String content) {
        // Compress the content before writing
        String compressedContent = compress(content);
        source.write(filePath, compressedContent);
    }

    // Compression method
    private String compress(String content) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(content.length());
            DeflaterOutputStream dos = new DeflaterOutputStream(bos);
            dos.write(content.getBytes());
            dos.close();
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decompression method
    private String decompress(String compressedContent) {
        try {
            byte[] compressedBytes = Base64.getDecoder().decode(compressedContent);
            InflaterInputStream iis = new InflaterInputStream(new ByteArrayInputStream(compressedBytes));
            ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedContent.length());
            int data;
            while ((data = iis.read()) != -1) {
                bos.write(data);
            }
            return new String(bos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}