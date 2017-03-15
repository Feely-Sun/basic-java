package com.basic.leanring.java;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * @author sunzihan
 * @version $Id: NIO.java V 0.1 3/11/17 13:20 sunzihan EXP $
 */
public class NIO {


    public static void main(String[] args) {
        try {

            final Pipe pipe =  Pipe.open();

            final Pipe.SinkChannel sinkChannel = pipe.sink();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Selector selector =Selector.open();
                        sinkChannel.configureBlocking(false);
                        sinkChannel.register(selector,SelectionKey.OP_WRITE);
                        while (true){
                            int sel = selector.select();
                            if (sel > 0){
                                Pipe.SourceChannel sourceChannel = pipe.source();
                                ByteBuffer buf2 = ByteBuffer.allocate(48);
                                int bytesRead = sourceChannel.read(buf2);
                                System.out.println(new String(buf2.array()).toString());
                            }else {
                                System.out.println("ddsfdsfdsfdsfdsfs");
                                Thread.sleep(2000);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


            while (true){
                String newData = "New String to write to file..." + System.currentTimeMillis();
                ByteBuffer buf = ByteBuffer.allocate(48);
                buf.clear();
                buf.put(newData.getBytes());
                buf.flip();
                while(buf.hasRemaining()) {
                    sinkChannel.write(buf);
                }

                Thread.sleep(10000);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }



}

