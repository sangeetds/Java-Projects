package com.cognitree.sangeet.reports;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileBufferUtil {
    static public ByteBuffer getByteBuffer(String fileName, int count) {
        FileChannel file;
        ByteBuffer byteBuffer = null;

        try {
            file = new RandomAccessFile(fileName, "rw").getChannel();
            byteBuffer = file.map(FileChannel.MapMode.READ_WRITE, 0, count * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteBuffer;
    }
}
