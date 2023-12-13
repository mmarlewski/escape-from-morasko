package com.efm

import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.utils.Align
import com.efm.entities.bosses.*
import com.efm.entities.bosses.slime.BossSlime
import com.efm.entities.bosses.slime.BossSlimeQuarter
import com.efm.entities.enemies.chess.King
import com.efm.entity.Enemy
import com.efm.level.World
import com.efm.screens.GameScreen
import com.efm.skill.BodyPart
import com.efm.skill.Skill
import com.efm.skills.*
import com.efm.ui.gameScreen.PopUps

fun showSkillAssignPopUpAfterBossKill(bossKilled : Enemy)
{
    if (bossHasAssignedSkill(bossKilled))
    {
        val twoRandomSkills = getAmountOfRandomSkills(2)
        val bossAssignedSkill = getBossAssignedSkill(bossKilled)
        PopUps.skillAssignment = PopUps.skillAssignment(twoRandomSkills[0], bossAssignedSkill, twoRandomSkills[1])
        showSkillAssignmentWindow()
    } else
    {
        val threeRandomSkills = getAmountOfRandomSkills(3)
        PopUps.skillAssignment = PopUps.skillAssignment(threeRandomSkills[0], threeRandomSkills[1], threeRandomSkills[2])
        showSkillAssignmentWindow()
    }
}

fun showSkillAssignmentWindow()
{
    val skillAssignmentWindow = columnOf(rowOf(PopUps.skillAssignment)).align(Align.center)
    skillAssignmentWindow.setFillParent(true)
    GameScreen.stage.addActor(skillAssignmentWindow)
    PopUps.setSkillAssignmentVisibility(true)
}

fun getBossAssignedSkill(bossKilled : Enemy) : Skill
{
    when (bossKilled)
    {
        is King ->
        {
            return Swap
        }
        is BossNatureGolem ->
        {
            return GrassHealing
        }
        is BossOctopusHead ->
        {
            return Pull
        }
        is BossSlimeQuarter ->
        {
            return Jump
        }
        is BossDragon ->
        {
            return LavaWalking
        }
    }
    return Jump
}

fun getAmountOfRandomSkills(amountOfSkills : Int) : MutableList<Skill>
{
    val hero = World.hero
    val possibleSkills = mutableListOf<Skill>()
    var skillsAdded = 0
    for (bodyPart in hero.bodyPartMap.keys)
    {
        if (hero.bodyPartMap[bodyPart] == null)
        {
            val skillsForGivenBodyPart = getSkillsForGivenBodyPart(bodyPart)
            if (skillsForGivenBodyPart.isNotEmpty())
            {
                possibleSkills.add(skillsForGivenBodyPart.random())
                skillsAdded += 1
            }
        }
    }
    //more skills to be added than hero has free slots
    while (skillsAdded < amountOfSkills)
    {
        val randomSkill = getAllSkills().random()
        if (!possibleSkills.contains(randomSkill))
        {
            possibleSkills.add(randomSkill)
            skillsAdded += 1
        }
    }
    return possibleSkills
}

fun getSkillsForGivenBodyPart(bodyPart : BodyPart) : List<Skill>
{
    val applicableSkills = mutableListOf<Skill>()
    for (skill in getAllSkills())
    {
        if (skill.bodyPart == bodyPart)
        {
            applicableSkills.add(skill)
        }
    }
    return applicableSkills
}

fun bossHasAssignedSkill(bossKilled : Enemy) : Boolean
{
    when (bossKilled)
    {
        is King ->
        {
            return true
        }
        is BossNatureGolem ->
        {
            return true
        }
        is BossOctopusHead ->
        {
            return true
        }
        is BossSlimeQuarter ->
        {
            return true
        }
        is BossDragon ->
        {
            return true
        }
        else ->
        {
            return false
        }
    }
    
}

fun getAllSkills() : MutableList<Skill>
{
    val allSkills = mutableListOf<Skill>()
    allSkills.add(Freeze)
    allSkills.add(Invisibility)
    allSkills.add(Jump)
    allSkills.add(Pull)
    allSkills.add(Push)
    allSkills.add(Shield)
    allSkills.add(Swap)
    for (skill in getAllPassiveSkills())
    {
        allSkills.add(skill)
    }
    return allSkills
}