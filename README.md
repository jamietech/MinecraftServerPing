MinecraftServerPing
===================

MinecraftServerPing is a Java API for interacting with the Minecraft server's MOTD packets. It allows you to quickly and easily access information easily available. Currently sent by the server is:

 - MOTD
 - Server version
 - Online Players
 - Maximum Players
 
Please note that MinecraftServerPing is copyright 2013 jamietech and all copyright notices must remain intact in source.

###Usage Example

MinecraftServerPing is very easy to use and implement into any project. Simply clone the repo, compile the code and add it to your build path. Coding with it is as simple as this too:

```java
System.out.println("mc2 has " + (new MinecraftServerPing()).getPing("mc2.joe.to").getOnlinePlayers() + " online users.");
System.out.println("mc3's motd is " + (new MinecraftServerPing()).getPing("mc3.joe.to").getMotd();
```
