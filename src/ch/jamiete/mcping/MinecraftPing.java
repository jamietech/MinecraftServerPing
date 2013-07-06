/*
 * Copyright 2013 jamietech. All rights reserved.
 * https://github.com/jamietech/MinecraftServerPing
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */
package ch.jamiete.mcping;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MinecraftPing {

    /**
     * Fetches a {@link MinecraftPingReply} for the supplied hostname on default port (25565). Will revert to pre-12w42b ping message if required.
     * 
     * @param hostname - the IP of the server to request ping from
     * @param port - the port of the server to request ping from
     * @return {@link MinecraftPingReply} - list of basic server information
     * @throws IOException thrown when failed to receive packet or when incorrect packet is received
     */
    public MinecraftPingReply getPing(final String hostname) throws IOException {
        return this.getPing(hostname, 25565);
    }

    /**
     * Fetches a {@link MinecraftPingReply} for the supplied hostname and port. Will revert to pre-12w42b ping message if required.
     * 
     * @param hostname - the IP of the server to request ping from
     * @param port - the port of the server to request ping from
     * @return {@link MinecraftPingReply} - list of basic server information
     * @throws IOException thrown when failed to receive packet or when incorrect packet is received
     */
    public MinecraftPingReply getPing(final String hostname, final int port) throws IOException {
        this.validate(hostname, "Hostname cannot be null.");
        this.validate(port, "Port cannot be null.");

        final Socket socket = new Socket();
        socket.connect(new InetSocketAddress(hostname, port), 3000);

        final DataInputStream in = new DataInputStream(socket.getInputStream());
        final DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.write(0xFE);
        out.write(0x01);
        out.write(0xFA);
        out.writeShort(11);
        out.writeChars("MC|PingHost");
        out.writeShort(7 + 2 * hostname.length());
        out.writeByte(73); // Protocol version
        out.writeShort(hostname.length());
        out.writeChars(hostname);
        out.writeInt(port);

        out.flush();

        if (in.read() != 255) {
            throw new IOException("Bad message: An incorrect packet was received.");
        }

        final short bit = in.readShort();

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bit; ++i) {
            sb.append(in.readChar());
        }

        out.close();

        final String[] bits = sb.toString().split("\0");
        if (bits.length != 6) {
            return this.getPing(sb.toString(), hostname, port);
        }

        return new MinecraftPingReply(hostname, port, bits[3], bits[1], bits[2], Integer.valueOf(bits[4]), Integer.valueOf(bits[5]));
    }

    /**
     * Returns a {@link MinecraftPingReply} for the response supplied. <b>Only call from {@link MinecraftPing#getPing(String, int)}.</b>
     * 
     * <p>
     * This method isn't intended for use outside of the {@link MinecraftPing} class.
     * 
     * @param response - the pre-12w42b ping reply message
     * @param hostname - the IP of the server ping was requested from
     * @param port - the port of the server ping was requested from
     * @return {@link MinecraftPingReply} - list of basic server information
     * @throws IOException thrown when incorrect message supplied
     */
    @Deprecated
    public MinecraftPingReply getPing(final String response, final String hostname, final int port) throws IOException {
        this.validate(response, "Response cannot be null. Try calling MinecraftPing.getPing().");
        this.validate(hostname, "Hostname cannot be null.");
        this.validate(port, "Port cannot be null.");

        final String[] bits = response.split("\u00a7");

        if (bits.length != 3) {
            throw new IOException("Bad message: Failed to parse pre-12w42b ping message, check to see if it's correct?");
        }

        return new MinecraftPingReply(hostname, port, bits[0], Integer.valueOf(bits[2]), Integer.valueOf(bits[1]));
    }

    private void validate(final Object o, final String m) {
        if (o == null) {
            throw new RuntimeException(m);
        }
    }
}