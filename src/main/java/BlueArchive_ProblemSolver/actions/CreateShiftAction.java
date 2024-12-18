package BlueArchive_ProblemSolver.actions;

import BlueArchive_ProblemSolver.cards.Shift;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class CreateShiftAction extends AbstractGameAction {
    private boolean upgrade;
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;

    public CreateShiftAction(AbstractPlayer p, boolean upgrade, boolean freeToPlayOnce, int energyOnUse) {
        this.p = p;
        this.upgrade = upgrade;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }
        if(upgrade)
            effect++;

        if (effect > 0) {
            Shift card_ = new Shift();
            card_.upgrade();
            this.addToBot(new MakeTempCardInHandAction(card_, effect));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}