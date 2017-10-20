package com.peabee.deapCopier;

import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by pengbo on 17-10-13.
 */
public class BinaryTree {
    public static void main(String[] args) {

        Node left = new Node(null, null, "l");
        Node right = new Node(null, null, "r");
        Node root = new Node(left,right,"root");

        right.setParent(root);
        left.setParent(root);

        List<Node> nodes = new ArrayList<>();

        MyBeanCopier myBeanCopier = new MyBeanCopier();
        Set<String> excludeProperties = new HashSet<>();
        excludeProperties.add("parent");
        myBeanCopier.setExcludeProperties(excludeProperties);
        Node b = myBeanCopier.convert(root, Node.class);
        System.out.println(b);
        buildParent(b, null);
        System.out.println(b);
        Node c = new Node();
        BeanCopier beanCopier = BeanCopier.create(Node.class, Node.class, false);
        beanCopier.copy(root, c, null);
        System.out.println(c);

    }
    //
    public static void buildParent(Node root, Node parent){
        root.setParent(parent);
        if(root.getLeft()  != null){
            buildParent(root.getLeft(), root);
        }
        if(root.getRight() != null){
            buildParent(root.getRight(), root);
        }
    }
    //中序遍历
    public static int traval(Node root, int depth, int lenth, List<Node> nodes){
        depth = depth + 1;
        root.setX(depth);
        if(root.getLeft() != null){
            lenth = traval(root.getLeft(), depth, lenth, nodes);
        }
        lenth = lenth + 1;
        root.setY(lenth);
        if(root.getRight() != null){
            lenth = traval(root.getRight(), depth, lenth, nodes);
        }
        root.setLeft(null);
        root.setRight(null);
        nodes.add(root);
        return lenth;
    }
}
class Node{
    private Node left;
    private Node right;
    private int x;
    private Node parent;

    public Node(){}

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int y;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node(Node left, Node right, String name) {
        this.left = left;
        this.right = right;
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
