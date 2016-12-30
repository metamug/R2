/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.resourcenesting;

import java.util.HashSet;
import java.util.List;

/**
 *  @author anish
 */

public class TreeUtil {
    
    //root of the tree
    private HashSet<Node> rootNodes;
    
    public TreeUtil(){
        rootNodes = new HashSet<>();
    }
    
    public void addRootNode(Node node){
        this.rootNodes.add(node);
    }
    
    public void addMultipleRootNodes(HashSet<Node> nodes){
        for(Node n : nodes){
            this.rootNodes.add(n);
        }
    }
    
    public HashSet<Node> getRootNodes(){
        return rootNodes;
    }
    
    public Node getNodeIfExists(Node node){
        if(!rootNodes.isEmpty()){
            for(Node n : rootNodes){
                //if node is root node
                if(n.equals(node)){
                    return n;
                }else{
                    //node is indirect child of any order    
                    Node n1 = n.getChildIfExists(node);
                    if(null != n1){
                        return n1;
                    }
                }                   
            }
        }
        return null;
    }
    
    //removes given node and appends its children to alternateParentNode
    //if given alternate parent null, it converts the children into root nodes
    //if alternate parent not found, node will not be removed
    public boolean removeNode(String nodeToRemove, String alternateParentNodeName){
        Node alternateParent = null;
        if(null != alternateParentNodeName){   
            alternateParent = getNodeIfExists(new Node(alternateParentNodeName));
            if(null == alternateParent){
                return false;
            }
        }
        
        Node node = new Node(nodeToRemove);           
        HashSet<Node> childrenOfRemovedNode = null;
        if(!rootNodes.isEmpty()){
            for(Node n : rootNodes){
                //if node is root node
                if(n.equals(node)){
                    rootNodes.remove(n);
                    childrenOfRemovedNode = n.getChildNodes();
                    if(!childrenOfRemovedNode.isEmpty()){
                        if(null != alternateParent){
                            alternateParent.addChildNodes(childrenOfRemovedNode);
                        }else{
                            this.addMultipleRootNodes(childrenOfRemovedNode);
                        }
                    }
                    return true;                    
                }else{
                    //node is indirect child of any order    
                    Node removedNode = n.removeChildIfExists(node);
                    if(null != removedNode){
                        childrenOfRemovedNode = removedNode.getChildNodes();
                        if(!childrenOfRemovedNode.isEmpty()){
                            if(null != alternateParent){
                                alternateParent.addChildNodes(childrenOfRemovedNode);
                            }else{
                                this.addMultipleRootNodes(childrenOfRemovedNode);
                            }
                        }
                        return true;
                    }                    
                }                   
            }
            //node not found
            return false;
        }else{
            //root nodes empty so node not found
            return false;
        }
    }
    
    //use this method to add the uploaded resource node
    //will add the node to the tree or replace its position if already exists
    //by removing the existing node
    //pass null as parent to add as root node
    public void addOrReplaceNode(String parentNode, String childNode){
        Node child = new Node(childNode);
        HashSet<Node> childrenOfRemovedNode = new HashSet<>();
        //remove node if exists
        if(!rootNodes.isEmpty()){
            for(Node n : rootNodes){
                //if node is root node
                if(n.equals(child)){
                    childrenOfRemovedNode = n.getChildNodes();
                    rootNodes.remove(n);
                }else{
                    //node is indirect child of any order    
                    Node removedNode = n.removeChildIfExists(child);
                    if(null != removedNode){
                        childrenOfRemovedNode = removedNode.getChildNodes();
                    }
                }                   
            }
        }
        
        //append removed node children to new node
        if(!childrenOfRemovedNode.isEmpty()){
            child.setChildNodes(childrenOfRemovedNode);
        }
        
        //if child is a rootnode, it wont have a parent
        if(null == parentNode){
            //add to root node list
            this.rootNodes.add(child);
        }
        //add child to given parent
        else{
            Node parent = new Node(parentNode);
            Node node = null;
            for(Node n : rootNodes){
                if(n.equals(parent)){
                    node = n;
                    break;
                }else{
                    node = n.getChildIfExists(parent);
                    if(null != node)
                        break;
                }
            }
            if(null != node){
                node.addChild(child);
            }else{
                //error given parent not found
                System.out.println("Validator Error : Parent not found in tree!");
            }
        }
        
    }
    
    //takes list of nodes and validates the order against the tree
    //first list element is considered parent and successive elements
    //are children of successive orders
    //assuming max. three nodes - one parent and two children
    public boolean validate(List<String> receivedNodeNames){
        if(!receivedNodeNames.isEmpty()){  
            Node parent = null;
            Node child1 = null;
            
            //will execute no. of times the order of received list
            for(int i=0; i < receivedNodeNames.size(); i++){
                Node receivedNode = new Node(receivedNodeNames.get(i));
                switch(i){
                    //first node will be searched in root node list
                    case 0:
                        for(Node n : rootNodes){
                            if(n.equals(receivedNode)){
                                //first node is valid so continue outer loop
                                //save matched parent node
                                parent = n;
                            }
                        }
                        if(null == parent){
                            return false;
                        }
                        break;
                    case 1:
                        if(null != parent.getChildIfExists(receivedNode)){
                            //second node found as child of parent node, continue loop
                            //save node as child1
                            child1 = parent.getChildIfExists(receivedNode);
                        }else{
                            //child not found in parent so invalid order
                            return false;
                        }                        
                        break;
                    case 2:
                        if(null != child1.getChildIfExists(receivedNode)){
                            //third node found                            
                        }else{
                            //third child not found as child of second child so invalid order
                            return false;
                        }
                        break;
                }
            }
            //order is valid as all nodes matched otherwise which the method would
            //return false in either of above cases
            return true;
        }
        //recieved list empty so invalid
        return false;      
    }
    
}
