package ch.jamiete.mcping;

public class Test {

    public static void main(String[] args) throws Exception {
        new Test().run();
    }

    public void run() throws Exception {

        MinecraftPingReply data = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname("us.oc.tc").setPort(25565));
        System.out.println(data.getDescription() + "  --  " + data.getPlayers().getOnline() + "/" + data.getPlayers().getMax());

    }

}
