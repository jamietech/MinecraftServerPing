package tk.nekotech;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MinecraftPing {
    public MinecraftPingReply getPing(String hostname, int port) throws IOException {
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        socket = new Socket();
        socket.connect(new InetSocketAddress(hostname, port), 3000);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        out.write(254);
        out.writeByte(1);
        if (in.read() != 255)
            throw new IOException("Bad message - An incorrect packet was received.");
        short bit = in.readShort();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bit; ++i)
            sb.append(in.readChar());
        String[] bits = sb.toString().split("\0");
        if (bits.length != 6) {
            return this.getOldPing(hostname, port);
        }
        return new MinecraftPingReply(hostname, port, bits[3], bits[2], Integer.valueOf(bits[4]), Integer.valueOf(bits[5]));
    }

    public MinecraftPingReply getOldPing(String hostname, int port) throws IOException {
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        socket = new Socket();
        socket.connect(new InetSocketAddress(hostname, port), 3000);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        out.write(254);
        if (in.read() != 255)
            throw new IOException("Bad message - An incorrect packet was received.");
        short bit = in.readShort();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bit; ++i)
            sb.append(in.readChar());
        String[] bits = sb.toString().split("\u00a7");
        if (bits.length != 3)
            throw new IOException("Bad message - The OLD ping was not specified properly.");
        return new MinecraftPingReply(hostname, port, bits[0], Integer.valueOf(bits[2]), Integer.valueOf(bits[1]));
    }
}