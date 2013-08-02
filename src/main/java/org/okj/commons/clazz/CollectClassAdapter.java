package org.okj.commons.clazz;

import static org.kohsuke.asm4.Opcodes.ASM4;

import java.util.HashSet;
import java.util.Set;

import org.kohsuke.asm4.AnnotationVisitor;
import org.kohsuke.asm4.ClassVisitor;
import org.kohsuke.asm4.FieldVisitor;
import org.kohsuke.asm4.MethodVisitor;
 
 
public class CollectClassAdapter extends ClassVisitor{
	
    private Set<String> classes = new HashSet<String>();
     
    public CollectClassAdapter(ClassVisitor cv) {
        super(ASM4,cv);
    }
 
     
    public Set<String> getClasses() {
		return classes;
	}


	public void setClasses(Set<String> classes) {
		this.classes = classes;
	}



	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces){
    	classes.add(superName);
    	for(String interf : interfaces){
    		classes.add(interf);
    	}
    }
     
    public FieldVisitor visitField(int access, String name, String desc,
              String signature, Object value){
        return cv.visitField(access, name, desc, signature, value);
    }
    public MethodVisitor visitMethod(int access, String name,
              String desc, String signature, String[] exceptions){
        MethodBodyAdapter mba = new MethodBodyAdapter(ASM4,access,name,desc,signature,exceptions,this.classes);
         
        return mba;
    }
    
    public AnnotationVisitor visitAnnotation(String name,
            boolean visible){
        return cv.visitAnnotation(name, visible);
    }
    
     
}