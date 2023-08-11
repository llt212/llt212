import java.util.LinkedList;
import java.util.Queue;

public class RedBlackTree {

    public final static Node NULL = new Node(Color.Black);

    public Node root;

    public RedBlackTree() {
        this.root = NULL;
    }

    /**
     * 左旋转
     * 
     * @param node
     */
    public void leftRotate(Node node) {
        Node rightNode = node.right;

        node.right = rightNode.left;
        if (rightNode.left != RedBlackTree.NULL)
            rightNode.left.parent = node;

        rightNode.parent = node.parent;
        if (node.parent == RedBlackTree.NULL)
            this.root = rightNode;
        else if (node.parent.left == node)
            node.parent.left = rightNode;
        else
            node.parent.right = rightNode;

        rightNode.left = node;
        node.parent = rightNode;
    }

    /**
     * 右旋转
     * 
     * @param node
     */
    public void rightRotate(Node node) {
        Node leftNode = node.left;

        node.left = leftNode.right;
        if (leftNode.right != RedBlackTree.NULL)
            leftNode.right.parent = node;

        leftNode.parent = node.parent;
        if (node.parent == RedBlackTree.NULL) {
            this.root = leftNode;
        } else if (node.parent.left == node) {
            node.parent.left = leftNode;
        } else {
            node.parent.right = leftNode;
        }

        leftNode.right = node;
        node.parent = leftNode;
    }

    public void insert(Node node) {
        Node parentPointer = RedBlackTree.NULL;
        Node pointer = this.root;

        while (pointer != RedBlackTree.NULL) {
            parentPointer = pointer;
            pointer = node.key < pointer.key ? pointer.left : pointer.right;
        }

        node.parent = parentPointer;
        if (parentPointer == RedBlackTree.NULL) {
            this.root = node;
        } else if (node.key < parentPointer.key) {
            parentPointer.left = node;
        } else {
            parentPointer.right = node;
        }

        node.left = RedBlackTree.NULL;
        node.right = RedBlackTree.NULL;
        node.color = Color.Red;
        this.insertFixUp(node);
    }

    private void insertFixUp(Node node) {
        // 当node不是根结点，且node的父节点颜色为红色
        while (node.parent.color == Color.Red) {
            // 先判断node的父节点是左子节点，还是右子节点，这不同的情况，解决方案也会不同
            if (node.parent == node.parent.parent.left) {
                Node uncleNode = node.parent.parent.right;
                if (uncleNode.color == Color.Red) { // 如果叔叔节点是红色，则父父一定是黑色
                    // 通过把父父节点变成红色，父节点和兄弟节点变成黑色，然后在判断父父节点的颜色是否合适
                    uncleNode.color = Color.Black;
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    node = node.parent.parent;
                } else if (node == node.parent.right) { // node是其父节点的右子节点，且叔叔节点是黑色
                    // 对node的父节点进行左旋转
                    node = node.parent;
                    this.leftRotate(node);
                } else { // node如果叔叔节点是黑色，node是其父节点的左子节点，父父节点是黑色
                    // 把父节点变成黑色，父父节点变成红色，对父父节点进行右旋转
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    this.rightRotate(node.parent.parent);
                }
            } else {
                Node uncleNode = node.parent.parent.left;
                if (uncleNode.color == Color.Red) {
                    uncleNode.color = Color.Black;
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    node = node.parent.parent;
                } else if (node == node.parent.left) {
                    node = node.parent;
                    this.rightRotate(node);
                } else {
                    node.parent.color = Color.Black;
                    node.parent.parent.color = Color.Red;
                    this.leftRotate(node.parent.parent);
                }
            }
        }
        // 如果之前树中没有节点，那么新加入的点就成了新结点，而新插入的结点都是红色的，所以需要修改。
        this.root.color = Color.Black;
    }

    /**
     * n2替代n1
     * 
     * @param n1
     * @param n2
     */
    private void transplant(Node n1, Node n2) {

        if (n1.parent == RedBlackTree.NULL) { // 如果n1是根节点
            this.root = n2;
        } else if (n1.parent.left == n1) { // 如果n1是其父节点的左子节点
            n1.parent.left = n2;
        } else { // 如果n1是其父节点的右子节点
            n1.parent.right = n2;
        }
        n2.parent = n1.parent;
    }

