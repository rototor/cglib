package net.sf.cglib.transform;

import org.objectweb.asm.*;
import net.sf.cglib.util.Opcodes;
/**
 *
 * @author  baliuka
 */
public class TransformCodeVisitor implements CodeVisitor {
    
    CodeVisitor cv;
    ReadWriteFieldFilter filter;
    /** Creates a new instance of TransformCodeVisitor */
    public TransformCodeVisitor(CodeVisitor cv, ReadWriteFieldFilter filter ) {
        this.cv = cv;
        this.filter = filter;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    }
    
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        
        switch( opcode  ){
        
            case Opcodes.GETFIELD :
                
                if(filter.acceptRead(Type.getType("L" + owner + ";").getClassName(), name )){
                    
                    cv.visitMethodInsn( 
                    
                            Opcodes.INVOKEVIRTUAL ,
                            owner, 
                            Signature.readMethod(name), 
                            Type.getMethodDescriptor(Type.getType(desc), new Type[]{}) 
                            
                          );//visitMethodInsn
                    
                    return;
                    
                }else{
                    
                  break;
                }
                
                
            case Opcodes.PUTFIELD :    
                
                if(filter.acceptWrite(Type.getType("L" + owner + ";").getClassName(), name )){
                     
                    cv.visitMethodInsn( 
                            Opcodes.INVOKEVIRTUAL ,
                            owner, 
                            Signature.writeMethod(name), 
                            Type.getMethodDescriptor(
                            
                                                    Type.VOID_TYPE , 
                                                    new Type[]{Type.getType(desc)}
                    
                                         )//getMethodDescriptor 
                          );//visitMethodInsn
                    
                    return;
                    
                }else{
                    
                  break;
                }
                
            default:    
                break;
        }
        cv.visitFieldInsn(opcode,owner,name, desc );    
    }
    
    public void visitIincInsn(int var, int increment) {
        cv.visitIincInsn( var, increment );
    }
    
    public void visitInsn(int opcode) {
        cv.visitInsn(opcode);
    }
    
    public void visitIntInsn(int opcode, int operand) {
        cv.visitIntInsn( opcode, operand);
    }
    
    public void visitJumpInsn(int opcode, Label label) {
        cv.visitJumpInsn(opcode, label );
    }
    
    public void visitLabel(Label label) {
        cv.visitLabel(label);
    }
    
    public void visitLdcInsn(Object cst) {
        cv.visitLdcInsn(cst);
    }
    
    public void visitLineNumber(int line, Label start) {
        cv.visitLineNumber(line,start);
    }
    
    public void visitLocalVariable(String name, String desc, Label start, Label end, int index) {
        cv.visitLocalVariable( name, desc, start, end, index  );
    }
    
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        cv.visitLookupSwitchInsn(dflt,keys, labels );
    }
    
    public void visitMaxs(int maxStack, int maxLocals) {
        cv.visitMaxs( maxStack, maxLocals );
    }
    
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        
        cv.visitMethodInsn(opcode, owner, name, desc );
        
    }
    
    public void visitMultiANewArrayInsn(String desc, int dims) {
        cv.visitMultiANewArrayInsn(desc,dims );
    }
    
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
        cv.visitTableSwitchInsn(min, max, dflt, labels);
    }
    
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        cv.visitTryCatchBlock(start, end, handler, type );
    }
    
    public void visitTypeInsn(int opcode, String desc) {
        cv.visitTypeInsn(opcode, desc); 
    }
    
    public void visitVarInsn(int opcode, int var) {
        cv.visitVarInsn(opcode, var);
    }
    
    
}