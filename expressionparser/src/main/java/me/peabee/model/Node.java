package me.peabee.model;

import java.util.List;

/**
 * Created by pengbo on 17-11-7.
 */
public class Node {
    private Node parent;
    private Node left;
    private Node right;
    private String name;
    private boolean outPutFlage;
    private List<Node> subNodeList;
    private int subNodeLogic; //0:没有，1：与，2：或
    private int direction; //0：没有，1：左，2：右

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOutPutFlage() {
        return outPutFlage;
    }

    public void setOutPutFlage(boolean outPutFlage) {
        this.outPutFlage = outPutFlage;
    }

    public List<Node> getSubNodeList() {
        return subNodeList;
    }

    public void setSubNodeList(List<Node> subNodeList) {
        this.subNodeList = subNodeList;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSubNodeLogic() {
        return subNodeLogic;
    }

    public void setSubNodeLogic(int subNodeLogic) {
        this.subNodeLogic = subNodeLogic;
    }
}
