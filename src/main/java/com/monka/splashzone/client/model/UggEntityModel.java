package com.monka.splashzone.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.monka.splashzone.Splashzone;
import com.monka.splashzone.entity.UggEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class UggEntityModel<T extends UggEntity> extends EntityModel<T> {

    public static final ModelLayerLocation UGG =
            new ModelLayerLocation(new ResourceLocation(Splashzone.MODID, "ugg_entity"), "main");
    private final ModelParts parts;

    public UggEntityModel(ModelPart root) {
        ModelPart body = root.getChild("body");
        ModelPart tail = body.getChild("tail");
        ModelPart eyeRight = body.getChild("eyeRight");
        ModelPart eyeLeft = body.getChild("eyeLeft");

        this.parts = new ModelParts(body, tail, eyeRight, eyeLeft);
    }

    public static LayerDefinition createBodyLayer() {
        var meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -9.0F, 4.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(19, 9).addBox(-2.0F, -0.5F, -10.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.5F, 2.0F));

        body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 2.0F));

        body.addOrReplaceChild("eyeRight", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-1.0F, -4.0F, -3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.5F, -8.0F));

        body.addOrReplaceChild("eyeLeft", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 5).addBox(-1.0F, -4.0F, -3.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -1.5F, -8.0F));

        return LayerDefinition.create(meshdefinition, 41, 14);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Yaw is y rotation, or horizontal and Pitch is x rotation, or vertical
        this.parts.body().resetPose();

        this.parts.eyeleft().yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.parts.eyeleft().xRot = headPitch * Mth.DEG_TO_RAD;

        this.parts.eyeRight().yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.parts.eyeRight().xRot = headPitch * Mth.DEG_TO_RAD;

        this.parts.body().zScale = 1.0F + ((float) (Math.sin(limbSwing * -1.0F + 1.0F) * limbSwingAmount));

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (this.young) {
            poseStack.pushPose();
            poseStack.scale(0.65F, 0.65F, 0.65F);
            poseStack.translate(0.0D, 0.8D, 0.125D);
            this.parts.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        } else {
            poseStack.pushPose();
            this.parts.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
            poseStack.popPose();
        }
    }

    private record ModelParts(ModelPart body, ModelPart tail, ModelPart eyeRight, ModelPart eyeleft) {

    }
}
