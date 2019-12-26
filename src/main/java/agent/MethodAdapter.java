package agent;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;


public class MethodAdapter extends MethodVisitor implements Opcodes{
    MethodAdapter(MethodVisitor mv){
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor){
        if(opcode== Opcodes.GETSTATIC) {
            mv.visitLdcInsn(Type.getType("L" + owner + ";"));
            mv.visitLdcInsn(name);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceStaticRead", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)V", false);
        }
        else if(opcode == PUTSTATIC) {
            mv.visitLdcInsn(Type.getType("L" + owner + ";"));
            mv.visitLdcInsn(name);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceStaticWrite", "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;)V", false);
        }
        else if(opcode == GETFIELD) {
            mv.visitInsn(DUP);
            mv.visitLdcInsn(name);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceFieldRead", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V", false);
        }
        else if(opcode == PUTFIELD && Type.getType(descriptor).getSize() == 1) {
            mv.visitInsn(SWAP);
            mv.visitInsn(DUP);
            mv.visitLdcInsn(name);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceFieldWrite", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V", false);
            mv.visitInsn(SWAP);
        } else if(opcode == PUTFIELD && Type.getType(descriptor).getSize() == 2) {
            // v, w
            mv.visitInsn(DUP2_X1);
            mv.visitInsn(POP2);
            // w, v
            mv.visitInsn(DUP);
            mv.visitLdcInsn(name);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceFieldWrite", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/Object;)V", false);
            // w, v
            mv.visitInsn(DUP_X2);
            mv.visitInsn(POP);
        }

        mv.visitFieldInsn(opcode, owner, name, descriptor);
    }
    @Override
    public void visitInsn(int opcode){
        if(opcode >= IALOAD && opcode <= SALOAD) {
            mv.visitInsn(DUP2);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceArrayRead", "(Ljava/lang/Object;ILjava/lang/Object;)V", false);
        } else if (opcode == LASTORE || opcode == DASTORE) {
            // v1, v2, w
            mv.visitInsn(DUP2_X2);
            mv.visitInsn(POP2);
            // w, v1, v2
            mv.visitInsn(DUP2);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceArrayWrite", "(Ljava/lang/Object;ILjava/lang/Object;)V", false);
            // w, v1, v2
            mv.visitInsn(DUP2_X2);
            mv.visitInsn(POP2);
        } else if(opcode >= IASTORE && opcode <= SASTORE) {
            // v1, v2, v3
            mv.visitInsn(DUP_X2);
            mv.visitInsn(POP);
            // v3, v1, v2
            mv.visitInsn(DUP2);
            mv.visitInsn(ACONST_NULL);
            mv.visitMethodInsn(INVOKESTATIC, "agent/Print", "traceArrayWrite", "(Ljava/lang/Object;ILjava/lang/Object;)V", false);
            // v3, v1, v2
            mv.visitInsn(DUP2_X1);
            mv.visitInsn(POP2);
        }

        mv.visitInsn(opcode);
    }
}
