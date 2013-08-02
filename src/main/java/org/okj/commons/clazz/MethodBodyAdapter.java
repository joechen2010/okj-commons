package org.okj.commons.clazz;

import java.util.Iterator;
import java.util.Set;

import org.kohsuke.asm4.Opcodes;
import org.kohsuke.asm4.tree.AbstractInsnNode;
import org.kohsuke.asm4.tree.FieldInsnNode;
import org.kohsuke.asm4.tree.MethodInsnNode;
import org.kohsuke.asm4.tree.MethodNode;
import org.kohsuke.asm4.tree.TypeInsnNode;
 
public class MethodBodyAdapter extends MethodNode   {
    private Set<String> classes = null;
     
    public MethodBodyAdapter(int api, int access, String name, String desc, String signature, String[] exceptions,Set<String> packages){
        super(api, access, name, desc, signature, exceptions);
        this.classes = packages;
    }
     
    public Set<String> getPackages(){
        return classes;
    }
    public void visitInsn(int opcode){
        Iterator<AbstractInsnNode> itr = this.instructions.iterator(0);
        while (itr.hasNext()) {
            AbstractInsnNode insn = itr.next();
             
            switch (insn.getType()) {
                case AbstractInsnNode.FIELD_INSN:{
                	classes.add(desc);
                }
                    break;
                case AbstractInsnNode.METHOD_INSN :{
                	classes.add(((MethodInsnNode) insn).owner);
                }
                    break;
                case AbstractInsnNode.TYPE_INSN :{
                	classes.add(((TypeInsnNode)insn).desc);
                }
                    break;
                 
//                case AbstractInsnNode.VAR_INSN :{
//                    System.out.println("VAR_INSN:"+AbstractInsnNode.VAR_INSN);
//                    VarInsnNode vinsn = ((VarInsnNode) insn);
//                    
//                }
//                    break;
//                
//                case AbstractInsnNode.LABEL :{
//                    System.out.println("LABEL:"+AbstractInsnNode.LABEL);
//                    LabelNode label = (LabelNode) insn;
//                }
//                    break;
//                
//                case AbstractInsnNode.LDC_INSN :{
//                    System.out.println("LDC_INSN:"+AbstractInsnNode.LDC_INSN);
//                    LdcInsnNode ldc = (LdcInsnNode) insn;
//                }
//                    break;
                 default :
                	 break;
            }
             
        }
    }
    
    public void visitFieldInsn(int opcode, String owner, String name, String desc){
    	switch (opcode) {
			case Opcodes.GETSTATIC:{
				if(desc.startsWith("L")){
					classes.add(desc.substring(1, desc.length() - 1));
		        }
			}
			break;
			default :
	       	 break;
		}
    }
}