package org.okj.commons.clazz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.kohsuke.asm4.ClassReader;
import org.kohsuke.asm4.ClassWriter;



public class ClassCollector {
	
	private Class<?> clazz;
	private Set<Class<?>> classes = new HashSet<Class<?>>();
	
	private List<String> packages = new ArrayList<String>();
	
	
	public ClassCollector(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public ClassCollector(Class<?> clazz, List<String> packages) {
		this.packages = packages;
		this.clazz = clazz;
	}
	
	
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public Set<Class<?>> getClasses() {
		ClassReader cr;
		try {
			cr = new ClassReader(clazz.getName());
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS); 
	    	CollectClassAdapter classAdapter = new CollectClassAdapter(cw); 
	    	cr.accept(classAdapter, ClassReader.SKIP_DEBUG); 
	    	Set<String> classes = classAdapter.getClasses();
	    	for(String c : classes){
	    		String className = c.replace('/', '.');
	    		filterClasses(className);
	    	}
		} catch (Exception e) {
		} 
		return classes;
	}

	private void filterClasses(String className) throws ClassNotFoundException {
		if(packages.isEmpty()){
			this.classes.add(Class.forName(className));
		}else{
			for(String p : packages){
				if(className.startsWith(p.toLowerCase())){
					this.classes.add(Class.forName(className));
				}
			}
		}
	}
	
	public void setClasses(Set<Class<?>> classes) {
		this.classes = classes;
	}
	
	public void addPackages(String pkg){
		packages.add(pkg);
	}

	public static void scanClasses(Class<?> clazz, Set<Class<?>> collectedClasses, Set<Class<?>> scanedClasses){
		ClassCollector cc = new ClassCollector(clazz); 
		Set<Class<?>> classes = cc.getClasses();
		collectedClasses.addAll(classes);
		if(classes.size() > 0){
			for(Class<?> c : classes){
				if(!scanedClasses.contains(c) && !clazz.equals(c)){
					scanedClasses.add(c);
					scanClasses(c, collectedClasses, scanedClasses);
				}
	    	}
		}
	}
	
	
	public static void main(String[] args) throws IOException{
    	Set<Class<?>> pkgs = new HashSet<Class<?>>();
    	Set<Class<?>> pkgss = new HashSet<Class<?>>();
    	ClassCollector.scanClasses(ClassCollector.class, pkgs,pkgss);
    	for(Class<?> s : pkgs){
    		System.out.println(s.getName());
    	}
    }

}
