package com.luacraft.library.server;

import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;

import com.luacraft.LuaCraftState;
import com.luacraft.classes.LuaJavaCommand;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.LuaType;

public class LuaLibCommand
{
	private static CommandHandler commandHandler = null;
	
	/**
	 * @author Jake
	 * @library command
	 * @function Add
	 * Adds a chat command
	 * @arguments [[String]]:command, [[Function]]:callback, [ [[String]]:usage ]
	 * @return nil
	 */

	public static JavaFunction Add = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			String commandStr = l.checkString(1);
			l.checkType(2, LuaType.FUNCTION);
			String usageStr = l.checkString(3,"");

			l.newMetatable("CommandCallbacks");
			l.pushString(commandStr);
			l.pushValue(2);
			l.setTable(-3);

			LuaJavaCommand command = new LuaJavaCommand(l, commandStr, usageStr);
			commandHandler.registerCommand(command);
			return 0;
		}
	};
	
	/**
	 * @author Jake
	 * @library command
	 * @function GetAll
	 * Gets a table of all registered commmands
	 * @arguments nil
	 * @return [[Table]]:commands
	 */

	public static JavaFunction GetAll = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.newMetatable("CommandCallbacks");
			return 1;
		}
	};

	public static void Init(final LuaCraftState l)
	{
		MinecraftServer server = l.getMinecraftServer();
		commandHandler = (CommandHandler) server.getCommandManager();

		l.newTable();
		{
			l.pushJavaFunction(Add);
			l.setField(-2, "Add");
			l.pushJavaFunction(GetAll);
			l.setField(-2, "GetAll");
		}
		l.setGlobal("command");
	}
}