/*
 * Copyright (c) 2014 Tom Parker <thpr@users.sourceforge.net>
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package tokenmodel.testsupport;

import pcgen.cdom.base.UserSelection;
import pcgen.cdom.content.CNAbility;
import pcgen.cdom.content.CNAbilityFactory;
import pcgen.cdom.enumeration.Nature;
import pcgen.cdom.helper.CNAbilitySelection;
import pcgen.core.Ability;
import pcgen.core.AbilityCategory;
import pcgen.rules.persistence.token.CDOMToken;
import pcgen.rules.persistence.token.ParseResult;
import plugin.lsttokens.ability.StackToken;
import plugin.lsttokens.choose.NoChoiceToken;

public abstract class AbstractAbilityGrantCheckTest extends AbstractTokenModelTest
{

	protected static final plugin.lsttokens.auto.FeatToken AUTO_FEAT_TOKEN =
			new plugin.lsttokens.auto.FeatToken();
	private static final plugin.lsttokens.choose.FeatSelectionToken CHOOSE_FEATSELECTION_TOKEN =
			new plugin.lsttokens.choose.FeatSelectionToken();
	private static final plugin.lsttokens.TypeLst TYPE_TOKEN =
			new plugin.lsttokens.TypeLst();
	private static final StackToken ABILITY_STACK_TOKEN = new StackToken();
	private static final NoChoiceToken CHOOSE_NOCHOICE_TOKEN =
			new NoChoiceToken();

	public Ability getMultNo(String s)
	{
		Ability a = create(Ability.class, s);
		context.getReferenceContext().reassociateCategory(AbilityCategory.FEAT, a);
		ParseResult result = TYPE_TOKEN.parseToken(context, a, "Selectable");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		return a;
	}

	public Ability getMultYesStackNo(String s, String target)
	{
		Ability a = create(Ability.class, s);
		context.getReferenceContext().reassociateCategory(AbilityCategory.FEAT, a);
		ParseResult result = AUTO_FEAT_TOKEN.parseToken(context, a, "%LIST");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = ABILITY_MULT_TOKEN.parseToken(context, a, "YES");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = CHOOSE_FEATSELECTION_TOKEN.parseToken(context, a, target);
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		return a;
	}

	public Ability getMultYesStackYes(String s, String target)
	{
		Ability a = create(Ability.class, s);
		context.getReferenceContext().reassociateCategory(AbilityCategory.FEAT, a);
		ParseResult result = AUTO_FEAT_TOKEN.parseToken(context, a, "%LIST");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = ABILITY_MULT_TOKEN.parseToken(context, a, "YES");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = ABILITY_STACK_TOKEN.parseToken(context, a, "YES");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = CHOOSE_FEATSELECTION_TOKEN.parseToken(context, a, target);
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		return a;
	}

	public Ability getMultYesStackNoChooseNoChoice(String s)
	{
		Ability a = create(Ability.class, s);
		context.getReferenceContext().reassociateCategory(AbilityCategory.FEAT, a);
		ParseResult result = ABILITY_MULT_TOKEN.parseToken(context, a, "YES");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = CHOOSE_NOCHOICE_TOKEN.parseToken(context, a, null);
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		return a;
	}

	public Ability getMultYesStackYesChooseNoChoice(String s)
	{
		Ability a = create(Ability.class, s);
		context.getReferenceContext().reassociateCategory(AbilityCategory.FEAT, a);
		ParseResult result = ABILITY_MULT_TOKEN.parseToken(context, a, "YES");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = ABILITY_STACK_TOKEN.parseToken(context, a, "YES");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		result = CHOOSE_NOCHOICE_TOKEN.parseToken(context, a, null);
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		return a;
	}

	public void testMultNo()
	{
		getMultNo("MultNo");
		Ability parent = getGrantor("MultNo");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "MultNo"));
	}

	public void testNaturalParens()
	{
		getMultNo("Natural (Parens)");
		Ability parent = getGrantor("Natural (Parens)");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Natural (Parens)"));
	}

	public void testMultYes()
	{
		getMultNo("Target");
		getMultYesStackNo("MultYes", "Target");
		Ability parent = getGrantor("MultYes (Target)");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "MultYes"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Target"));
	}

	public void testMultYesTargetParens()
	{
		getMultNo("Target (Parens)");
		getMultYesStackNo("MultYes", "Target (Parens)");
		Ability parent = getGrantor("MultYes (Target (Parens))");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "MultYes"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Target (Parens)"));
	}

	public void testMultYesNC()
	{
		getMultYesStackNoChooseNoChoice("MultYesNC");
		Ability parent = getGrantor("MultYesNC");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "MultYesNC"));
	}

	public void testStackYes()
	{
		getMultNo("Target");
		getMultYesStackNo("MultYesStackYes", "Target");
		Ability parent = getGrantor("MultYesStackYes (Target)");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "MultYesStackYes"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Target"));
	}

	public void testStackYesNC()
	{
		getMultYesStackNoChooseNoChoice("MultYesStackYesNC");
		Ability parent = getGrantor("MultYesStackYesNC");
		finishLoad();
		applyParent(parent);
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Parent"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "Grantor"));
		assertTrue(pc.hasAbilityKeyed(AbilityCategory.FEAT, "MultYesStackYesNC"));
	}

	private void applyParent(Ability parent)
	{
		CNAbility cna = CNAbilityFactory.getCNAbility(AbilityCategory.FEAT, Nature.NORMAL, parent);
		CNAbilitySelection cnas = new CNAbilitySelection(cna);
		pc.addAbility(cnas, UserSelection.getInstance(), this);
	}

	public void generic()
	{
		//Need to do these with 2 choices and test :P
		//	6) Ability granted by a ADD:VFEAT token where the target (in parens) is MULT:YES STACK:YES CHOOSE:NOCHOICE and the stackable items are chosen more than once (STACK is used)
		//	7) Ability granted by a ADD:VFEAT token where the target (in parens) is MULT:YES STACK:YES and any CHOOSE except NOCHOICE or USERINPUT. and the stackable items are chosen more than once (STACK is used)
		Ability multyesstackyes = getMultYesStackYes("MultYes", "Target");
		Ability multyesstackyesNC = getMultYesStackYesChooseNoChoice("MultYes");
	}

	private Ability getGrantor(String s)
	{
		getMultYesStackNo("Grantor", s);
		Ability parent = getMultNo("Parent");
		ParseResult result =
				getGrantToken().parseToken(context, parent,
					getGrantPrefix() + "Grantor (" + s + ")");
		if (result != ParseResult.SUCCESS)
		{
			result.printMessages();
			fail("Test Setup Failed");
		}
		return parent;
	}

	protected String getGrantPrefix()
	{
		return "";
	}

	protected abstract CDOMToken<? super Ability> getGrantToken();
//	{
//		//TODO Need to cycle through:
//		//ADD:FEAT
//		//ABILITY (Virtual)
//		//ABILITY (Normal)
//		//AUTO:FEAT
//		//ADD:VFEAT
//		//ADD:ABILITY (Virtual)
//		//ADD:ABILITY (Normal)
//		//VFEAT:
//		//(domain's Feat)
//		//(race's Feat)
//		//(template's Feat)
//		return ADD_FEAT_TOKEN;
//	}

	/*
	 * Is it at all possible to deal with
	 * 
	 * This (%LIST) in Ability, Domain Feat, Auto Feat
	 */
	@Override
	public CDOMToken<?> getToken()
	{
		return getGrantToken();
	}


}
