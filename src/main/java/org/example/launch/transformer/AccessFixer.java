package org.example.launch.transformer;

import com.jab125.tweakloader.transformer.ClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import java.lang.reflect.Modifier;

public class AccessFixer implements ClassTransformer {
    @Override
    public boolean shouldTransform(String name) {
        return true;
    }

    @Override
    public byte[] transformClass(String className, byte[] in) {
        ClassReader classReader = new ClassReader(in);
        ClassNode node = new ClassNode(Opcodes.ASM9);
        classReader.accept(node, ClassReader.SKIP_DEBUG + ClassReader.SKIP_FRAMES);
        for (MethodNode methodNode : node.methods) {
            methodNode.access = makePublic(methodNode.access);
        }

        for (FieldNode fieldNode : node.fields) {
            fieldNode.access = makePublic(fieldNode.access);
        }
        node.access = makePublic(node.access);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        node.accept(classWriter);
        return classWriter.toByteArray();
    }

    private int makePublic(int access) {
        if (Modifier.isPublic(access)) return access;
        if (Modifier.isPrivate(access)) return access - Opcodes.ACC_PRIVATE + Opcodes.ACC_PUBLIC;
        if (Modifier.isProtected(access)) return access - Opcodes.ACC_PROTECTED + Opcodes.ACC_PUBLIC;
        return access + Opcodes.ACC_PUBLIC;
    }
}
