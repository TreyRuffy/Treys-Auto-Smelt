package me.treyruffy.treysautosmelt;

import java.io.IOException;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class TreysAutoSmelt extends JavaPlugin implements Listener{

	public void onEnable(){
		getServer().getPluginManager().registerEvents(this, this);
	    getConfig().options().copyDefaults(true);
	    saveConfig();
	    try {
	        Metrics metrics = new Metrics(this);
	        metrics.start();
	    } catch (IOException e){}
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = ChatColor.BLACK + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "Trey's Auto Smelt" + ChatColor.BLACK + "] ";
		if (label.equalsIgnoreCase("TreysAutoSmelt")){
			if (args.length == 0 || args.length >= 3){
				sender.sendMessage(ChatColor.AQUA + "-=[ " + ChatColor.GOLD + "Trey's Auto Smelt Help " + ChatColor.AQUA + "]=-");
				sender.sendMessage(ChatColor.GREEN + "Add a '" + ChatColor.YELLOW + "?" + ChatColor.GREEN + "' after a command to see more about it.");
				sender.sendMessage(ChatColor.DARK_AQUA + "Trey's Auto Smelt was made by TreyRuffy");
				if (sender.hasPermission("smelt.reload") || sender instanceof ConsoleCommandSender){
					sender.sendMessage(ChatColor.GREEN + "/treysautosmelt reload");
					return true;
				} else {
					return true;
				}
			} else if (args.length == 1){
				if (args[0].equalsIgnoreCase("reload")){
					if (sender.hasPermission("smelt.reload") || sender instanceof ConsoleCommandSender){				
					sender.sendMessage(prefix + ChatColor.GREEN + "Reloading config...");
					reloadConfig();
					sender.sendMessage(prefix + ChatColor.GREEN + "Reloaded config!");
					return true;
					} else {
						return false;
					}
				} else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")){
					sender.sendMessage(ChatColor.AQUA + "-=[ " + ChatColor.GOLD + "Trey's Auto Smelt Help " + ChatColor.AQUA + "]=-");
					sender.sendMessage(ChatColor.GREEN + "Add a '" + ChatColor.YELLOW + "?" + ChatColor.GREEN + "' after a command to see more about it.");
					sender.sendMessage(ChatColor.DARK_AQUA + "Trey's Auto Smelt was made by TreyRuffy");
					if (sender.hasPermission("smelt.reload") || sender instanceof ConsoleCommandSender){
						sender.sendMessage(ChatColor.GREEN + "/treysautosmelt reload");
						return true;
					} else {
						return true;
					}
				} else {
					sender.sendMessage(ChatColor.AQUA + "-=[ " + ChatColor.GOLD + "Trey's Auto Smelt Help " + ChatColor.AQUA + "]=-");
					sender.sendMessage(ChatColor.GREEN + "Add a '" + ChatColor.YELLOW + "?" + ChatColor.GREEN + "' after a command to see more about it.");
					sender.sendMessage(ChatColor.DARK_AQUA + "Trey's Auto Smelt was made by TreyRuffy");
					if (sender.hasPermission("smelt.reload")){
						sender.sendMessage(ChatColor.GREEN + "/treysautosmelt reload");
						return true;
					} else {
						return true;
					}
				}
			} else if (args.length == 2){
				if (args[0].equalsIgnoreCase("reload") && args[1].equalsIgnoreCase("?")){
					if (sender.hasPermission("smelt.reload") || sender instanceof ConsoleCommandSender){
						sender.sendMessage(ChatColor.AQUA + "-=[ " + ChatColor.GOLD + "Reload" + ChatColor.AQUA + " ]=-");
						sender.sendMessage(ChatColor.YELLOW + "Lets you reload the config for Trey's Auto Smelt.");
						sender.sendMessage(ChatColor.GREEN + "/treysautosmelt reload");
						return true;
					} else {
						sender.sendMessage(prefix + ChatColor.RED + "You do not have permission to type that command.");
						return false;
					}
				} else if ((args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) && args[1].equalsIgnoreCase("?")){
						 sender.sendMessage(ChatColor.AQUA + "-=[ " + ChatColor.GOLD + "Help" + ChatColor.AQUA + " ]=-");
						 sender.sendMessage(ChatColor.YELLOW + "Lets you see the Trey's Auto Smelt help menu.");
						 sender.sendMessage(ChatColor.GREEN + "/treysautosmelt help");
						 return true;
				} else {
					sender.sendMessage(ChatColor.AQUA + "-=[ " + ChatColor.GOLD + "Trey's Auto Smelt Help " + ChatColor.AQUA + "]=-");
					sender.sendMessage(ChatColor.GREEN + "Add a '" + ChatColor.YELLOW + "?" + ChatColor.GREEN + "' after a command to see more about it.");
					sender.sendMessage(ChatColor.DARK_AQUA + "Trey's Auto Smelt was made by TreyRuffy");
					if (sender.hasPermission("smelt.reload")){
						sender.sendMessage(ChatColor.GREEN + "/treysautosmelt reload");
						return true;
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Player player = (Player) e.getPlayer();
		Block block = (Block) e.getBlock();
		Random rand = new Random();
		int lapis = rand.nextInt(500);
		int redstone = rand.nextInt(200);
		
		if (block.getType() == Material.IRON_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.iron") && getConfig().getBoolean("Enabled.Iron", true)){
			if (player.getItemInHand().getType().equals(Material.STONE_PICKAXE) || player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Iron"))); 
				block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.IRON_INGOT));
			}
		} else if (block.getType() == Material.GOLD_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.gold") && getConfig().getBoolean("Enabled.Gold", true)){
			if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Gold")));
				block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GOLD_INGOT));
			}
		} else if (block.getType() == Material.COAL_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.coal") && getConfig().getBoolean("Enabled.Coal", true)){
			if (player.getItemInHand().getType().equals(Material.WOOD_PICKAXE) || player.getItemInHand().getType().equals(Material.GOLD_PICKAXE) || player.getItemInHand().getType().equals(Material.STONE_PICKAXE) || player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Coal")));
				block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COAL));
			}
		} else if (block.getType() == Material.QUARTZ_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.quartz") && getConfig().getBoolean("Enabled.Quartz", true)){
			if (player.getItemInHand().getType().equals(Material.STONE_PICKAXE) || player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Quartz")));
				block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.QUARTZ));
			}
		} else if (block.getType() == Material.LAPIS_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.lapis") && getConfig().getBoolean("Enabled.Lapis", true)){
			if (player.getItemInHand().getType().equals(Material.STONE_PICKAXE) || player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Lapis")));
				if (lapis <= 130){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.INK_SACK, 4, (short) 4));
				} else if (lapis <= 220 && lapis >= 131){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.INK_SACK, 5, (short) 4));
				} else if (lapis <= 345 && lapis >= 221){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.INK_SACK, 6, (short) 4));
				} else if (lapis <= 435 && lapis >= 346){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.INK_SACK, 7, (short) 4));
				} else if (lapis <= 500 && lapis >= 436){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.INK_SACK, 8, (short) 4));
				}
			}
		} else if (block.getType() == Material.REDSTONE_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.redstone") && getConfig().getBoolean("Enabled.Redstone", true)){
			if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Redstone")));
				if (redstone <= 110){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.REDSTONE, 4));
				} else if (redstone <= 200 && redstone >= 111){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.REDSTONE, 5));
				}
			}
		} else if (block.getType() == Material.GLOWING_REDSTONE_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.redstone") && getConfig().getBoolean("Enabled.Redstone", true)){
			if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Redstone")));
				if (redstone <= 110){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.REDSTONE, 4));
				} else if (redstone <= 200 && redstone >= 111){
					block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.REDSTONE, 5));
				}
			}
		} else if (block.getType() == Material.DIAMOND_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.diamond") && getConfig().getBoolean("Enabled.Diamond", true)){
			if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Diamond")));
				block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.DIAMOND));
			}
		} else if (block.getType() == Material.EMERALD_ORE && player.getGameMode() != GameMode.CREATIVE && player.hasPermission("smelt.emerald") && getConfig().getBoolean("Enabled.Emerald", true)){
			if (player.getItemInHand().getType().equals(Material.IRON_PICKAXE) || player.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)){
				e.setCancelled(true);
				block.setType(Material.AIR);
				player.giveExp((int) (getConfig().getInt("XP.Emerald")));
				block.getWorld().dropItem(player.getLocation(), new ItemStack(Material.EMERALD));
			}
		}
	}
		
	@EventHandler
	public void EntityDeathEvent(EntityDeathEvent e){
		Random rand = new Random();
		Entity entity = e.getEntity();
		Player player = e.getEntity().getKiller();
		
		if((entity instanceof Pig) && player.hasPermission("smelt.pork") && getConfig().getBoolean("Enabled.Pork", true)){
			e.getDrops().clear();
			e.setDroppedExp(0);
			player.giveExp((int) (getConfig().getInt("XP.Pork")));
			int pork = rand.nextInt(300);
			if (pork <= 85){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GRILLED_PORK));
			} else if (pork >= 86 && pork <= 240){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GRILLED_PORK, 2));
			} else if (pork >= 241 && pork <= 300){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.GRILLED_PORK, 3));
			}
		} else if ((entity instanceof Cow) && player.hasPermission("smelt.beef") && getConfig().getBoolean("Enabled.Beef", true)){
			e.getDrops().clear();
			e.setDroppedExp(0);
			player.giveExp((int) (getConfig().getInt("XP.Beef")));
			int beef = rand.nextInt(300);
			int leather = rand.nextInt(300);
			if (beef <= 87){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_BEEF));				
			} else if (beef >= 88 && beef <= 245){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_BEEF, 2));
			} else if (beef >= 246 && beef <= 300){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_BEEF, 3));
			}
			if (leather <= 130){
			} else if (leather >= 131 && leather <= 240){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.LEATHER));
			} else if (leather >= 241 && leather <= 300){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.LEATHER, 2));
			}
		} else if ((entity instanceof Chicken) && player.hasPermission("smelt.chicken") && getConfig().getBoolean("Enabled.Chicken", true)){
			e.getDrops().clear();
			e.setDroppedExp(0);
			player.giveExp((int) (getConfig().getInt("XP.Chicken")));
			entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_CHICKEN));
			int feather = rand.nextInt(200);
			if (feather <= 80){
			} else if (feather >=81 && feather <= 140){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.FEATHER));
			} else if (feather >= 141 && feather <= 200){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.FEATHER, 2));
			}
		} else if ((entity instanceof Sheep) && player.hasPermission("smelt.mutton") && getConfig().getBoolean("Enabled.Mutton", true)){
			e.getDrops().clear();
			e.setDroppedExp(0);
			entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.WOOL));
			player.giveExp((int) (getConfig().getInt("XP.Mutton")));
			int mutton = rand.nextInt(200);
			if (mutton <= 110){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_MUTTON));
			} else if (mutton >= 111 && mutton <= 200){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_MUTTON, 2));
			}
		} else if ((entity instanceof Rabbit) && player.hasPermission("smelt.rabbit") && getConfig().getBoolean("Enabled.Rabbit", true)){
			e.getDrops().clear();
			e.setDroppedExp(0);
			player.giveExp((int) (getConfig().getInt("XP.Rabbit")));
			int rabbit = rand.nextInt(200);
			int hide = rand.nextInt(200);
			int foot = rand.nextInt(200);
			if (rabbit <= 105){
			} else if (rabbit >= 106 && rabbit <= 200){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.COOKED_RABBIT));
			}
			if (hide <= 123){
			} else if (hide >= 124 && hide <= 200){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.RABBIT_HIDE));
			}
			if (foot <= 187){
			} else if (foot >= 188 && foot <= 200){
				entity.getWorld().dropItem(player.getLocation(), new ItemStack(Material.RABBIT_FOOT));
			}
		}
	}
}
