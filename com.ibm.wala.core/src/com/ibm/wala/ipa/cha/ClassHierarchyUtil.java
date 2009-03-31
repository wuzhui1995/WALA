/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.ipa.cha;

import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.types.MethodReference;

/**
 * Utilities for querying a class hierarchy
 */
public class ClassHierarchyUtil {

  /**
   * find the root of the inheritance tree for method m.
   */
  public static IMethod getRootOfInheritanceTree(IMethod m) {
    try {
      IClass c = m.getDeclaringClass();
      IClass parent = c.getSuperclass();
      if (parent == null) {
        return m;
      } else {
        MethodReference ref = MethodReference.findOrCreate(parent.getReference(), m.getSelector());
        IMethod m2 = m.getClassHierarchy().resolveMethod(ref);
        if (m2 != null && !m2.equals(m)) {
          return getRootOfInheritanceTree(m2);
        }
        return m;
      }
    } catch (ClassHierarchyException e) {
      return m;
    }
  }

}