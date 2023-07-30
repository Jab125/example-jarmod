package org.example.launch.transformer;

import com.jab125.tweakloader.transformer.ClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class BrandingTransformer implements ClassTransformer {
    @Override
    public boolean shouldTransform(String s) {
        return "net.minecraft.client.ClientBrandRetriever".equals(s);
    }

    @Override
    public byte[] transformClass(String s, byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        ClassNode node = new ClassNode(Opcodes.ASM9);
        for (MethodNode method : node.methods) {
            for (AbstractInsnNode instruction : method.instructions) {
                if (instruction instanceof LdcInsnNode ldcInsnNode) {
                    if ("vanilla".equals(ldcInsnNode.cst)) ldcInsnNode.cst = "example-jarmod";
                }
            }
        }


        classReader.accept(node, ClassReader.SKIP_DEBUG + ClassReader.SKIP_FRAMES);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        node.accept(classWriter);
        return classWriter.toByteArray();
    }
}
