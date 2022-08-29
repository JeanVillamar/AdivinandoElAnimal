package TDAs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/**
 *
 * @author Grupo 6
 */
public class BinaryTree<E> {

    private BinaryTreeNode<E> root;
    public BinaryTree(E rootContent) {
        this.root = new BinaryTreeNode<>(rootContent);
    }

    public E getRootContent() {
        return this.root.getContent();
    }

    public BinaryTree() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean isLeaf() {
        return !hasChildren();
    }

    public void setRootContent(E content) {
        this.root = new BinaryTreeNode<>(content);
    }

    private BinaryTreeNode<E> getRoot() {
        return root;
    }

    private void setRoot(BinaryTreeNode<E> root) {
        this.root = root;
    }

    public void setLeft(BinaryTree<E> tree) {
        this.root.setLeft(tree);
    }

    public void setRight(BinaryTree<E> tree) {
        this.root.setRight(tree);
    }

    public BinaryTree getLeft() {
        return this.root.getLeft();
    }

    public BinaryTree getRight() {
        return this.root.getRight();
    }

    public boolean hasChildren() {
        return this.root.getLeft() != null || this.root.getRight() != null;
    }

    public boolean hasLeft() {
        return this.root.getLeft() != null;
    }

    public boolean hasRight() {
        return this.root.getRight() != null;
    }



    public LinkedList<E> preOrderTraversalRecursive() {
        LinkedList<E> traversal = new LinkedList<>();
        if (!this.isEmpty()) {
            traversal.add(this.getRootContent());
        } else {

            if (this.getLeft() != null) {
                traversal.addAll(this.getLeft().preOrderTraversalRecursive());
            }
            if (this.getRight() != null) {
                traversal.addAll(this.getRight().preOrderTraversalRecursive());
            }
        }
        return traversal;
    }

    public LinkedList<E> preOrderTraversalIterative() {
        LinkedList<E> traversal = new LinkedList<>();
        Stack<BinaryTree<E>> s = new Stack<>();
        s.push(this);
        while (!s.isEmpty()) {
            BinaryTree<E> tree = s.pop();
            if (!tree.isEmpty()) {
                traversal.add(tree.getRootContent());
            }
            if (tree.getRight() != null && !tree.getRight().isEmpty()) {
                s.push(tree.getRight());
            }
            if (tree.getLeft() != null && !tree.getLeft().isEmpty()) {
                s.push(tree.getLeft());
            }
        }
        return traversal;
    }

    public LinkedList<E> breadthTraversal() {
        LinkedList<E> traversal = new LinkedList<>();
        Queue<BinaryTree<E>> q = new LinkedList<>();
        q.offer(this);
        while (!q.isEmpty()) {
            BinaryTree<E> tree = q.poll();
            if (!tree.isEmpty()) {
                traversal.add(tree.getRootContent());
            }
            if (tree.getLeft() != null && !tree.getLeft().isEmpty()) {
                q.offer(tree.getLeft());
            }
            if (tree.getRight() != null && !tree.getRight().isEmpty()) {
                q.offer(tree.getRight());
            }
        }
        return traversal;
    }

    // CLASE INTERNA NECESITADA
    class BinaryTreeNode<E> {

        private E content;
        private BinaryTree<E> left;
        private BinaryTree<E> right;

        public BinaryTreeNode(E content) {
            this.content = content;
            this.left = null;
            this.right = null;
        }

        public BinaryTreeNode(E content, BinaryTree<E> left, BinaryTree<E> right) {
            this.content = content;
            this.left = left;
            this.right = right;
        }

        public E getContent() {
            return content;
        }

        public void setContent(E content) {
            this.content = content;
        }

        public BinaryTree<E> getLeft() {
            return left;
        }

        public void setLeft(BinaryTree<E> left) {
            this.left = left;
        }

        public BinaryTree<E> getRight() {
            return right;
        }

        public void setRight(BinaryTree<E> right) {
            this.right = right;
        }

    }

}
