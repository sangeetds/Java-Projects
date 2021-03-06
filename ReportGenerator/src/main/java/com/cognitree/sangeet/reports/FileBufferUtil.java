package com.cognitree.sangeet.reports;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileBufferUtil {
    static public FileChannel file;

    static public ByteBuffer getByteBuffer(String fileName, int size) {
        ByteBuffer byteBuffer = null;

        try {
            file = new RandomAccessFile(fileName, "rw").getChannel();
            byteBuffer = file.map(FileChannel.MapMode.READ_WRITE, 0, size);
        } catch (IOException e) {
            System.out.println("File" + fileName + "not writable or corrupt.");
        }

        return byteBuffer;
    }

    static void closeFile() {
        try {
            file.close();
        } catch (IOException e) {
            System.out.println("Problems with the file channel");
        }
    }
}
