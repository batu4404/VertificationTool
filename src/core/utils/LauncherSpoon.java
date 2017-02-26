package core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.Literal;

import spoon.compiler.SpoonCompiler;
import spoon.compiler.SpoonResource;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtLiteral;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.FactoryImpl;
import spoon.reflect.reference.CtLocalVariableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.DefaultCoreFactory;
import spoon.support.StandardEnvironment;
import spoon.support.compiler.FileSystemFile;
import spoon.support.compiler.FileSystemFolder;
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler;

public class LauncherSpoon {
	
	private Factory factory = createFactory();
	
	private SpoonCompiler modelBuilder;
	
	private Filter<CtType<?>> typeFilter;
	
	public LauncherSpoon() {
		modelBuilder = createCompiler(factory);
	}
	
	public Factory createFactory() {
		return new FactoryImpl(new DefaultCoreFactory(), new StandardEnvironment());
	}
	
	public SpoonCompiler createCompiler() {
		return createCompiler(factory);
	}
	
	public SpoonCompiler createCompiler(Factory factory) {
		SpoonCompiler comp = new JDTBasedSpoonCompiler(factory);
		return comp;
	}
	
	public void addInputResource(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			addInputResource(new FileSystemFolder(file));
		} else {
			addInputResource(new FileSystemFile(file));
		}
	}

	private void addInputResource(SpoonResource resource) {
		modelBuilder.addInputSource(resource);
	}
	
	private void addInputResource(File resource) {
		modelBuilder.addInputSource(resource);
	}
	
	public void buildModel() {
		modelBuilder.build();
	}
	
	public CtModel getModel() {
		return factory.getModel();
	}
	
	public List<CtMethod> getMethods() {
		CtModel model = factory.getModel();
		return model.getElements(new TypeFilter(CtMethod.class));
	}
	
	public Factory getFactory() {
		return factory;
	}
	
	public static void main(String[] args) {
		
		System.out.println("hello world");
		LauncherSpoon launcher = new LauncherSpoon();
		String pathFile = "TestSpoon.java";
		File resource = new File(pathFile);
		if(!resource.exists()) {
			System.err.println("cannot open file");
			System.exit(1);
		}
		launcher.addInputResource(resource);
		launcher.buildModel();
		
		String str = "a = 10";
		Factory factory = launcher.getFactory();
//		CtLocalVariableReference left = factory.Core()
//						.createLocalVariableReference()
//						.setType(CtTypeReference.);
//		CtLiteral Literal = factory.Core().createLiteral();
//		CtExpression exp = launcher.getFactory().Core().createBinaryOperator();
		
	}

}
