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

package com.buuz135.industrial.entity.client;

import com.buuz135.industrial.entity.InfinityTridentEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class InfinityTridentModel extends EntityModel<InfinityTridentEntity> {

    private final ModelPart main;

    public InfinityTridentModel(ModelPart model) {
        this.main = model;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("main", CubeListBuilder.create()
                        .texOffs(28, 42).addBox(-9.0f, 8.0f, 7.0f, 2.0f, 6.0f, 2.0f)
                        .texOffs(20, 42).addBox(-9.0f, 0.0f, 7.0f, 2.0f, 6.0f, 2.0f)
                        .texOffs(0, 29).addBox(-10.0f, 14.0f, 6.0f, 4.0f, 2.0f, 4.0f)
                        .texOffs(16, 12).addBox(-10.0f, 6.0f, 6.0f, 4.0f, 2.0f, 4.0f)
                        .texOffs(12, 42).addBox(-9.0f, -14.0f, 7.0f, 2.0f, 8.0f, 2.0f)
                        .texOffs(8, 12).addBox(-4.5f, -28.0f, 7.0f, 2.0f, 15.0f, 2.0f)
                        .texOffs(16, 29).addBox(-9.0f, -32.0f, 7.0f, 2.0f, 11.0f, 2.0f)
                        .texOffs(0, 42).addBox(-9.5f, -28.0f, 6.5f, 3.0f, 4.0f, 3.0f)
                        .texOffs(0, 12).addBox(-13.5f, -28.0f, 7.0f, 2.0f, 15.0f, 2.0f)
                        .texOffs(0, 0).addBox(-12.5f, -21.0f, 5.5f, 9.0f, 7.0f, 5.0f)
                        .texOffs(24, 29).addBox(-9.5f, -6.0f, 6.5f, 3.0f, 6.0f, 3.0f)
                        .texOffs(28, 0).addBox(-10.0f, 8.0f, 6.0f, 4.0f, 6.0f, 4.0f),
                PartPose.offsetAndRotation(0.0f, 24.0f, 0.0f, 0.0f, 0.0f, 0.0f));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(InfinityTridentEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        main.render(poseStack, buffer, packedLight, packedOverlay);
    }


}

