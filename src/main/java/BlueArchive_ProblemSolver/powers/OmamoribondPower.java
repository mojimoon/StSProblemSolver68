package BlueArchive_ProblemSolver.powers;

import BlueArchive_ProblemSolver.DefaultMod;
import BlueArchive_ProblemSolver.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static BlueArchive_ProblemSolver.DefaultMod.makePowerPath;

public class OmamoribondPower extends AbstractPower implements CloneablePowerInterface, AllApplyedPower, SharedPower {
    public static final String POWER_ID = DefaultMod.makeID("OmamoribondPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("OmamoribondPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("OmamoribondPower32.png"));

    private static int omamoriIdOffset;
    private int drawpower;
    private int drawcount;

    public OmamoribondPower(final AbstractCreature owner, int current_amount, int amount, int drawpower) {
        name = NAME;
        ID = POWER_ID + omamoriIdOffset;
        ++omamoriIdOffset;

        this.owner = owner;
        this.amount = current_amount;
        this.drawpower = drawpower;
        this.drawcount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + drawcount + DESCRIPTIONS[1];
    }

    public void AllApplyed(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF && !power.ID.equals("Shackled") && !target.hasPower("Artifact") && (source != null && source.isPlayer)) {
            amount--;
            if(amount == 0) {
                amount = drawcount;
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(drawpower));
                this.flash();
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new OmamoribondPower(owner, amount, drawcount, drawpower);
    }

}
