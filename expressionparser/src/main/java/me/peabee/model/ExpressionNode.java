package me.peabee.model;

import java.util.List;
import java.util.Stack;

/**
 * Created by pengbo on 17-11-7.
 */
public class ExpressionNode {

    //节点信息
    private String info;
    //是否输出
    private boolean isVariable;

    //关系方向 -关系节点才有
    private int direction;

    //当 subExpression!=null 时 stack 有值
    private List<Stack<ExpressionNode>> stackList;

    //0 只有一个子表达式； 1-与；2-或
    private int subExpressionLogic;

    //子表达式
    private String subExpression;

    //0-叶子节点，1-关系节点
    private int type;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isVariable() {
        return isVariable;
    }

    public void setVariable(boolean variable) {
        isVariable = variable;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<Stack<ExpressionNode>> getStackList() {
        return stackList;
    }

    public void setStackList(List<Stack<ExpressionNode>> stackList) {
        this.stackList = stackList;
    }

    public String getSubExpression() {
        return subExpression;
    }

    public void setSubExpression(String subExpression) {
        this.subExpression = subExpression;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSubExpressionLogic() {
        return subExpressionLogic;
    }

    public void setSubExpressionLogic(int subExpressionLogic) {
        this.subExpressionLogic = subExpressionLogic;
    }
}
