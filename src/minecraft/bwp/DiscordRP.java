package bwp;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {

	private boolean running = true;
	private long created = 0;

	private boolean isMac = System.getProperty("os.name").equals("Mac OS X");

	public void start() {
		if (!isMac) {
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
		if (!isMac) {
			running = false;
			DiscordRPC.discordShutdown();
		}
	}
	public void update(String firstLine, String secondLine, String image) {
		if (!isMac) {
			DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
			b.setBigImage("large", "Voxyl Client");
			b.setSmallImage(image, "Made with <3");
			b.setDetails(firstLine);
			b.setStartTimestamps(created);

			DiscordRPC.discordUpdatePresence(b.build());
		}
	}
}


