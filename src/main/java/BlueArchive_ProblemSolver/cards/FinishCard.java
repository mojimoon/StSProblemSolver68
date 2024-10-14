package BlueArchive_ProblemSolver.cards;

import BlueArchive_ProblemSolver.actions.FinishAction;
import BlueArchive_ProblemSolver.characters.ProblemSolver68;
import BlueArchive_ProblemSolver.powers.AssaultPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javax.swing.*;
import java.util.ArrayList;

abstract public class FinishCard extends AbstractDynamicCard {
    public FinishCard(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, img, cost, type, color, rarity, target);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(ProblemSolver68.someoneHasPower(AssaultPower.POWER_ID)) {
            ProblemSolver68.flashPower(AssaultPower.POWER_ID);
            ArrayList<AbstractGameAction> actions = onFinish(p, m);
            for (AbstractGameAction action : actions) {
                this.addToBot(action);
            }
        } else {
            this.addToBot(new FinishAction(this, onFinish(p, m), makeFinishString()));
        }
    }
    private String makeFinishString() {
        String temp = getFinishString();
        temp = temp.replaceAll("!M!", Integer.toString(magicNumber));
        temp = temp.replaceAll("!B!", Integer.toString(block));
        temp = temp.replaceAll("!D!", Integer.toString(damage));
        return temp;
    }

    public abstract String getFinishString();

    public abstract ArrayList<AbstractGameAction> onFinish(AbstractPlayer p, AbstractMonster m);
    public void triggerOnGlowCheck() {
        this.glowColor = BlueArchive_ProblemSolver.cards.AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        //if (false) {
        //    this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        //}
    }
}