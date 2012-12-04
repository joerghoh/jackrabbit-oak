/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jackrabbit.oak.plugins.nodetype;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeType;

import org.apache.jackrabbit.oak.api.Tree;

/**
 * EffectiveNodeTypeProvider... TODO
 *
 * FIXME: see also TypeValidator which has it's own private EffectiveNodeType class. See OAK-412
 */
public interface EffectiveNodeTypeProvider {

    /**
     * Returns {@code true} if this tree is of the specified primary node
     * type or mixin type, or a subtype thereof respecting the effective node
     * type of the {@code tree}. Returns {@code false} otherwise.
     *
     * @param tree The tree to be tested.
     * @param nodeTypeName The name of the node type to be tested.
     * @return true if the specified node is of the given node type.
     * @throws NoSuchNodeTypeException If the specified node type name doesn't
     * refer to an existing node type.
     * @throws RepositoryException If the given node type name is invalid or if
     * some other error occurs.
     */
    boolean isNodeType(Tree tree, String nodeTypeName) throws NoSuchNodeTypeException, RepositoryException;

    /**
     * Calculates and returns all effective node types of the given node.
     *
     * @param targetNode the node for which the types should be calculated.
     * @return all types of the given node
     * @throws RepositoryException if the type information can not be accessed
     * @see <a href="http://www.jcp.org/en/jsr/detail?id=283">JCR 2.0 Specification,
     * Section 3.7.6.5</a> for the definition of the effective node type.
     */
    Iterable<NodeType> getEffectiveNodeTypes(Node targetNode) throws RepositoryException;

    /**
     * Calculates and returns all effective node types of the given tree.
     *
     * @param tree
     * @return all node types of the given tree
     * @throws RepositoryException if the type information can not be accessed,
     * @see <a href="http://www.jcp.org/en/jsr/detail?id=283">JCR 2.0 Specification,
     * Section 3.7.6.5</a> for the definition of the effective node type.
     */
    Iterable<NodeType> getEffectiveNodeTypes(Tree tree) throws RepositoryException;
}