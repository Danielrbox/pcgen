/*
 * EqContainerwToken.java
 * Copyright 2003 (C) Devon Jones <soulcatcher@evilsoft.org>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.     See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on December 15, 2003, 12:21 PM
 *
 * Current Ver: $Revision$
 * Last Editor: $Author$
 * Last Edited: $Date$
 *
 */
package plugin.exporttokens;

import pcgen.cdom.enumeration.FormulaKey;
import pcgen.core.Equipment;
import pcgen.core.Globals;
import pcgen.core.PlayerCharacter;
import pcgen.core.analysis.OutputNameFormatting;
import pcgen.io.ExportHandler;
import pcgen.io.exporttoken.Token;
import pcgen.util.BigDecimalHelper;

import java.math.BigDecimal;
import java.util.StringTokenizer;

/**
 * Deals with EQCONTAINERW Token
 *
 * EQCONTAINERW.x.ACCHECK
 * EQCONTAINERW.x.ACMOD
 * EQCONTAINERW.x.ALTCRIT
 * EQCONTAINERW.x.ALTDAMAGE
 * EQCONTAINERW.x.ATTACKS
 * EQCONTAINERW.x.CARRIED
 * EQCONTAINERW.x.CONTENTS.?
 * EQCONTAINERW.x.CONTENTWEIGHT
 * EQCONTAINERW.x.COST
 * EQCONTAINERW.x.CRITMULT
 * EQCONTAINERW.x.CRITRANGE
 * EQCONTAINERW.x.DAMAGE
 * EQCONTAINERW.x.EDR
 * EQCONTAINERW.x.EQUIPPED
 * EQCONTAINERW.x.ITEMWEIGHT
 * EQCONTAINERW.x.LOCATION
 * EQCONTAINERW.x.LONGNAME
 * EQCONTAINERW.x.MAXDEX
 * EQCONTAINERW.x.MOVE
 * EQCONTAINERW.x.NAME
 * EQCONTAINERW.x.OUTPUTNAME
 * EQCONTAINERW.x.PROF
 * EQCONTAINERW.x.QTY
 * EQCONTAINERW.x.RANGE
 * EQCONTAINERW.x.SIZE
 * EQCONTAINERW.x.SPELLFAILURE
 * EQCONTAINERW.x.SPROP
 * EQCONTAINERW.x.TOTALWEIGHT
 * EQCONTAINERW.x.TYPE.?
 * EQCONTAINERW.x.WT
 */
public class EqContainerwToken extends Token
{
	/** Token Name */
	public static final String TOKENNAME = "EQCONTAINERW";
	/** Indent, TAB character */
	public static final String INDENT = "&nbsp;&nbsp;";

	/**
	 * @see pcgen.io.exporttoken.Token#getTokenName()
	 */
	@Override
	public String getTokenName()
	{
		return TOKENNAME;
	}

