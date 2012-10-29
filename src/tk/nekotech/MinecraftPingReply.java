package tk.nekotech;

public class MinecraftPingReply {
    private final String ip;
    private final int port;
    private final String motd;
    private final String protocolVersion;
    private final String version;
    private final int maxPlayers;
    private final int onlinePlayers;

    protected MinecraftPingReply(final String ip, final int port, final String motd, final int maxPlayers, final int onlinePlayers) {
        this(ip, port, motd, "Pre-47", "Pre-12w42b", maxPlayers, onlinePlayers);
    }

    protected MinecraftPingReply(final String ip, final int port, final String motd, final String protocolVersion, final String version, final int maxPlayers, final int onlinePlayers) {
        this.ip = ip;
        this.port = port;
        this.motd = motd;
        this.protocolVersion = protocolVersion;
        this.version = version;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public String getIp() {
        return this.ip;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public String getMotd() {
        return this.motd;
    }

    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }

    public int getPort() {
        return this.port;
    }

    public String getProtocolVersion() {
        return this.protocolVersion;
    }

    public String getVersion() {
        return this.version;
    }

    @Override
    public String toString() {
        return String.format("{\"ip\":\"%s\",\"port\":%s,\"maxPlayers\":%s,\"onlinePlayers\":%s,\"motd\":\"%s\",\"protocolVersion\":\"%s\",\"serverVersion\":\"%s\"}", this.getIp(), this.getPort(), this.getMaxPlayers(), this.getOnlinePlayers(), this.getMotd(), this.getProtocolVersion(), this.getVersion());
    }
}
