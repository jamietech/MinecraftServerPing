package tk.nekotech;

public class MinecraftPingReply {
    private String ip;
    private int port;
    private String motd;
    private String version;
    private int maxPlayers;
    private int onlinePlayers;

    protected MinecraftPingReply(String ip, int port, String motd, int maxPlayers, int onlinePlayers) {
        this(ip, port, motd, "Pre-12w42b", maxPlayers, onlinePlayers);
    }

    protected MinecraftPingReply(String ip, int port, String motd, String version, int maxPlayers, int onlinePlayers) {
        this.ip = ip;
        this.port = port;
        this.motd = motd;
        this.version = version;
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    public String getMotd() {
        return this.motd;
    }

    public String getVersion() {
        return this.version;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getOnlinePlayers() {
        return this.onlinePlayers;
    }
}
