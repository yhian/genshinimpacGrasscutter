package emu.grasscutter.server.packet.send;

import emu.grasscutter.Config.ServerOptions;
import emu.grasscutter.GenshinConstants;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.GenshinPlayer;
import emu.grasscutter.net.packet.GenshinPacket;
import emu.grasscutter.net.packet.PacketOpcodes;
import emu.grasscutter.net.proto.ChatInfoOuterClass.ChatInfo;
import emu.grasscutter.net.proto.PullRecentChatRspOuterClass.PullRecentChatRsp;
import emu.grasscutter.utils.Utils;

public class PacketPullRecentChatRsp extends GenshinPacket {
	public PacketPullRecentChatRsp(GenshinPlayer player) {
		super(PacketOpcodes.PullRecentChatRsp);
		
		ServerOptions serverOptions = Grasscutter.getConfig().getServerOptions();
		PullRecentChatRsp.Builder proto = PullRecentChatRsp.newBuilder();
		
		if (serverOptions.WelcomeEmotes != null && serverOptions.WelcomeEmotes.length > 0) {
			ChatInfo welcomeEmote = ChatInfo.newBuilder()
				.setTime((int) (System.currentTimeMillis() / 1000))
				.setUid(GenshinConstants.SERVER_CONSOLE_UID)
				.setToUid(player.getId())
				.setIcon(serverOptions.WelcomeEmotes[Utils.randomRange(0, serverOptions.WelcomeEmotes.length - 1)])
				.build();
			
			proto.addChatInfo(welcomeEmote);
		}
		
		if (serverOptions.WelcomeMotd != null && serverOptions.WelcomeMotd.length() > 0) {
			ChatInfo welcomeMotd = ChatInfo.newBuilder()
				.setTime((int) (System.currentTimeMillis() / 1000))
				.setUid(GenshinConstants.SERVER_CONSOLE_UID)
				.setToUid(player.getId())
				.setText(Grasscutter.getConfig().getServerOptions().WelcomeMotd)
				.build();
			
			proto.addChatInfo(welcomeMotd);
		}

		this.setData(proto);
	}
}
