package com.diffplug.spotless.extra.glue.jdt;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.TypeDeclaration;

class JdtFlags {

	static final int VISIBILITY_CODE_INVALID= 	-1;

	static boolean isStatic(BodyDeclaration bodyDeclaration) {
		if (isNestedInterfaceOrAnnotation(bodyDeclaration))
			return true;
		int nodeType= bodyDeclaration.getNodeType();
		if (nodeType != ASTNode.METHOD_DECLARATION
				&& nodeType != ASTNode.ANNOTATION_TYPE_MEMBER_DECLARATION
				&& isInterfaceOrAnnotationMember(bodyDeclaration))
			return true;
		if (bodyDeclaration instanceof EnumConstantDeclaration)
			return true;
		if (bodyDeclaration instanceof EnumDeclaration && bodyDeclaration.getParent() instanceof AbstractTypeDeclaration)
			return true;
		return Modifier.isStatic(bodyDeclaration.getModifiers());
	}

	private static boolean isPackageVisible(BodyDeclaration bodyDeclaration) {
		return (! isPrivate(bodyDeclaration) && ! isProtected(bodyDeclaration) && ! isPublic(bodyDeclaration));
	}

	private static boolean isPrivate(BodyDeclaration bodyDeclaration) {
		return Modifier.isPrivate(bodyDeclaration.getModifiers());
	}

	private static boolean isProtected(BodyDeclaration bodyDeclaration) {
		return Modifier.isProtected(bodyDeclaration.getModifiers());
	}

	private static boolean isPublic(BodyDeclaration bodyDeclaration) {
		if (isInterfaceOrAnnotationMember(bodyDeclaration))
			return true;
		return Modifier.isPublic(bodyDeclaration.getModifiers());
	}

	private static boolean isInterfaceOrAnnotationMember(BodyDeclaration bodyDeclaration) {
		return isInterfaceOrAnnotation(bodyDeclaration.getParent());
	}

	private static boolean isInterfaceOrAnnotation(ASTNode node) {
		boolean isInterface= (node instanceof TypeDeclaration) && ((TypeDeclaration) node).isInterface();
		boolean isAnnotation= node instanceof AnnotationTypeDeclaration;
		return isInterface || isAnnotation;
	}

	private static boolean isNestedInterfaceOrAnnotation(BodyDeclaration bodyDeclaration) {
		return bodyDeclaration.getParent() instanceof AbstractTypeDeclaration && isInterfaceOrAnnotation(bodyDeclaration);
	}

	static int getVisibilityCode(BodyDeclaration bodyDeclaration) {
		if (isPublic(bodyDeclaration))
			return Modifier.PUBLIC;
		else if (isProtected(bodyDeclaration))
			return Modifier.PROTECTED;
		else if (isPackageVisible(bodyDeclaration))
			return Modifier.NONE;
		else if (isPrivate(bodyDeclaration))
			return Modifier.PRIVATE;
		Assert.isTrue(false);
		return VISIBILITY_CODE_INVALID;
	}
}
