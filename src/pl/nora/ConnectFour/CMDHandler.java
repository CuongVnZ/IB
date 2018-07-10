package pl.nora.ConnectFour;

import pl.nora.ConnectFour.ConnectFour;
import pl.nora.ConnectFour.Data;
import pl.nora.ConnectFour.Invites.InviteMenu;
import pl.nora.ConnectFour.Invites.InvitesHandler;
import pl.nora.ConnectFour.Invites.Queues.Queue;
import pl.nora.ConnectFour.Language.Language;
import pl.nora.ConnectFour.Utilities.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

@SuppressWarnings("unused")
public class CMDHandler
implements CommandExecutor {

    public CMDHandler(ConnectFour connectfour)
    {
        pl = connectfour;
    }

    public boolean onCommand(CommandSender commandsender, Command command, String s, String as[])
    {
        if(commandsender instanceof Player)
        {
            Player player = (Player)commandsender;
            if(command.getName().equalsIgnoreCase("connectfour"))
                if(as.length == 2)
                {
                    if(as[0].equals("invite"))
                    {
                        Player player1 = Bukkit.getPlayer(as[1]);
                        if(!player.canSee(player1))
                            return false;
                        pl.getInvHandler().invite(player, player1);
                    } else
                    if(as[0].equals("accept"))
                    {
                        Player player2 = Bukkit.getPlayer(as[1]);
                        if(!player.canSee(player2))
                            return false;
                        pl.getInvHandler().accept(player, player2);
                    } else
                    {
                        pl.getUtil().helpMenu(player);
                    }
                } else
                if(as.length == 1)
                {
                    if(as[0].equals("queue") && pl.getUtil().queueEnabled.booleanValue())
                    {
                        if(pl.getQueue().isInQueue(player))
                        {
                            pl.getQueue().remove(player);
                            player.sendMessage(pl.getLang().getRP("Messages.Invites.Queue.Quit", player.getName()));
                        } else
                        {
                            pl.getQueue().addPlayer(player);
                            player.sendMessage(pl.getLang().getRP("Messages.Invites.Queue.Join", player.getName()).replace("%SIZE%", (new StringBuilder(String.valueOf(pl.getQueue().getSize()))).toString()));
                        }
                    } else
                    if(as[0].equals("invite") && pl.getUtil().inviteMenu.booleanValue())
                        player.openInventory(InviteMenu.getInviteMenu(player));
                    else
                    if(as[0].equals("stats"))
                    {
                        if(!pl.getUtil().stats.booleanValue())
                            return false;
                        player.sendMessage(pl.getLang().get("Messages.Stats").replace("%WINS%", (new StringBuilder()).append(pl.getData().getWins(player)).toString()).replace("%LOSSES%", (new StringBuilder()).append(pl.getData().getLoss(player)).toString()));
                    } else
                    {
                        pl.getUtil().helpMenu(player);
                    }
                } else
                {
                    pl.getUtil().helpMenu(player);
                }
        }
        return false;
    }

    ConnectFour pl;
}

