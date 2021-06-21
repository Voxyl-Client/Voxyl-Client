package bwp;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;

public class DiscordRP {

	private boolean running = true;
	private long created = 0;

	public void start() {
		if (!Minecraft.isRunningOnMac) {
			this.created = System.currentTimeMillis();

			DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {

				@Override
				public void apply(DiscordUser user) {
					System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
					update("Booting up...", "", "large");
				}
			}).build();

			DiscordRPC.discordInitialize("832921057698512918", handlers, true);

			new Thread("Discord RPC Callback") {

				@Override
				public void run() {
					while (running) {
						DiscordRPC.discordRunCallbacks();
					}
				}

			}.start();
		}
	}

	public void shutdown() {
		if (!Minecraft.isRunningOnMac) {
			running = false;
			DiscordRPC.discordShutdown();
		}
	}
	public void update(String firstLine, String secondLine, String image) {
		if (!Minecraft.isRunningOnMac) {
			DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
			b.setBigImage("large", "BWP Client");
			b.setSmallImage(image, "Made by ambmt");
			b.setDetails(firstLine);
			b.setStartTimestamps(created);

			DiscordRPC.discordUpdatePresence(b.build());
		}
	}
}


