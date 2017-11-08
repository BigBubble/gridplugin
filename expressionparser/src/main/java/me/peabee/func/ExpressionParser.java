package me.peabee.func;

import me.peabee.exception.ParserException;
import me.peabee.model.ExpressionNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by pengbo on 17-11-7.
 */
public class ExpressionParser {

    /** 节点有子表达式 */
    private static final int HAVA_SUB_EXPRESSION = 1;

    /**
     * 解析表达式到桟
     * @param expression
     * @param stack
     * @return 变量值
     */
    public static String parserExpression(String expression, Stack<ExpressionNode> stack) throws ParserException{
        if (expression == null || expression.trim() ==""){
            throw new ParserException("表达式为空");
        }

        //替换表达式内所有空白
        expression = expression.replaceAll("\\s","");

        String variableString = null;

        char leftStart = '\0'; //起始标记 '(','['

        int leftB = 0; //左中括号数量
        int rightB = 0; //右中括号数量
        //节点
        StringBuilder leafNode = new StringBuilder();
        //关系
        StringBuilder relationNode = new StringBuilder();

        int len = expression.length();
        int index = -1;
        while (++ index < len){
            char currentChar = expression.charAt(index);
            //节点起始标记
            if(currentChar == '('){
                leafNode.append(currentChar);
                //非子表达式
                if(leftStart != '['){
                    leftStart = currentChar;
                    //是起始标记，关系字符串长度不为0，加入关系字符串
                    if(relationNode.length() > 0){
                        ExpressionNode expressionNode = generaterRelationNode(relationNode.toString());
                        stack.push(expressionNode);
                        relationNode.setLength(0);
                    }
                }
            }else if(currentChar == ')') {
                //节点结束标记
                leafNode.append(currentChar);
                //非子表达式，将节点信息加入桟
                if (leftStart != '[') {
                    leftStart = '\0';
                    if (leafNode.length() > 0) {
                        ExpressionNode expressionNode = generaterLeafNode(leafNode.toString(), 0);
                        if(expressionNode.isVariable()){
                            variableString = expressionNode.getInfo();
                        }
                        stack.push(expressionNode);
                        leafNode.setLength(0);
                    }
                }
            }else if(currentChar == '['){
                leftB ++; //嵌套数量+1
                if(leftStart != '['){
                    //非子节点，系字符串长度不为0，加入关系字符串
                    leftStart = currentChar;
                    if(relationNode.length() > 0){
                        ExpressionNode expressionNode = generaterRelationNode(relationNode.toString());
                        stack.push(expressionNode);
                        relationNode.setLength(0);
                    }
                }else{
                    //在子表达式内，按节点信息处理
                    leafNode.append(currentChar);
                }

            }else if(currentChar == ']'){
                rightB ++;
                if(rightB == leftB){ //嵌套层数相同
                    leftStart = '\0';
                    if(leafNode.length() > 0){
                        ExpressionNode expressionNode = generaterLeafNode(leafNode.toString(), HAVA_SUB_EXPRESSION);
                        stack.push(expressionNode);
                        leafNode.setLength(0);
                    }
                    leftB = 0;
                    rightB = 0;
                }else{
                    //按节点信息处理
                    leafNode.append(currentChar);
                }

            }else{
                //其他字符如果没有起始标记==\0，加入关系,否则加入叶子节点
                if (leftStart == '\0'){
                    relationNode.append(currentChar);
                }else{
                    leafNode.append(currentChar);
                }
            }

        }
        return variableString;
    }


