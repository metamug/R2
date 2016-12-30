/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.resourcenesting;

import java.util.HashSet;

/**
 *
 * @author anish
 */
public class Node {
    
    private final String resourceName;
    private HashSet<Node> childNodes;
    
    public Node(String name){
        this.resourceName = name;
        this.childNodes = new HashSet<>();
    }
    
    public String getName(){
        return this.resourceName;
    }
    
    public void setChildNodes(HashSet<Node> nodes){
        this.childNodes = nodes;
    }
    
    public void addChildNodes(HashSet<Node> nodes){
        for(Node n : nodes){
            this.childNodes.add(n);
        }
    }
    
    //add child to this node
    public void addChild(Node child){
        this.childNodes.add(child);
    }
    
    //get children of this node
    public HashSet<Node> getChildNodes(){
        return this.childNodes;
    }
    
    //return child of any order if exists or else return null
    public Node getChildIfExists(Node child){
        if(!childNodes.isEmpty()){
            for(Node n : childNodes){
                //if node is direct child of iterating node
                if(n.equals(child)){
                    return n;
                }
                //if not direct child, call method inside iterating node
                else{
                    Node n1 = n.getChildIfExists(child);
                    if(null != n1){
                        return n1;
                    }
                }
            }
        }
        return null;
    }    
    
    //remove child if exists
    //can be a child of any order
    //returns child which is removed, null if not found
    public Node removeChildIfExists(Node child){
        if(!childNodes.isEmpty()){
            for(Node n : childNodes){
                //if node is direct child of iterating node
                if(n.equals(child)){
                    childNodes.remove(n);
                    return n;
                }
                //if not direct child, call method inside iterating node
                else{
                    Node n1 = n.removeChildIfExists(child);
                    if(null != n1){
                        return n1;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public int hashCode(){
        return this.resourceName.hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        return this.resourceName.equals(((Node)obj).getName());
    }    
}
