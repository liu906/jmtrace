package agent;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

//import com.sun.tools.javac.code.Attribute;
//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtMethod;
//import javassist.bytecode.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ClassVisitor;

public class TraceTransformer implements ClassFileTransformer {


    public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;

        if(className.startsWith("java")||className.startsWith("sun")||className.startsWith("agent")||className.startsWith("org")){
            return byteCode;
        }
//        System.out.println("==================Instrumenting...===============");
//        System.out.println("=================="+ className +"=================");
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES); // ??
        ClassAdapter ct = new ClassAdapter(cw);
        cr.accept(ct,0);
        return cw.toByteArray();

    }
}