	/**
	 * @see pcgen.io.exporttoken.Token#getToken(java.lang.String, pcgen.core.PlayerCharacter, pcgen.io.ExportHandler)
	 */
	@Override
	public String getToken(String tokenSource, PlayerCharacter pc,
		ExportHandler eh)
	{
		String retString = "";
		StringTokenizer aTok = new StringTokenizer(tokenSource, ".", false);
		aTok.nextToken(); //clear EQCONTAINERW Token
		Equipment eq = null;
		if (aTok.hasMoreElements())
		{
			try
			{
				int containerNo = Integer.parseInt(aTok.nextToken());
				eq = getContainer(pc, containerNo);
			}
			catch (NumberFormatException e)
			{
				// TODO - This exception needs to be handled
			}
		}

		if (eq != null)
		{
			String property = "NAME";
			if (aTok.hasMoreElements())
			{
				property = aTok.nextToken();
			}

			if (property.equals("ACCHECK"))
			{
				retString = Integer.toString(getAcCheckToken(pc, eq));
			}
			else if (property.equals("ACMOD"))
			{
				retString = Integer.toString(getAcModToken(pc, eq));
			}
			else if (property.equals("ALTCRIT"))
			{
				retString = getAltCritToken(eq);
			}
			else if (property.equals("ALTDAMAGE"))
			{
				retString = getAltDamageToken(pc, eq);
			}
			else if (property.equals("ATTACKS"))
			{
				retString = Double.toString(getAttacksToken(pc, eq));
			}
			else if (property.equals("CARRIED"))
			{
				retString = Float.toString(getCarriedToken(eq));
			}
			else if (property.equals("CONTENTS"))
			{
				retString = getContentsToken(eq, aTok);
			}
			else if (property.equals("CONTENTWEIGHT"))
			{
				retString =
						BigDecimalHelper.trimZeros(Float
							.toString(getContentWeightToken(pc, eq)));
			}
			else if (property.equals("COST"))
			{
				retString = BigDecimalHelper.trimZeros(getCostToken(pc, eq));
			}
			else if (property.equals("CRITMULT"))
			{
				retString = getCritMultToken(eq);
			}
			else if (property.equals("CRITRANGE"))
			{
				retString = getCritRangeToken(pc, eq);
			}
			else if (property.equals("DAMAGE"))
			{
				retString = getDamageToken(pc, eq);
			}
			else if (property.equals("EDR"))
			{
				retString = Integer.toString(getEdrToken(pc, eq));
			}
			else if (property.equals("EQUIPPED"))
			{
				retString = getEquippedToken(eq);
			}
			else if (property.equals("ITEMWEIGHT"))
			{
				retString =
						BigDecimalHelper.trimZeros(Float
							.toString(getItemWeightToken(pc, eq)));
			}
			else if (property.equals("LOCATION"))
			{
				retString = getLocationToken(eq);
			}
			else if (property.equals("LONGNAME"))
			{
				retString = getLongNameToken(eq);
			}
			else if (property.equals("MAXDEX"))
			{
				retString = Integer.toString(getMaxDexToken(pc, eq));
			}
			else if (property.equals("MOVE"))
			{
				retString = getMoveToken(eq);
			}
			else if (property.equals("NAME") || property.equals("OUTPUTNAME"))
			{
				retString = getNameToken(eq, pc);
			}
			else if (property.equals("PROF"))
			{
				retString = eq.consolidatedProfName();
			}
			else if (property.equals("QTY"))
			{
				retString =
						BigDecimalHelper.trimZeros(Double
							.toString((getQuantityToken(eq))));
			}
			else if (property.equals("RANGE"))
			{
				retString = Integer.toString(getRangeToken(eq, pc));
			}
			else if (property.equals("SIZE"))
			{
				retString = getSizeToken(eq);
			}
			else if (property.equals("SPELLFAILURE"))
			{
				retString = Integer.toString(getSpellFailureToken(pc, eq));
			}
			else if (property.equals("SPROP"))
			{
				retString = getSPropToken(pc, eq);
			}
			else if (property.equals("TOTALWEIGHT") || property.equals("WT"))
			{
				retString =
						BigDecimalHelper.trimZeros(Float
							.toString(getTotalWeightToken(pc, eq)));
			}
			else if (property.equals("TYPE"))
			{
				retString = getTypeToken(eq, aTok);
			}
		}
		return retString;
	}

