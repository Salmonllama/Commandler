package studio.spiderling.Commandler;

import org.javacord.api.event.server.ServerJoinEvent;
import org.javacord.api.listener.server.ServerJoinListener;

public class FrameworkServerInit implements ServerJoinListener {

    public void onServerJoin(ServerJoinEvent event) {
        FrameworkDB.serverFirstTimeSetup(FrameworkConfig.getDefaultPrefix(), event.getServer().getIdAsString());
    }
}