    /**
     * 子表达式中可能有多个表达式，表示取并集或交集，但求解变量一定是同一个
     * 1.(a)-b-(c)-d-[(e)-f-(g)]&(h)-i-(j)  前一个‘]’结尾，后一个‘(’开头
     * (b)-b-(c)&(d)-e-(f) 前一个‘)’结尾，后一个‘(’开头
     * (a)-b-(c)-d-[(e)-f-(g)]&[(h)-i-(j)]-k-(l) 前一个‘]’结尾，后一个‘]’开头
     * (a)-b-(c)&[(d)-e-(f)]-g-(h) 前一个‘)’结尾，后一个‘[’开头
     * @param expression
     * @param expressionNode
     * @return
     */
    private static void parserPartExpression(String expression, ExpressionNode expressionNode) throws ParserException{

        StringBuilder expressionItem = new StringBuilder();
        List<String> expressionItemList = new ArrayList<>();
        int i = -1;
        char preChar = '\0';
        char postChar = '\0';
        char existLogicChar = '\0';

        int leftB = 0; //‘[’括号个数
        int rightB = 0; //‘]’括号个数
        while(++ i < expression.length()){
            if(i > 0){
                preChar = expression.charAt(i - 1);
            }
            if(i < expression.length() - 1){
                postChar = expression.charAt(i + 1);
            }
            char currentChar = expression.charAt(i);
            if(currentChar == '[') leftB ++;
            if(currentChar == ']') rightB ++;

            if((currentChar == '&' || currentChar == '|') && (preChar == ')' || preChar == ']')
                && (postChar == '[' || postChar == '(') && leftB == rightB){
                expressionItemList.add(expressionItem.toString());
                expressionItem.setLength(0);
                if(existLogicChar == '\0'){
                    existLogicChar = currentChar;
                }else{
                    if(existLogicChar != currentChar){
                        throw new ParserException("子表达式中逻辑符号不一致");
                    }
                }
            }else{
                expressionItem.append(currentChar);
            }
        }
        //最后一部分
        if(expressionItem.length() > 0){
            expressionItemList.add(expressionItem.toString());
        }

        String existViarable = null;
        List<Stack<ExpressionNode>> subStackList = new ArrayList<>(expressionItemList.size());
        for(String item : expressionItemList){
            Stack<ExpressionNode> stack  = new Stack<ExpressionNode>();
            String variable = parserExpression(item, stack);
            if(existViarable == null){
                existViarable = variable;
            }else{
                if(!existViarable.equals(variable)){
                    throw new ParserException("子表达式变量不一致");
                }
            }
            subStackList.add(stack);
        }
        if(existLogicChar == '&'){
            expressionNode.setSubExpressionLogic(1);
        }
        if(existLogicChar == '|'){
            expressionNode.setSubExpressionLogic(2);
        }
        expressionNode.setStackList(subStackList);
        expressionNode.setInfo(existViarable);
    }

    /**
     * 处理关系节点
     * @param relationString
     * @return
     */
    private static ExpressionNode generaterRelationNode(String relationString){
        ExpressionNode expressionNode = new ExpressionNode();
        if(relationString.indexOf("?") > -1){
            expressionNode.setVariable(true);
        }
        if(relationString.indexOf("<") == 0){
            //左
            expressionNode.setDirection(1);
        }else if(relationString.indexOf(">") == relationString.length() - 1){
            //右
            expressionNode.setDirection(2);
        }
        relationString = relationString.replaceAll("<|-|>","");
        expressionNode.setInfo(relationString);
        return expressionNode;
    }


    private static ExpressionNode generaterLeafNode(String leafString){
        return generaterLeafNode(leafString, 0);
    }
    /**
     * 处理叶子节点
     * @param leafString
     * @param hasSubExpression 是否有子表达式0,没有，1有
     * @return
     */
    private static ExpressionNode generaterLeafNode(String leafString, int hasSubExpression){
        ExpressionNode expressionNode =new ExpressionNode();
        if(hasSubExpression == HAVA_SUB_EXPRESSION){
            expressionNode.setSubExpression(leafString);
            parserPartExpression(leafString, expressionNode);
        }else{
            if(leafString.indexOf("?") > -1){
                expressionNode.setVariable(true);
            }
            expressionNode.setInfo(leafString);
        }
        return expressionNode;
    }

    public static void main(String[] args) {
        String expression = "(#a)-包含?->(#x1)-属于->(#类别:x2)<-属于-(#x3)<-包含?-[(#具体产品:x4?)-!从属&等于->[(gg)-hh-(ii?)&(x)-y-(ii?)]]-适用?->(#x5)<-适用?-(#a)";
        Stack<ExpressionNode> stack = new Stack<ExpressionNode>();
        String s = parserExpression(expression,stack);
        for(ExpressionNode ss : stack){
            System.out.println(ss.getInfo() + "    " + ss.isVariable());
        }
    }
}