	/**
	 * Get the AC Check Token
	 * @param pc
	 * @param eq
	 * @return AC Check Token
	 */
	public static int getAcCheckToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.acCheck(pc).intValue();
	}

	/**
	 * Get the AC Mod Token
	 * @param pc
	 * @param eq
	 * @return AC Mod Token
	 */
	public static int getAcModToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.getACMod(pc).intValue();
	}

	/**
	 * Get Alternative Critical Token
	 * @param eq
	 * @return Alternative Critical Token
	 */
	public static String getAltCritToken(Equipment eq)
	{
		return eq.getAltCritMult();
	}

	/**
	 * Get alternative damage token
	 * @param pc
	 * @param eq
	 * @return alternative damage token
	 */
	public static String getAltDamageToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.getAltDamage(pc);
	}

	/**
	 * Get Attacks token
	 * @param pc
	 * @param eq
	 * @return Attacks token
	 */
	public static double getAttacksToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.bonusTo(pc, "COMBAT", "ATTACKS", true);
	}

	/**
	 * Get Carried token
	 * @param eq
	 * @return Carried token
	 */
	public static float getCarriedToken(Equipment eq)
	{
		return eq.numberCarried().floatValue();
	}

	/**
	 * Get Contents Token
	 * @param eq
	 * @param aTok
	 * @return Contents Token
	 */
	public static String getContentsToken(Equipment eq, StringTokenizer aTok)
	{
		String retString = "";
		if (aTok.hasMoreTokens())
		{
			String aType = aTok.nextToken();
			String aSubTag = "NAME";

			if (aTok.hasMoreTokens())
			{
				aSubTag = aTok.nextToken();
			}

			retString = eq.getContainerByType(aType, aSubTag);
		}
		else
		{
			retString = eq.getContainerContentsString();
		}
		return retString;
	}

	/**
	 * Get Content Weight Token
	 * @param pc
	 * @param eq
	 * @return Content Weight Token
	 */
	public static float getContentWeightToken(PlayerCharacter pc, Equipment eq)
	{
		if (eq.getChildCount() == 0)
		{
			return 0;
		}
		return eq.getContainedWeight(pc).floatValue();
	}

	/**
	 * Get Cost token
	 * @param pc
	 * @param eq
	 * @return Cost token
	 */
	public static BigDecimal getCostToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.getCost(pc);
	}

	/**
	 * Get Critical Multiplier Token
	 * @param eq
	 * @return Critical Multiplier Token
	 */
	public static String getCritMultToken(Equipment eq)
	{
		return eq.getCritMult();
	}

	/**
	 * Get Critical Range Token
	 * @param pc
	 * @param eq
	 * @return Critical Range Token
	 */
	public static String getCritRangeToken(PlayerCharacter pc, Equipment eq)
	{
		int critRange = pc.getCritRange(eq, true);
		return critRange == 0 ? "" : Integer.toString(critRange);
	}

	/**
	 * Get Damage Token
	 * @param pc
	 * @param eq
	 * @return Damage Token
	 */
	public static String getDamageToken(PlayerCharacter pc, Equipment eq)
	{
		String retString = eq.getDamage(pc);

		if ((pc != null) && (eq.isNatural()))
		{
			retString = Globals.adjustDamage(retString, pc.getDisplay().getRace().getSafe(
					FormulaKey.SIZE).resolve(pc, "").intValue(), pc.getDisplay().sizeInt());
		}

		return retString;
	}

	/**
	 * Get eDR Token
	 * @param pc
	 * @param eq
	 * @return eDR Token
	 */
	public static int getEdrToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.eDR(pc).intValue();
	}

	/**
	 * Get Equipped Token
	 * @param eq
	 * @return Equipped Token
	 */
	public static String getEquippedToken(Equipment eq)
	{
		if (eq.isEquipped())
		{
			return "Y";
		}
		return "N";
	}

	/**
	 * Get Item Weight Token
	 * @param pc
	 * @param eq
	 * @return Item Weight Token
	 */
	public static float getItemWeightToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.getWeight(pc).floatValue();
	}

	/**
	 * Get Location Token
	 * @param eq
	 * @return Location Token
	 */
	public static String getLocationToken(Equipment eq)
	{
		return eq.getParentName();
	}

	/**
	 * Get Long Name Token
	 * @param eq
	 * @return Long Name Token
	 */
	public static String getLongNameToken(Equipment eq)
	{
		StringBuilder retString = new StringBuilder();
		int depth = eq.itemDepth();

		while (depth > 0)
		{
			retString.append(INDENT);
			--depth;
		}

		retString.append(eq.longName());
		return retString.toString();
	}

	/**
	 * Get Max DEX Token
	 * @param pc
	 * @param eq
	 * @return Max DEX Token
	 */
	public static int getMaxDexToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.getMaxDex(pc).intValue();
	}

	/**
	 * Get Move Token
	 * @param eq
	 * @return Move Token
	 */
	public static String getMoveToken(Equipment eq)
	{
		return eq.moveString();
	}

	/**
	 * Get Name Token
	 * @param eq
	 * @param pc
	 * @return Name Token
	 */
	public static String getNameToken(Equipment eq, PlayerCharacter pc)
	{
		return OutputNameFormatting.parseOutputName(eq, pc);
	}

	/**
	 * Get Quantity Token
	 * @param eq
	 * @return Quantity Token
	 */
	public static double getQuantityToken(Equipment eq)
	{
		return eq.qty();
	}

	/**
	 * Get Range Token
	 * @param eq
	 * @param pc
	 * @return Range Token
	 */
	public static int getRangeToken(Equipment eq, PlayerCharacter pc)
	{
		return eq.getRange(pc).intValue();
	}

	/**
	 * Get Size Token
	 * @param eq
	 * @return Size Token
	 */
	public static String getSizeToken(Equipment eq)
	{
		return eq.getSize();
	}

	/**
	 * Get Spell Failure Token
	 * @param pc
	 * @param eq
	 * @return Spell Failure Token
	 */
	public static int getSpellFailureToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.spellFailure(pc).intValue();
	}

	/**
	 * Get Special Property Token
	 * @param pc
	 * @param eq
	 * @return Special Property Token
	 */
	public static String getSPropToken(PlayerCharacter pc, Equipment eq)
	{
		return eq.getSpecialProperties(pc);
	}

	/**
	 * Get Total Weight Token
	 * @param pc
	 * @param eq
	 * @return Total Weight Token
	 */
	public static float getTotalWeightToken(PlayerCharacter pc, Equipment eq)
	{
		return getContentWeightToken(pc, eq) + getItemWeightToken(pc, eq);
	}

	/**
	 * Get Type Token
	 * @param eq
	 * @param aTok
	 * @return Type Token
	 */
	public static String getTypeToken(Equipment eq, StringTokenizer aTok)
	{
		String retString = "";
		if (aTok.hasMoreTokens())
		{
			try
			{
				int x = Integer.parseInt(aTok.nextToken());
				retString = eq.typeIndex(x);
			}
			catch (NumberFormatException e)
			{
				// TODO - This exception needs to be handled
			}
		}
		else
		{
			retString = eq.getType();
		}
		return retString;
	}

	private Equipment getContainer(PlayerCharacter pc, int no)
	{
		for (Equipment eq : pc.getEquipmentListInOutputOrder())
		{
			if (eq.isContainer())
			{
				no--;
			}

			if (no < 0)
			{
				return eq;
			}
		}
		return null;
	}
}
