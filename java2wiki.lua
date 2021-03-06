--[[
	/**
	* @author LuaStoned
	* @function Player:Kick()
	* Kick the player
	* @arguments String:arg
	* @return nil
	*/
]]

local fileDir = "/run/media/jake/storage/Documents/Developer/Java/LuaCraft-Forge/1.8.9/"
local javaDir = "src/main/java/com/luacraft/"
 
local pattern           = "/%*%*([%w%s%p]-)%*/"

local fmt_header        = "==== %s(%s) ===="
local fmt_header_lib    = "==== %s.%s(%s) ===="
local fmt_alias         = "* '''Alias''' to this function: '''%s'''"
local fmt_return        = "* Return value: '''%s'''"
local fmt_info          = "* '''Information''': %s"
local fmt_derive		= "* '''Inherited''' from [[%s]]"

function fileRead(file)
    local f = assert(io.open(file, "rb"))
    local content = f:read("*all")
    f:close()
    return content
end

function fileWrite(file,data)
    local f = assert(io.open(file, "w"))
    f:write(data)
	f:close()
end

function fileAppend(file,data)
    local f = assert(io.open(file, "a"))
    f:write(data)
	f:close()
end

function grabdoc(fileName)
	local fullPath = fileDir .. javaDir .. fileName .. ".java"
	local str = fileRead(fullPath)
	local tbl = {}

	for doc in str:gmatch(pattern) do
		local author	= doc:match("@author%s(.-)\r?\n")
		local lib		= doc:match("@library%s(.-)\r?\n")
		local func		= doc:match("@function%s(.-)\r?\n")
		local info		= doc:match("@info%s(.-)\r?\n")
		local alias		= doc:match("@alias%s(.-)\r?\n")
		local arg		= doc:match("@arguments%s(.-)\r?\n")
		local ret		= doc:match("@return%s(.-)\r?\n")
		
		arg = (arg == "nil") and "" or arg -- (" " .. arg .. " ")
		arg = arg:gsub(":", " ")
		ret = ret:gsub(":", " ")
		
		local fmt = {}
		if (lib) then
			table.insert(fmt, string.format(fmt_header_lib, lib, func, arg))
		else
			table.insert(fmt, string.format(fmt_header, func, arg))
		end
		if (alias) then table.insert(fmt, string.format(fmt_alias, alias)) end
		if (info) then table.insert(fmt, string.format(fmt_info, info)) end
		table.insert(fmt, string.format(fmt_return, ret))
		--if (... == true) then table.insert(fmt, string.format(fmt_derive, name)) end
		table.insert(tbl, {alias = alias, func = func, fmt = table.concat(fmt, "\n")})
	end
	
	return tbl
end

function java2wiki(out, space, str, append)
	local function funcSort(a, b)
		return a.func < b.func
	end
	
	local doc = grabdoc(str)
	table.sort(doc, funcSort)
	
	local fmt = {}
	for k, tbl in pairs(doc) do
		table.insert(fmt, tbl.fmt)	
	end
	
	local docStr = string.format("= %s =\n\n%s", space, table.concat(fmt, "\n\n"))
	if (append) then
		docStr = "\n\n" .. docStr
		fileAppend(fileDir .. "wiki/" .. out .. ".txt", docStr)
	else
		fileWrite(fileDir .. "wiki/" .. out .. ".txt", docStr)
	end
end

-- Hooks

java2wiki("Hooks", "Shared", "LuaEventManager")
java2wiki("Hooks", "Client", "LuaEventManagerClient", true)
java2wiki("Hooks", "Shared", "LuaCraftState", true)

-- Library

java2wiki("Globals", "Shared", "library/LuaGlobals")
java2wiki("Globals", "Client", "library/client/LuaGlobals", true)
java2wiki("Globals", "Server", "library/server/LuaGlobals", true)

java2wiki("Command", "Shared", "library/LuaLibCommand")

java2wiki("Game", "Client", "library/client/LuaLibGame")
java2wiki("Game", "Server", "library/server/LuaLibGame", true)

java2wiki("HTTP", "Shared", "library/LuaLibHTTP")
java2wiki("Language", "Shared", "library/LuaLibLanguage")
java2wiki("SQL", "Shared", "library/LuaLibSQL")
java2wiki("ThreadLib", "Shared", "library/LuaLibThread")
java2wiki("Util", "Shared", "library/LuaLibUtil")

java2wiki("Input", "Client", "library/client/LuaLibInput")
java2wiki("Profiler", "Client", "library/client/LuaLibProfiler")
java2wiki("Render", "Client", "library/client/LuaLibRender")
java2wiki("Surface", "Client", "library/client/LuaLibSurface")

-- Meta

java2wiki("Angle", "Shared", "meta/LuaAngle")
java2wiki("Block", "Shared", "meta/LuaBlock")

java2wiki("ByteBuf", "Client", "meta/client/LuaByteBuf")
java2wiki("ByteBuf", "Shared", "meta/LuaByteBuf", true)
java2wiki("ByteBuf", "Server", "meta/server/LuaByteBuf", true)

java2wiki("Channel", "Shared", "meta/LuaChannel")
java2wiki("Color", "Shared", "meta/LuaColor")
java2wiki("Container", "Shared", "meta/LuaContainer")
java2wiki("DamageSource", "Shared", "meta/LuaDamageSource")
java2wiki("DataWatcher", "Shared", "meta/LuaDataWatcher")

java2wiki("Entity", "Client", "meta/client/LuaEntity")
java2wiki("Entity", "Shared", "meta/LuaEntity", true)

java2wiki("EntityItem", "Shared", "meta/LuaEntityItem")
java2wiki("EntityDamageSource", "Shared", "meta/LuaEntityDamageSource")
java2wiki("ItemStack", "Shared", "meta/LuaItemStack")
java2wiki("Living", "Shared", "meta/LuaLiving")
java2wiki("LivingBase", "Shared", "meta/LuaLivingBase")
java2wiki("NBTTag", "Shared", "meta/LuaNBTTag")
java2wiki("Object", "Shared", "meta/LuaObject")

java2wiki("Player", "Shared", "meta/LuaPlayer")
java2wiki("Player", "Server", "meta/server/LuaPlayer", true)

java2wiki("Resource", "Shared", "meta/LuaResource")
java2wiki("SQLDatabase", "Shared", "meta/LuaSQLDatabase")
java2wiki("SQLQuery", "Shared", "meta/LuaSQLQuery")
java2wiki("Thread", "Shared", "meta/LuaThread")
java2wiki("World", "Shared", "meta/LuaWorld")

java2wiki("Font", "Client", "meta/client/LuaFont")
java2wiki("ModelResource", "Client", "meta/client/LuaModelResource")

java2wiki("Vector", "Client", "meta/client/LuaVector")
java2wiki("Vector", "Shared", "meta/LuaVector", true)

java2wiki("PropertyManager", "Server", "meta/server/LuaPropertyManager")