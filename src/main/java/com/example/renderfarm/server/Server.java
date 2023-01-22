package com.example.renderfarm.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Server implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private boolean isActive;

    private static final int PORT = 8080;

    private final Map<SocketChannel, ClientSocket> socketChannelMap;

    private final ServerLogicExecutor serverLogicExecutor;

    public Server(@Autowired ServerLogicExecutor serverLogicExecutor) {
        isActive = true;
        socketChannelMap = new ConcurrentHashMap<>();
        this.serverLogicExecutor = serverLogicExecutor;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("Render farm server is Started");

            while (isActive) {
                int readyChannels = selector.selectNow();
                if (readyChannels == 0) continue;

                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isValid()) {
                        try {
                            if (key.isAcceptable()) {
                                acceptChannel(key);
                            }
                            ClientSocket clientSocket;
                            if (key.isReadable()) {
                                clientSocket = socketChannelMap.get((SocketChannel) key.channel());
                                String clientMessage = clientSocket.readData();
                                serverLogicExecutor.executeCommand(clientSocket, clientMessage);
                            }

                            if (key.isWritable()) {
                                clientSocket = socketChannelMap.get((SocketChannel) key.channel());
                                clientSocket.writeData();
                            }
                        } catch (CancelledKeyException | IOException e) {
                            disconnectChannel(key);
                        }
                    }
                }
                selector.selectedKeys().clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptChannel(SelectionKey key) {
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);

            ClientSocket clientSocket = new ClientSocket(key.selector(), socketChannel);
            socketChannelMap.put(socketChannel, clientSocket);

            serverLogicExecutor.notifyNewClient(clientSocket);
        } catch (IOException e) {
            log.info("Can not connect client");
        }
    }

    private void disconnectChannel(SelectionKey key) {
        try {
            key.channel().close();
        } catch (IOException e) {
            log.info("Close channel Exception", e);
        } finally {
            socketChannelMap.remove((SocketChannel) key.channel());
        }

    }
}