    /**
     * 删除节点node
     * 
     * @param node
     */
    public void delete(Node node) {
        Node pointer1 = node;
        // 用于记录被删除的颜色，如果是红色，可以不用管，但如果是黑色，可能会破坏红黑树的属性
        Color pointerOriginColor = pointer1.color;
        // 用于记录问题的出现点
        Node pointer2;
        if (node.left == RedBlackTree.NULL) {
            pointer2 = node.right;
            this.transplant(node, node.right);
        } else if (node.right == RedBlackTree.NULL) {
            pointer2 = node.left;
            this.transplant(node, node.left);
        } else {
            // 如要删除的字节有两个子节点，则找到其直接后继（右子树最小值），直接后继节点没有非空左子节点。
            pointer1 = node.right.minimum();
            // 记录直接后继的颜色和其右子节点
            pointerOriginColor = pointer1.color;
            pointer2 = pointer1.right;
            // 如果其直接后继是node的右子节点，不用进行处理
            if (pointer1.parent == node) {
                pointer2.parent = pointer1;
            } else {
                // 否则，先把直接后继从树中提取出来，用来替换node
                this.transplant(pointer1, pointer1.right);
                pointer1.right = node.right;
                pointer1.right.parent = pointer1;
            }
            // 用node的直接后继替换node，继承node的颜色
            this.transplant(node, pointer1);
            pointer1.left = node.left;
            pointer1.left.parent = pointer1;
            pointer1.color = node.color;
        }
        if (pointerOriginColor == Color.Black) {
            this.deleteFixUp(pointer2);
        }
    }

    /**
     * The procedure RB-DELETE-FIXUP restores properties 1, 2, and 4
     * 
     * @param node
     */
    private void deleteFixUp(Node node) {
        // 如果node不是根节点，且是黑色
        while (node != this.root && node.color == Color.Black) {
            // 如果node是其父节点的左子节点
            if (node == node.parent.left) {
                // 记录node的兄弟节点
                Node pointer1 = node.parent.right;
                // 如果node兄弟节点是红色
                if (pointer1.color == Color.Red) {
                    pointer1.color = Color.Black;
                    node.parent.color = Color.Red;
                    leftRotate(node.parent);
                    pointer1 = node.parent.right;
                }
                if (pointer1.left.color == Color.Black && pointer1.right.color == Color.Black) {
                    pointer1.color = Color.Red;
                    node = node.parent;
                } else if (pointer1.right.color == Color.Black) {
                    pointer1.left.color = Color.Black;
                    pointer1.color = Color.Red;
                    rightRotate(pointer1);
                    pointer1 = node.parent.right;
                } else {
                    pointer1.color = node.parent.color;
                    node.parent.color = Color.Black;
                    pointer1.right.color = Color.Black;
                    leftRotate(node.parent);
                    node = this.root;
                }
            } else {
                // 记录node的兄弟节点
                Node pointer1 = node.parent.left;
                // 如果他兄弟节点是红色
                if (pointer1.color == Color.Red) {
                    pointer1.color = Color.Black;
                    node.parent.color = Color.Red;
                    rightRotate(node.parent);
                    pointer1 = node.parent.left;
                }
                if (pointer1.right.color == Color.Black && pointer1.left.color == Color.Black) {
                    pointer1.color = Color.Red;
                    node = node.parent;
                } else if (pointer1.left.color == Color.Black) {
                    pointer1.right.color = Color.Black;
                    pointer1.color = Color.Red;
                    leftRotate(pointer1);
                    pointer1 = node.parent.left;
                } else {
                    pointer1.color = node.parent.color;
                    node.parent.color = Color.Black;
                    pointer1.left.color = Color.Black;
                    rightRotate(node.parent);
                    node = this.root;
                }
            }

        }
        node.color = Color.Black;
    }

    private void innerWalk(Node node) {
        if (node != NULL) {
            innerWalk(node.left);
            System.out.println(node);
            innerWalk(node.right);
        }
    }

    /**
     * 中序遍历
     */
    public void innerWalk() {
        this.innerWalk(this.root);
    }

    /**
     * 层次遍历
     */
    public void print() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(this.root);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.println(temp);
            if (temp.left != NULL)
                queue.add(temp.left);
            if (temp.right != NULL)
                queue.add(temp.right);
        }
    }

    // 查找
    public Node search(int key) {
        Node pointer = this.root;
        while (pointer != NULL && pointer.key != key) {
            pointer = pointer.key < key ? pointer.right : pointer.left;
        }
        return pointer;
    }

}

