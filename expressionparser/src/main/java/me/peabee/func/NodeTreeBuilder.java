package me.peabee.func;

import me.peabee.model.ExpressionNode;
import me.peabee.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by pengbo on 17-11-7.
 */
public class NodeTreeBuilder {
    /**
     * 构建二叉树
     * 先确定根节点位置，再分别创建左右子树
     * @param stack
     * @return
     */
    public static Node buildTree(Stack<ExpressionNode> stack){
        //stack1
        int root = 0;
        for(int i = 0; i < stack.size(); i ++){
            ExpressionNode element = stack.get(i);
            if(element.isVariable()){
                //如果是节点
                if(i % 2 == 0){
                    //如果是最后一个节点，将左边的关系作为根节点
                    if(i == stack.size() - 1){
                        root = i - 1;
                    }else{ //其他情况将右边的关系作为根结点
                        root = i + 1;
                    }
                }else{
                    //如果是关系，将当前关系作为根结点
                    root = i;
                }
            }
        }
        Node rootNode = new Node();
        rootNode.setName(stack.get(root).getInfo());
        rootNode.setOutPutFlage(stack.get(root).isVariable());
        rootNode.setDirection(stack.get(root).getDirection());
        //构建左子树
        int endIndex = root - 1;
        Node left = buildLeftTree(stack, 0, endIndex);
        //构建右子树
        int startIndex = root + 1;
        Node right = buildRigthTree(stack, startIndex, stack.size()-1);

        rootNode.setLeft(left);
        left.setParent(rootNode);
        rootNode.setRight(right);
        right.setParent(rootNode);

        return rootNode;
    }

    /**
     * 构建左子树
     * @param stack
     * @param start
     * @param end
     *       O6
     *     O4  O7
     *   O2  O5
     * O1  O3
     * @return
     */
    public static Node buildLeftTree(Stack<ExpressionNode> stack, int start, int end){
        if(start == end){
            Node node = new Node();
            node.setName(stack.get(start).getInfo());
            node.setOutPutFlage(stack.get(start).isVariable());
            buildSubTree(node, stack.get(start));
            return node;
        }

        Node left = null; //左节点不是第一个必定是非叶子节点
        Node root = null;
        Node right = null;

        while(start < end){

            if(left == null){
                left = new Node();
                ExpressionNode leftNode = stack.get(start);
                left.setName(leftNode.getInfo());
                left.setOutPutFlage(leftNode.isVariable());
                //构建子树
                buildSubTree(left, leftNode);
            }
            root = new Node();
            ExpressionNode rootNode = stack.get( ++ start);
            root.setName(rootNode.getInfo());
            root.setOutPutFlage(rootNode.isVariable());
            root.setDirection(rootNode.getDirection());

            right = new Node();
            ExpressionNode rightNode = stack.get(++ start);
            right.setName(rightNode.getInfo());
            right.setOutPutFlage(rightNode.isVariable());

            //构建子树
            buildSubTree(right, rightNode);

            root.setLeft(left);
            left.setParent(root);
            root.setRight(right);
            right.setParent(root);

            left = root;
        }

        return left;
    }

    /**
     * 构建右子树
     * @param stack
     * @param start
     * @param end
     *     O9
     *  O10  O11
     *    O12  O13
     *      O14  O15
     * @return
     */
    public static Node buildRigthTree(Stack<ExpressionNode> stack, int start, int end){
        if(start == end){
            Node node = new Node();
            node.setName(stack.get(start).getInfo());
            node.setOutPutFlage(stack.get(start).isVariable());
            //构建子树
            buildSubTree(node, stack.get(end));
            return node;
        }

        Node left = null;
        Node root = null;
        Node right = null; //右节点不是最后一个就是非叶子节点

        while(start < end){

            if(right == null){
                right = new Node();
                right.setName(stack.get(end).getInfo());
                right.setOutPutFlage(stack.get(end).isVariable());

                //构建子树
                buildSubTree(right, stack.get(end));
            }
            root = new Node();
            ExpressionNode rootNode = stack.get(-- end);
            root.setName(rootNode.getInfo());
            root.setOutPutFlage(rootNode.isVariable());
            root.setDirection(rootNode.getDirection());

            left = new Node();
            ExpressionNode leftNode = stack.get(-- end);
            left.setName(leftNode.getInfo());
            left.setOutPutFlage(leftNode.isVariable());
            //构建子树
            buildSubTree(left, leftNode);

            root.setLeft(left);
            left.setParent(root);
            root.setRight(right);
            right.setParent(root);

            right = root;
        }
        return right;
    }

    /**
     * 构建子树
     * @param left
     * @param expressionNode
     */
    public static void buildSubTree(Node left, ExpressionNode expressionNode){
            if(expressionNode.getSubExpression() != null && expressionNode.getStackList() != null){
                List<Node> subNodeList =new ArrayList<>();
                for(Stack<ExpressionNode> stack : expressionNode.getStackList()){
                    Node subNode = buildTree(stack);
                    subNodeList.add(subNode);
                }
                left.setSubNodeList(subNodeList);
                left.setSubNodeLogic(expressionNode.getSubExpressionLogic());
            }

    }

    public static void main(String[] args) {
        String expression = "(#a)-包含?->(#x1)-属于->(#类别:x2)<-属于-(#x3)<-包含?-[(#具体产品:x4?)-!从属&等于->[(gg)-hh-(ii?)&(x)-y-(ii?)]]-适用?->(#x5)<-适用?-(#a)";
        Stack<ExpressionNode> stack = new Stack<ExpressionNode>();
        ExpressionParser.parserExpression(expression,stack);
        Node root = buildTree(stack);
        traversalBinaryTree(root,"");
    }

    /**
     * 中序遍历二叉树
     * @param node
     */
    public static void traversalBinaryTree(Node node,String indent){
        if(node.getLeft() != null){
            traversalBinaryTree(node.getLeft(),indent);
        }
        String a = indent + node.getName() + "-out:"+node.isOutPutFlage();
        if(node.getLeft() != null || node.getRight() != null){
            a=a+"  direction:"+node.getDirection();
        }
        System.out.println(a+"\n");
        if(node.getSubNodeList() != null){
            if(node.getSubNodeLogic() == 1){
                System.out.println(indent + "    逻辑：与");
            }else if(node.getSubNodeLogic() == 2){
                System.out.println(indent + "    逻辑：或");
            }
            for(Node subNode : node.getSubNodeList()){
                traversalBinaryTree(subNode,indent + "    ");
            }
        }

        if(node.getRight() != null){
            traversalBinaryTree(node.getRight(), indent);
        }
    }
}
