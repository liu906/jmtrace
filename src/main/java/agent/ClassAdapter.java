package agent;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class ClassAdapter extends ClassVisitor {
    ClassAdapter(ClassWriter cv){
        super(Opcodes.ASM4, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
        MethodVisitor mv = cv.visitMethod(access,name,desc,signature,exceptions);
        return (mv==null) ? null: new MethodAdapter(mv);
    }
}
