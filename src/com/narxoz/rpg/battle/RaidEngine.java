package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null) {
            result.setWinner("None");
            result.setRounds(0);
            result.addLine("Invalid input: one of the teams is null.");
            return result;
        }

        if (teamASkill == null || teamBSkill == null) {
            result.setWinner("None");
            result.setRounds(0);
            result.addLine("Invalid input: one of the skills is null.");
            return result;
        }

        if (!teamA.isAlive() || !teamB.isAlive()) {
            result.setWinner("None");
            result.setRounds(0);
            result.addLine("Invalid input: one of the teams is already defeated.");
            return result;
        }

        final int maxRounds = 100;
        int rounds = 0;

        while (teamA.isAlive() && teamB.isAlive() && rounds < maxRounds) {
            rounds++;
            result.addLine("=== Round " + rounds + " ===");

            int beforeB = teamB.getHealth();
            boolean critA = random.nextInt(100) < 10;
            teamASkill.cast(teamB);
            int damageA = Math.max(0, beforeB - teamB.getHealth());

            if (critA && damageA > 0) {
                teamB.takeDamage(damageA / 2);
                damageA = beforeB - teamB.getHealth();
                result.addLine(teamA.getName() + " uses " + teamASkill.getSkillName() + " (" + teamASkill.getEffectName() + ") on " + teamB.getName() + " for " + damageA + " total damage [CRIT]");
            } else {
                result.addLine(teamA.getName() + " uses " + teamASkill.getSkillName() + " (" + teamASkill.getEffectName() + ") on " + teamB.getName() + " for " + damageA + " damage");
            }

            if (!teamB.isAlive()) {
                break;
            }

            int beforeA = teamA.getHealth();
            boolean critB = random.nextInt(100) < 10;
            teamBSkill.cast(teamA);
            int damageB = Math.max(0, beforeA - teamA.getHealth());

            if (critB && damageB > 0) {
                teamA.takeDamage(damageB / 2);
                damageB = beforeA - teamA.getHealth();
                result.addLine(teamB.getName() + " uses " + teamBSkill.getSkillName() + " (" + teamBSkill.getEffectName() + ") on " + teamA.getName() + " for " + damageB + " total damage [CRIT]");
            } else {
                result.addLine(teamB.getName() + " uses " + teamBSkill.getSkillName() + " (" + teamBSkill.getEffectName() + ") on " + teamA.getName() + " for " + damageB + " damage");
            }
        }

        result.setRounds(rounds);

        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
            if (rounds >= maxRounds) {
                result.addLine("Battle stopped because max rounds limit was reached.");
            }
        }

        return result;
    }
}