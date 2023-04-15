package org.landg.lgrs.imagechannel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class ImageChannelTestHelper {
    InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    public static byte[] readFileIntoByteArray(String fileName) {
        InputStream rvDataInputStream = new ImageChannelTestHelper().getFileFromResourceAsStream(fileName);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        try {
            while ((nRead = rvDataInputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Unable to read the file");
        }
        return buffer.toByteArray();
    }
}
