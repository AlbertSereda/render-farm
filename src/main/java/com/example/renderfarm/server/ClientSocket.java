package com.example.renderfarm.server;

import com.example.renderfarm.server.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientSocket {
    private static final Logger log = LoggerFactory.getLogger(ClientSocket.class);

    private boolean isAuthorized = false;

    private Client client;

    private final ByteBuffer bufferRead;
    private final ByteBuffer bufferWrite;

    private static final int READ_BUFFER_CAPACITY = 1024;
    private static final int WRITE_BUFFER_CAPACITY = 1024;

    private final Selector selector;

    private final SocketChannel socketChannel;

    public ClientSocket(Selector selector, SocketChannel socketChannel) {
        this.selector = selector;
        this.socketChannel = socketChannel;
        bufferRead = ByteBuffer.allocate(READ_BUFFER_CAPACITY);
        bufferWrite = ByteBuffer.allocate(WRITE_BUFFER_CAPACITY);
    }

    public String readData() throws IOException {

        int bytesRead = socketChannel.read(bufferRead);
        String clientMessage = "";

        if (bytesRead == -1) {
            log.info("Connection close " + socketChannel);
            throw new IOException();
        } else if (bytesRead > 0 && bufferRead.get(bufferRead.position() - 1) == '\n') {
            bufferRead.flip();
            clientMessage = new String(bufferRead.array(), bufferRead.position(), bufferRead.limit());
            bufferRead.clear();
        }
        return clientMessage;
    }

    public void writeData() throws IOException {

        socketChannel.write(bufferWrite);

        if (!bufferWrite.hasRemaining()) {
            bufferWrite.compact();
            socketChannel.register(selector, SelectionKey.OP_READ);
            bufferWrite.clear();
        }
    }

    public void writeToBuffer(byte[] bytesToWrite) {

        bufferWrite.put(ByteBuffer.wrap(bytesToWrite));
        if (bytesToWrite.length != 0) {
            if ((bytesToWrite[bytesToWrite.length - 1]) != '\n') {
                bufferWrite.put((byte) '\n');
            }
        }

        bufferWrite.flip();

        try {
            socketChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            log.info("Connection is close", e);
        }
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
