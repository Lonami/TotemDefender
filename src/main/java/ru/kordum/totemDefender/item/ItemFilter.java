package ru.kordum.totemDefender.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.kordum.totemDefender.TotemDefender;
import ru.kordum.totemDefender.model.ICustomRenderModel;

public class ItemFilter extends Item implements ICustomRenderModel {
    public ItemFilter() {
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        super.getSubItems(tab, items);
        if (tab != TotemDefender.tab) {
            return;
        }

        for (EnumType type : EnumType.values()) {
            ItemStack subItem = new ItemStack(this, 1, type.ordinal());
            items.add(subItem);
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = stack.getMetadata();
        EnumType type = EnumType.byMeta(meta);
        return super.getUnlocalizedName(stack) + "." + type.getName();
    }

    @Override
    public void registerRender() {
        for (EnumType type : EnumType.values()) {
            ModelResourceLocation location = new ModelResourceLocation(this.getRegistryName() + "_" + type.getName(), "inventory");
            ModelLoader.setCustomModelResourceLocation(this, type.ordinal(), location);
        }
    }

    public enum EnumType implements IStringSerializable {
        PLAYER("player"),
        SELF_PLAYER("self_player"),
        ANOTHER_PLAYER("another_player"),
        ANIMAL("animal"),
        ENEMY("enemy"),
        SLIME("slime"),
        WATER("water");

        private final String name;

        EnumType(String name) {
            this.name = name;
        }

        public static EnumType byMeta(int meta) {
            for (EnumType type : EnumType.values()) {
                if (type.ordinal() == meta) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}