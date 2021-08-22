/*
 * This file is part of Industrial Foregoing.
 *
 * Copyright 2021, Buuz135
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.buuz135.industrial.plugin.jei.generator;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.buuz135.industrial.block.generator.mycelial.IMycelialGeneratorType;
import com.buuz135.industrial.utils.Reference;
import com.hrznstudio.titanium.api.client.AssetTypes;
import com.hrznstudio.titanium.client.screen.addon.SlotsScreenAddon;
import com.hrznstudio.titanium.client.screen.asset.DefaultAssetProvider;
import com.hrznstudio.titanium.util.AssetUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class MycelialGeneratorCategory implements IRecipeCategory<MycelialGeneratorRecipe> {

    private IGuiHelper guiHelper;
    private IMycelialGeneratorType type;
    private final IDrawable smallTank;

    public MycelialGeneratorCategory(IMycelialGeneratorType type, IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        this.type = type;
        this.smallTank = guiHelper.createDrawable(DefaultAssetProvider.DEFAULT_LOCATION, 235 + 3, 1 + 3, 12, 13);
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(Reference.MOD_ID, "mycelial_" + type.getName());
    }

    @Override
    public Class<? extends MycelialGeneratorRecipe> getRecipeClass() {
        return MycelialGeneratorRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("industrialforegoing.jei.category." + type.getName());
    }

    @Override
    public IDrawable getBackground() {
        return guiHelper.createBlankDrawable(20*type.getInputs().length + 110,  Minecraft.getInstance().font.lineHeight * 3);
    }

    @Override
    public IDrawable getIcon() {
        return null;
    }

    public IMycelialGeneratorType getType() {
        return type;
    }

    @Override
    public void setIngredients(MycelialGeneratorRecipe recipe, IIngredients iIngredients) {
        for (int i = 0; i < type.getInputs().length; i++) {
            if (type.getInputs()[i] == IMycelialGeneratorType.Input.SLOT){
                iIngredients.setInputLists(VanillaTypes.ITEM, recipe.getInputItems().stream().map(ingredients -> ingredients.stream().map(ingredient -> Arrays.asList(ingredient.getItems())).flatMap(Collection::stream).collect(Collectors.toList())).collect(Collectors.toList()));
            } else if (type.getInputs()[i] == IMycelialGeneratorType.Input.TANK){
                iIngredients.setInputLists(VanillaTypes.FLUID, recipe.getFluidItems());
            }
        }
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, MycelialGeneratorRecipe recipe, IIngredients iIngredients) {
        IGuiItemStackGroup guiItemStackGroup = iRecipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStackGroup = iRecipeLayout.getFluidStacks();
        for (int i = 0; i < type.getInputs().length; i++) {
            if (type.getInputs()[i] == IMycelialGeneratorType.Input.SLOT){
                guiItemStackGroup.init(i, true, 20*i , Minecraft.getInstance().font.lineHeight / 2);
                guiItemStackGroup.set(i, recipe.getInputItems().get(i).stream().map(ingredient ->Arrays.asList(ingredient.getItems())).flatMap(Collection::stream).collect(Collectors.toList()));
            } else if (type.getInputs()[i] == IMycelialGeneratorType.Input.TANK){
                guiFluidStackGroup.init(i, true, 20*i +3, 3 + Minecraft.getInstance().font.lineHeight / 2, 12, 13, 1000, false,smallTank);
                guiFluidStackGroup.set(i, recipe.getFluidItems().get(i));
            }
        }
    }

    @Override
    public void draw(MycelialGeneratorRecipe recipe, PoseStack stack, double mouseX, double mouseY) {
        for (int i = 0; i < type.getInputs().length; i++) {
            if (type.getInputs()[i] == IMycelialGeneratorType.Input.SLOT){
                int finalI = i;
                SlotsScreenAddon.drawAsset(stack, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER, 20*i , Minecraft.getInstance().font.lineHeight / 2, 0, 0, 1, integer -> Pair.of(1,1), integer -> ItemStack.EMPTY, true, integer -> new Color(type.getInputColors()[finalI].getFireworkColor()), integer -> true);
            } else if (type.getInputs()[i] == IMycelialGeneratorType.Input.TANK){
                AssetUtil.drawAsset(stack, Minecraft.getInstance().screen, DefaultAssetProvider.DEFAULT_PROVIDER.getAsset(AssetTypes.TANK_SMALL), 20*i , Minecraft.getInstance().font.lineHeight / 2);
            }
        }
        int x = 20*type.getInputs().length + 3;
        Minecraft.getInstance().font.draw(stack, ChatFormatting.DARK_GRAY + "Time: " + ChatFormatting.DARK_AQUA+ new DecimalFormat( ).format(recipe.getTicks() / 20D) +ChatFormatting.DARK_GRAY + " s", x, Minecraft.getInstance().font.lineHeight * 0, 0xFFFFFFFF);
        Minecraft.getInstance().font.draw(stack, ChatFormatting.DARK_GRAY + "Production: " + ChatFormatting.DARK_AQUA+ recipe.getPowerTick() +ChatFormatting.DARK_GRAY + " FE/t", x, Minecraft.getInstance().font.lineHeight * 1, 0xFFFFFFFF);
        Minecraft.getInstance().font.draw(stack, ChatFormatting.DARK_GRAY + "Total: " + ChatFormatting.DARK_AQUA+ new DecimalFormat( ).format(recipe.getTicks() * recipe.getPowerTick())+ ChatFormatting.DARK_GRAY + " FE", x, Minecraft.getInstance().font.lineHeight * 2, 0xFFFFFFFF);


    }

    @Override
    public List<Component> getTooltipStrings(MycelialGeneratorRecipe recipe, double mouseX, double mouseY) {
        return Collections.emptyList();
    }

}
