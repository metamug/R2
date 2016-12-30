/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtg.resourcenesting.tests;

import com.metamug.resourcenesting.TreeUtil;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author anish
 */
public class JUnitTest {
    
    String nodeA, nodeB, nodeC, nodeD, nodeE, nodeF;
    TreeUtil treeUtil;
    
    @Before
    public void setup(){
        nodeA = "nodeA";
        nodeB = "nodeB";
        nodeC = "nodeC";
        nodeD = "nodeD";
        nodeE = "nodeE";
        nodeF = "nodeF";
        
        treeUtil = new TreeUtil();
        treeUtil.addOrReplaceNode(null,nodeA);
        treeUtil.addOrReplaceNode(nodeA,nodeB);
        treeUtil.addOrReplaceNode(nodeB,nodeC);
        treeUtil.addOrReplaceNode(nodeB, nodeF);
        treeUtil.addOrReplaceNode(null,nodeD);
        treeUtil.addOrReplaceNode(nodeD,nodeE);
        //built structure: A/B/C,F;D/E
    }
    
    @Test
    public void Test1() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        //proposed structure: A/B/C
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test2() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeD);
        nodeList.add(nodeE);
        //proposed structure: D/E
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test3() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        //proposed structure: A/
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test4() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        //proposed structure: A/B
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test5() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        //proposed structure: B/C
        assertFalse(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test6() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeD);
        //proposed structure: D/
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test7() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeC);
        //proposed structure: C/
        assertFalse(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test8() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeE);
        //proposed structure: E/
        assertFalse(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test9() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeF);
        //proposed structure: A/B/F
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test10() {        
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeB);
        nodeList.add(nodeF);
        //proposed structure: B/F
        assertFalse(treeUtil.validate(nodeList));
    }
    
    /*
    
        Checking replacement of nodes
    
    */
    
    @Test
    public void Test11() {        
        treeUtil.addOrReplaceNode(nodeD, nodeB);
        //structure will change to A;D/E/B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeD);
        nodeList.add(nodeB);
        nodeList.add(nodeF);
        //proposed structure: D/B/F
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test12() {        
        treeUtil.addOrReplaceNode(nodeD, nodeB);
        //structure will change to A;D/E/B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeF);
        //proposed structure: A/B/F
        assertFalse(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test13() {        
        treeUtil.addOrReplaceNode(null, nodeB);
        //structure will change to A;D/E;B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        //proposed structure: B/C
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test14() {        
        treeUtil.addOrReplaceNode(null, nodeB);
        //structure will change to A;D/E;B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        //proposed structure: A/B
        assertFalse(treeUtil.validate(nodeList));
    }
    
    /*
    
        Tests for checking removal of nodes
    
    */
    
    @Test
    public void Test15(){
        treeUtil.removeNode(nodeA, null);
        //will change to B/C,F;D/E
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        //proposed structure: B/C
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test16(){
        treeUtil.removeNode(nodeA, null);
        //will change to B/C,F;D/E
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        //proposed structure: A/
        assertFalse(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test17(){
        treeUtil.removeNode(nodeA, null);
        //will change to B/C,F;D/E
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeB);
        nodeList.add(nodeF);
        //proposed structure: B/F
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test18(){
        treeUtil.removeNode(nodeB, null);
        //will change to A;D/E;B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeC);
        //proposed structure: C/
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test19(){
        treeUtil.removeNode(nodeB, "invalid parent!");
        //invalid parent
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeC);
        //proposed structure: A/B/C/
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test20(){
        treeUtil.removeNode(nodeB, nodeD);
        ////will change to A;D/B,E;B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeD);
        nodeList.add(nodeC);
        //proposed structure: D/C
        assertTrue(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test21(){
        treeUtil.removeNode(nodeF, null);
        ////will change to A/B/C;D/E
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        nodeList.add(nodeF);
        //proposed structure: A/B/F
        assertFalse(treeUtil.validate(nodeList));
    }
    
    @Test
    public void Test22(){
        treeUtil.removeNode(nodeB, nodeD);
        ////will change to A;D/B,E;B/C,F
        List<String> nodeList = new ArrayList<>();
        nodeList.add(nodeA);
        nodeList.add(nodeB);
        //proposed structure: A/B
        assertFalse(treeUtil.validate(nodeList));
    }
    
}
