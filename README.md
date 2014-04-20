MinecraftServerPing
===================

MinecraftServerPing is a quick and easy Java API for grabbing Minecraft server information. It allows you to access information sent to the client to display on the server list. Currently, information available is:

 * MOTD and favicon
 * Server and protocol versions
 * Player counts and samples

*MinecraftServerPing requires [Gson](https://code.google.com/p/google-gson/) by Google to function.*

Please leave the copyright notice intact in source if you copy source into your code rather than build path.

If you require compatibility with the previous packets, use the [alpha tag](https://github.com/jamietech/MinecraftServerPing/releases/tag/alpha).

The latest version of MinecraftServerPing is based heavily upon the work of [zh32](https://gist.github.com/zh32/7190955).

###Configuration
Configuration is provided through the `MinecraftPingOptions` class. This class provides the following configuration options:

 * `String` hostname *(hostname of the server to query, **required**)*
 * `int` port *(port of the server to query, **optional** default `25565`)*
 * `int` timeout *(socket timeout in ms, **optional** default `2000`)*
 * `String` charset *(charset for MOTD byte->string, **optional** default `UTF-8`)*

###Example

MinecraftServerPing is very easy to use and implement into any project. Simply clone the repo, compile the code and add it to your build path. Coding with it is as simple as this too:

```java
MinecraftPingReply data = new MinecraftPing().getPing(new MinecraftPingOptions().setHostname("example.com").setPort(25565));
System.out.println(data.getDescription() + "  --  " + data.getPlayers().getOnline() + "/" + data.getPlayers().getMax());
```