package com.techgenii.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {

        if (args.length != 1) {
            System.err.println(
                    "Usage:  " + EchoServer.class.getSimpleName() +
                            " <port>");

        }
        int port = Integer.parseInt(args[0]);

        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {

        final EchoServerHandler serverHandler = new EchoServerHandler();
        final NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            final ServerBootstrap b = new ServerBootstrap();

            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(this.port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });

            final ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            System.out.println("Closing Gracefully");
            group.shutdownGracefully().sync();
        }


    }
}
