package com.luacraft.meta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.luacraft.LuaCraftState;
import com.luacraft.LuaUserdataManager;
import com.luacraft.classes.Angle;
import com.luacraft.classes.Vector;
import com.naef.jnlua.JavaFunction;
import com.naef.jnlua.LuaState;

public class LuaEntity
{
	public static JavaFunction __tostring = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			GetClass.invoke(l);
			l.pushString(String.format("Entity [%d][%s]", self.getEntityId(), l.toString(-1)));
			return 1;
		}
	};

	public static JavaFunction __eq = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Entity other = (Entity) l.checkUserdata(2, Entity.class, "Entity");
			l.pushBoolean(self.getEntityId() == other.getEntityId());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function AttackFrom
	 * Attack an entity using a [[DamageSource]]
	 * @arguments [[DamageSource]]:source, [[Number]]:damage
	 * @return nil
	 */

	public static JavaFunction AttackFrom = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			DamageSource damage = (DamageSource) l.checkUserdata(2, DamageSource.class, "DamageSource");
			self.attackEntityFrom(damage, (float) l.checkNumber(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetUniqueID
	 * Get the entities unique ID that will be persistent
	 * If used on a player in online-mode, it will return the Minecraft UUID for the player
	 * @arguments nil
	 * @return [[String]]:id
	 */

	public static JavaFunction GetUniqueID = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushString(self.getUniqueID().toString());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsAlive
	 * Returns if the entity is alive
	 * @arguments nil
	 * @return [[Boolean]]:alive
	 */

	public static JavaFunction IsAlive = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isEntityAlive());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsOnGround
	 * Check if the entity stands on ground
	 * @arguments nil
	 * @return [[Boolean]]:onground
	 */

	public static JavaFunction IsOnGround = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.onGround);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsEntity
	 * Returns if the argument is an entity
	 * @arguments arg
	 * @return [[Boolean]]:entity
	 */

	public static JavaFunction IsEntity = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, Entity.class));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsPlayer
	 * Returns if the entity is a player entity
	 * @arguments nil
	 * @return [[Boolean]]:player
	 */

	public static JavaFunction IsPlayer = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, EntityPlayer.class));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsMob
	 * Returns if the entity is a mob entity
	 * @arguments nil
	 * @return [[Boolean]]:mob
	 */

	public static JavaFunction IsMob = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, EntityMob.class));
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsAnimal
	 * Returns if the entity is an animal entity
	 * @arguments nil
	 * @return [[Boolean]]:animal
	 */

	public static JavaFunction IsAnimal = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, EntityAnimal.class));
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsTameable
	 * Returns if the entity is a tameable entity
	 * @arguments nil
	 * @return [[Boolean]]:tameable
	 */

	public static JavaFunction IsTameable = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, EntityTameable.class));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsLiving
	 * Returns if the entity is a living entity
	 * @arguments nil
	 * @return [[Boolean]]:living
	 */

	public static JavaFunction IsLiving = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, EntityLiving.class));
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsItem
	 * Returns if the entity is an item entity
	 * @arguments nil
	 * @return [[Boolean]]:item
	 */

	public static JavaFunction IsItem = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			l.pushBoolean(l.isUserdata(1, EntityItem.class));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsInstanceOf
	 * Returns if the entity is an instance of the given
	 * @arguments [[Entity]]:entity
	 * @return [[Boolean]]:instance
	 */

	public static JavaFunction IsInstanceOf = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Entity other = (Entity) l.checkUserdata(2, Entity.class, "Entity");
			l.pushBoolean(self.getClass().isAssignableFrom(other.getClass()));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsValid
	 * Returns if the entity is not null
	 * @arguments nil
	 * @return [[Boolean]]:valid
	 */

	public static JavaFunction IsValid = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self != null);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetAngles
	 * Returns the entity angles
	 * @arguments nil
	 * @return [[Angle]]:ang
	 */

	public static JavaFunction GetAngles = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Angle ang = new Angle(self.rotationPitch, self.rotationYaw);
			ang.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetAngles
	 * Sets the entity angles
	 * @arguments [[Angle]]:ang
	 * @return nil
	 */

	public static JavaFunction SetAngles = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Angle ang = (Angle) l.checkUserdata(2, Angle.class, "Angle");
			self.setRotation((float) ang.p, (float) ang.y);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetPos
	 * Returns the entity position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	public static JavaFunction GetPos = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.posX, self.posZ, self.posY);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetPos
	 * Sets the entity position
	 * @arguments [[Vector]]:pos
	 * @return nil
	 */

	public static JavaFunction SetPos = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.setPosition(pos.x, pos.z, pos.y);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function IsBurning
	 * Check if the entity is burning
	 * @arguments nil
	 * @return [[Boolean]]:burning
	 */

	public static JavaFunction IsBurning = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isBurning());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function Ignite
	 * Set the entity on fire
	 * @arguments [ [[Number]]:duration ]
	 * @return nil
	 * This is an alternative function to SetBurning
	 */

	public static JavaFunction Ignite = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setFire(l.checkInteger(2,15));
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function Extinguish
	 * Extinguish the entity
	 * @arguments nil
	 * @return nil
	 * This is an alternative function to SetBurning
	 */

	public static JavaFunction Extinguish = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setFire(0);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function SetNoClip
	 * Set the entity into noclip mode
	 * @arguments [[Boolean]]:noclip
	 * @return nil
	 */

	public static JavaFunction SetNoClip = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.noClip = l.checkBoolean(2);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetNoClip
	 * Get whether or not the entity is in noclip mode
	 * @arguments nil
	 * @return [[Boolean]]:noclipping
	 */

	public static JavaFunction GetNoClip = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.noClip);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsMounted
	 * Check if the entity is mounted by another entity
	 * @arguments nil
	 * @return [[Boolean]]:mounted
	 */

	public static JavaFunction IsMounted = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.ridingEntity != null);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetMount
	 * Mount the entity to another entity
	 * @arguments [[Entity]]:ent
	 * @return nil
	 */

	public static JavaFunction SetMount = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Entity other = (Entity) l.checkUserdata(2, Entity.class, "Entity");
			self.mountEntity(other);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetMount
	 * Get the entity that is being ridden
	 * @arguments nil
	 * @return [[Entity]]:riding
	 */

	public static JavaFunction GetMount = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.ridingEntity, "Entity");
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsSneaking
	 * Check if the entity is sneaking
	 * @arguments nil
	 * @return [[Boolean]]:sneak
	 */

	public static JavaFunction IsSneaking = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isSneaking());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsInWater
	 * Check if the entity is in water
	 * @arguments nil
	 * @return [[Boolean]]:inwater
	 */

	public static JavaFunction IsInWater = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isInWater());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function GetAir
	 * Return the entitys air volume
	 * @arguments nil
	 * @return [[Number]]:air
	 */

	public static JavaFunction GetAir = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.getAir());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetAir
	 * Set the entitys air volume
	 * @arguments [[Number]]:air
	 * @return nil
	 */

	public static JavaFunction SetAir = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setAir(l.checkInteger(2));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetFireResistance
	 * The amount of ticks you have to stand inside in fire before be set on fire
	 * @arguments nil
	 * @return [[Number]]:res
	 */

	public static JavaFunction GetFireResistance = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.fireResistance);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetFireResistance
	 * Sets the entitys resistance to fire
	 * @arguments [[Number]]:resistance
	 * @return nil
	 */

	public static JavaFunction SetFireResistance = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.fireResistance = l.checkInteger(2);
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetEyePos
	 * Returns the entitys eye position
	 * @arguments nil
	 * @return [[Vector]]:pos
	 */

	public static JavaFunction GetEyePos = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.posX, self.posZ, self.posY + self.getEyeHeight());
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function GetEyeHeight
	 * Returns the entitys eye height
	 * @arguments nil
	 * @return [[Number]]:height
	 */

	public static JavaFunction GetEyeHeight = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.getEyeHeight());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetVelocity
	 * Returns the entitys current velocity
	 * @arguments nil
	 * @return [[Vector]]:vel
	 */

	public static JavaFunction GetVelocity = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = new Vector(self.motionX, self.motionZ, self.motionY);
			pos.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetVelocity
	 * Sets the entitys current velocity
	 * @arguments [[Vector]]:vel
	 * @return nil
	 */

	public static JavaFunction SetVelocity = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector vel = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.motionX = vel.x;
			self.motionY = vel.z;
			self.motionZ = vel.y;
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function AddVelocity
	 * Adds to the entitys current velocity
	 * @arguments [[Vector]]:vel
	 * @return nil
	 */

	public static JavaFunction AddVelocity = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector vel = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.addVelocity(vel.x, vel.z, vel.y);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function IsInAir
	 * Returns if the entity is airborne
	 * @arguments nil
	 * @return [[Boolean]]:inair
	 */

	public static JavaFunction IsInAir = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isAirBorne);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function IsSprinting
	 * Check if the entity is sprinting
	 * @arguments nil
	 * @return [[Boolean]]:sprinting
	 */

	public static JavaFunction IsSprinting = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isSprinting());
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function SetSprinting
	 * Set the entity to sprint
	 * @arguments [[Boolean]]:sprinting
	 * @return nil
	 * This onls works if the forward movement is not zero
	 */

	public static JavaFunction SetSprinting = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setSprinting(l.checkBoolean(2));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetClass
	 * Returns the class the entity is a part of
	 * @arguments nil
	 * @return [[Boolean]]:classname
	 */

	public static JavaFunction GetClass = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			String name = EntityList.getEntityString(self);
			l.pushString(name != null ? name : self.getClass().getSimpleName());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function EntIndex
	 * Returns the entity index
	 * @arguments nil
	 * @return [[Number]]:index
	 */

	public static JavaFunction EntIndex = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.getEntityId());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function IsInWeb
	 * Checks if the entity is in a web
	 * @arguments nil
	 * @return [[Boolean]]:web
	 */

	public static JavaFunction IsInWeb = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isInWeb);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetStepSize
	 * Returns the entity step size
	 * @arguments nil
	 * @return [[Number]]:stepsize
	 */

	public static JavaFunction GetStepSize = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.stepHeight);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetStepSize
	 * Sets the entity step size
	 * @arguments [[Number]]:stepsize
	 * @return nil
	 */

	public static JavaFunction SetStepSize = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.stepHeight = l.checkInteger(2);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetDimensionID
	 * Return the dimension number the entity is currently in
	 * @arguments nil
	 * @return [[Number]]:dimension
	 */

	public static JavaFunction GetDimensionID = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushInteger(self.dimension);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function SetWorld
	 * Set the world object in which the entity resides in
	 * @arguments [[World]]:world
	 * @return nil
	 */

	public static JavaFunction SetWorld = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			World world = (World) l.checkUserdata(2, World.class, "World");
			self.worldObj = world;
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetWorld
	 * Get the world object in which the entity resides in
	 * @arguments nil
	 * @return [[World]]:world
	 */

	public static JavaFunction GetWorld = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.worldObj, "World");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function TravelToWorld
	 * Moves the entity to another world
	 * @arguments [[World]]:world
	 * @return nil
	 */

	public static JavaFunction TravelToWorld = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			World world = (World) l.checkUserdata(2, World.class, "World");
			self.travelToDimension(world.provider.getDimensionId());
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function GetWidth
	 * Get the width of the entity
	 * @arguments nil
	 * @return [[Number]]:width
	 */

	public static JavaFunction GetWidth = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.width);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetHeight
	 * Get the height of the entity
	 * @arguments nil
	 * @return [[Number]]:height
	 */

	public static JavaFunction GetHeight = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.height);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetSize
	 * Get the width and the height of the entity
	 * @arguments nil
	 * @return [[Number]]:width, [[Number]]:height
	 */

	public static JavaFunction GetSize = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushNumber(self.width);
			l.pushNumber(self.height);
			return 2;
		}
	};

	/**
	 * @author Jake
	 * @function SetSize
	 * Set the width and the height of the entity
	 * @arguments [[Number]]:width, [[Number]]:height
	 * @return nil
	 */

	public static JavaFunction SetSize = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setSize((float) l.checkNumber(2), (float) l.checkNumber(3));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function Explode
	 * Explodes the entity
	 * @arguments [[Number]]:size
	 * @return nil
	 */

	public static JavaFunction Explode = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.worldObj.createExplosion(self, self.posX, self.posY, self.posZ, (float) l.checkNumber(2,5), l.checkBoolean(3,false));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function OBBMins
	 * Gets the entitys minimum bound
	 * @arguments nil
	 * @return [[Vector]]:mins
	 */

	public static JavaFunction OBBMins = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			AxisAlignedBB bb = self.getBoundingBox();
			Vector mins = new Vector(bb.minX, bb.minZ, bb.minY);
			mins.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function OBBMaxs
	 * Gets the entitys maximum bound
	 * @arguments nil
	 * @return [[Vector]]:maxs
	 */

	public static JavaFunction OBBMaxs = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			AxisAlignedBB bb = self.getBoundingBox();
			Vector maxs = new Vector(bb.maxX, bb.maxZ, bb.maxY);
			maxs.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function WorldToLocal
	 * Get the local vector of a world vector in relation to the entity
	 * @arguments [[Vector]]:world
	 * @return [[Vector]]:local
	 */

	public static JavaFunction WorldToLocal = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector toVec = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			Vector local = new Vector(self.posX - toVec.x, self.posZ - toVec.y, self.posY - toVec.z);
			local.push(l);
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function LocalToWorld
	 * Get the world vector of a local vector in relation to the entity
	 * @arguments [[Vector]]:local
	 * @return [[Vector]]:world
	 */

	public static JavaFunction LocalToWorld = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector toVec = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			Vector local = new Vector(self.posX + toVec.x, self.posZ + toVec.y, self.posY + toVec.z);
			local.push(l);
			return 1;
		}
	};

	/**
	 * @author Gregor
	 * @function Move
	 * Try to move the entity to the given position
	 * @arguments [[Vector]]:pos
	 * @return nil
	 */

	public static JavaFunction Move = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			Vector pos = (Vector) l.checkUserdata(2, Vector.class, "Vector");
			self.moveEntity(pos.x, pos.z, pos.y);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function PlaySound
	 * Play a sound emitting from the entity
	 * @arguments [[String]]:Sound name, [ [[Number]]:Volume, [[Number]]:Pitch ]
	 * @return nil
	 */

	public static JavaFunction PlaySound = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.playSound(l.checkString(2), (float) l.checkNumber(3, 1), (float) l.checkNumber(4, 1));
			return 0;
		}
	};

	/**
	 * @author Gregor
	 * @function GetItem
	 * Gets the entities [[ItemStack]]
	 * @arguments nil
	 * @return [[ItemStack]]:item
	 */

	public static JavaFunction GetItem = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			EntityItem self = (EntityItem) l.checkUserdata(1, EntityItem.class, "EntityItem");
			l.pushUserdataWithMeta(self.getEntityItem(), "ItemStack");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function Spawn
	 * Spawn an entity in the world
	 * @arguments [ [[Boolean]]:force ]
	 * @return [[Boolean]]:success
	 */

	public static JavaFunction Spawn = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.forceSpawn = l.checkBoolean(2, false);
			l.pushBoolean(self.worldObj.spawnEntityInWorld(self));
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function Remove
	 * Remove the entity
	 * @arguments nil
	 * @return nil
	 */

	public static JavaFunction Remove = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.worldObj.removeEntity(self);
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function SetInvisible
	 * Sets if the entity is invisible or not
	 * @arguments [[Boolean]]:invisible
	 * @return nil
	 */

	public static JavaFunction SetInvisible = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			self.setInvisible(l.checkBoolean(2));
			return 0;
		}
	};

	/**
	 * @author Jake
	 * @function IsInvisible
	 * Returns if the entity is invisible
	 * @arguments nil
	 * @return [[Boolean]]:invisible
	 */

	public static JavaFunction IsInvisible = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushBoolean(self.isInvisible());
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetNBTTag
	 * Returns the entities NBTTag compound
	 * @arguments nil
	 * @return [[NBTTag]]:nbttag
	 */

	public static JavaFunction GetNBTTag = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.getEntityData(), "NBTTag");
			return 1;
		}
	};

	/**
	 * @author Jake
	 * @function GetDataWatcher
	 * Returns a DataWatcher which can be used to control the entities networked data
	 * @arguments nil
	 * @return [[DataWatcher]]:networkdata
	 */

	public static JavaFunction GetDataWatcher = new JavaFunction()
	{
		public int invoke(LuaState l)
		{
			Entity self = (Entity) l.checkUserdata(1, Entity.class, "Entity");
			l.pushUserdataWithMeta(self.getDataWatcher(), "DataWatcher");
			return 1;
		}
	};

	public static void Init(final LuaCraftState l)
	{
		l.pushJavaFunction(IsEntity);
		l.setGlobal("IsEntity");
		l.pushJavaFunction(IsPlayer);
		l.setGlobal("IsPlayer");
		l.pushJavaFunction(IsMob);
		l.setGlobal("IsMob");
		l.pushJavaFunction(IsAnimal);
		l.setGlobal("IsAnimal");
		l.pushJavaFunction(IsTameable);
		l.setGlobal("IsTameable");
		l.pushJavaFunction(IsLiving);
		l.setGlobal("IsLiving");
		l.pushJavaFunction(IsItem);
		l.setGlobal("IsItem");

		l.newMetatable("Entity");
		{
			l.pushJavaFunction(__tostring);
			l.setField(-2, "__tostring");
			l.pushJavaFunction(__eq);
			l.setField(-2, "__eq");

			LuaUserdataManager.SetupMetaMethods(l);

			l.pushJavaFunction(AttackFrom);
			l.setField(-2, "AttackFrom");
			l.pushJavaFunction(GetUniqueID);
			l.setField(-2, "GetUniqueID");
			l.pushJavaFunction(IsAlive);
			l.setField(-2, "IsAlive");
			l.pushJavaFunction(IsOnGround);
			l.setField(-2, "IsOnGround");
			l.pushJavaFunction(IsPlayer);
			l.setField(-2, "IsPlayer");
			l.pushJavaFunction(IsMob);
			l.setField(-2, "IsMob");
			l.pushJavaFunction(IsAnimal);
			l.setField(-2, "IsAnimal");
			l.pushJavaFunction(IsTameable);
			l.setField(-2, "IsTameable");
			l.pushJavaFunction(IsLiving);
			l.setField(-2, "IsLiving");
			l.pushJavaFunction(IsItem);
			l.setField(-2, "IsItem");
			l.pushJavaFunction(IsInstanceOf);
			l.setField(-2, "IsInstanceOf");
			l.pushJavaFunction(IsValid);
			l.setField(-2, "IsValid");
			l.pushJavaFunction(GetAngles);
			l.setField(-2, "GetAngles");
			l.pushJavaFunction(SetAngles);
			l.setField(-2, "SetAngles");
			l.pushJavaFunction(GetPos);
			l.setField(-2, "GetPos");
			l.pushJavaFunction(SetPos);
			l.setField(-2, "SetPos");
			l.pushJavaFunction(IsBurning);
			l.setField(-2, "IsBurning");
			l.pushJavaFunction(Ignite);
			l.setField(-2, "Ignite");
			l.pushJavaFunction(Extinguish);
			l.setField(-2, "Extinguish");
			l.pushJavaFunction(SetNoClip);
			l.setField(-2, "SetNoClip");
			l.pushJavaFunction(GetNoClip);
			l.setField(-2, "GetNoClip");
			l.pushJavaFunction(IsMounted);
			l.setField(-2, "IsMounted");
			l.pushJavaFunction(SetMount);
			l.setField(-2, "SetMount");
			l.pushJavaFunction(GetMount);
			l.setField(-2, "GetMount");
			l.pushJavaFunction(IsSneaking);
			l.setField(-2, "IsSneaking");
			l.pushJavaFunction(IsInWater);
			l.setField(-2, "IsInWater");
			l.pushJavaFunction(GetAir);
			l.setField(-2, "GetAir");
			l.pushJavaFunction(SetAir);
			l.setField(-2, "SetAir");
			l.pushJavaFunction(GetFireResistance);
			l.setField(-2, "GetFireResistance");
			l.pushJavaFunction(SetFireResistance);
			l.setField(-2, "SetFireResistance");
			l.pushJavaFunction(GetEyePos);
			l.setField(-2, "GetEyePos");
			l.pushJavaFunction(GetEyeHeight);
			l.setField(-2, "GetEyeHeight");
			l.pushJavaFunction(SetVelocity);
			l.setField(-2, "SetVelocity");
			l.pushJavaFunction(AddVelocity);
			l.setField(-2, "AddVelocity");
			l.pushJavaFunction(IsInAir);
			l.setField(-2, "IsInAir");
			l.pushJavaFunction(IsSprinting);
			l.setField(-2, "IsSprinting");
			l.pushJavaFunction(SetSprinting);
			l.setField(-2, "SetSprinting");
			l.pushJavaFunction(GetClass);
			l.setField(-2, "GetClass");
			l.pushJavaFunction(EntIndex);
			l.setField(-2, "EntIndex");
			l.pushJavaFunction(IsInWeb);
			l.setField(-2, "IsInWeb");
			l.pushJavaFunction(GetStepSize);
			l.setField(-2, "GetStepSize");
			l.pushJavaFunction(SetStepSize);
			l.setField(-2, "SetStepSize");
			l.pushJavaFunction(GetDimensionID);
			l.setField(-2, "GetDimensionID");
			l.pushJavaFunction(SetWorld);
			l.setField(-2, "SetWorld");
			l.pushJavaFunction(GetWorld);
			l.setField(-2, "GetWorld");
			l.pushJavaFunction(TravelToWorld);
			l.setField(-2, "TravelToWorld");
			l.pushJavaFunction(GetWidth);
			l.setField(-2, "GetWidth");
			l.pushJavaFunction(GetHeight);
			l.setField(-2, "GetHeight");
			l.pushJavaFunction(GetSize);
			l.setField(-2, "GetSize");
			l.pushJavaFunction(SetSize);
			l.setField(-2, "SetSize");
			l.pushJavaFunction(Explode);
			l.setField(-2, "Explode");
			l.pushJavaFunction(OBBMins);
			l.setField(-2, "OBBMins");
			l.pushJavaFunction(OBBMaxs);
			l.setField(-2, "OBBMaxs");
			l.pushJavaFunction(WorldToLocal);
			l.setField(-2, "WorldToLocal");
			l.pushJavaFunction(LocalToWorld);
			l.setField(-2, "LocalToWorld");
			l.pushJavaFunction(Move);
			l.setField(-2, "Move");
			l.pushJavaFunction(PlaySound);
			l.setField(-2, "PlaySound");
			l.pushJavaFunction(GetItem);
			l.setField(-2, "GetItem");
			l.pushJavaFunction(Spawn);
			l.setField(-2, "Spawn");
			l.pushJavaFunction(Remove);
			l.setField(-2, "Remove");
			l.pushJavaFunction(SetInvisible);
			l.setField(-2, "SetInvisible");
			l.pushJavaFunction(IsInvisible);
			l.setField(-2, "IsInvisible");
			l.pushJavaFunction(GetNBTTag);
			l.setField(-2, "GetNBTTag");
			l.pushJavaFunction(GetDataWatcher);
			l.setField(-2, "GetDataWatcher");
		}
		l.pop(1);

	}
}