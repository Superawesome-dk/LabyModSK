package dk.superawesome.labymodsk.Expression.npcs;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprNpcFromID extends SimpleExpression<NPC> {

    private Expression<Number> ID;

    @Override
    public Class<? extends NPC> getReturnType() {
        return NPC.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        ID = (Expression<Number>) e[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean arg1) {
        return "[the] (npc|citizen) (of|from|with id) %number%";
    }

    @Override
    @Nullable
    protected NPC[] get(Event e) {
        NPCRegistry registry = CitizensAPI.getNPCRegistry();
        return new NPC[]{registry.getById(ID.getSingle(e).intValue())};
    }
}
