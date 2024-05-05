#include <climits>
#include <iostream>
#include <stack>
using namespace std;

typedef struct node
{
    int data;
    node *L;
    node *R;
}node;
node *root, *temp;
int count, key;

class bst
{
public:
    bst()
    {
        root = NULL;
        count = 0;
    }

    void insert();
    void disin();
    void dispre();
    void dispost();
    void search(node *, int);
    int height(node *);
    void mirror(node *);
    void min(node *);
};

void bst::insert()
{
    char ans;

    do
    {
        int value;
        cout << "Enter value to insert: ";
        cin >> value;

        node *newNode = new node;
        newNode->data = value;
        newNode->L = NULL;
        newNode->R = NULL;

        if (root == NULL)
        {
            root = newNode;
        }
        else
        {
            node *current = root;
            node *parent = NULL;

            while (current != NULL)
            {
                parent = current;
                if (value > current->data)
                {
                    current = current->R;
                }
                else
                {
                    current = current->L;
                }
            }

            if (value > parent->data)
            {
                parent->R = newNode;
            }
            else
            {
                parent->L = newNode;
            }
        }

        count++;
        cout << "Do you want to insert more values (y/n)? ";
        cin >> ans;
    } while (ans == 'y' || ans == 'Y');

    cout << "Total number of nodes: " << count << endl;
}

void bst::disin()
{
    node *current = root;
    stack<node *> s;

    while (current != NULL || !s.empty())
    {
        while (current != NULL)
        {
            s.push(current);
            current = current->L;
        }

        current = s.top();
        s.pop();
        cout << current->data << "\t";
        current = current->R;
    }

    cout << endl;
}

void bst::dispre()
{
    if (root == NULL)
        return;

    stack<node *> s;
    s.push(root);

    while (!s.empty())
    {
        node *current = s.top();
        s.pop();

        cout << current->data << "\t";

        if (current->R != NULL)
        {
            s.push(current->R);
        }

        if (current->L != NULL)
        {
            s.push(current->L);
        }
    }

    cout << endl;
}

void bst::dispost()
{
    if (root == NULL)
        return;

    stack<node *> s;
    node *current = root;
    node *lastVisited = NULL;

    while (current != NULL || !s.empty())
    {
        while (current != NULL)
        {
            s.push(current);
            current = current->L;
        }

        current = s.top();

        if (current->R == NULL || current->R == lastVisited)
        {
            cout << current->data << "\t";

            s.pop();
            lastVisited = current;
            current = NULL;
        }
        else
        {
            current = current->R;
        }
    }

    cout << endl;
}


void bst::search(node *root, int key)
{
    int flag = 0;
    cout << "\nEnter your key : " << endl;
    cin >> key;
    temp = root;
    while (temp != NULL)
    {
        if (key == temp->data)
        {
            cout << "           KEY FOUND           \n";
            flag = 1;
            break;
        }
        node *parent = temp;
        if (key > parent->data)
        {
            temp = temp->R;
        }
        else
        {
            temp = temp->L;
        }
    }
    if (flag == 0)
    {
        cout << "            KEY NOT FOUND              " << endl;
    }
}

int bst::height(node *root)
{
    int hl, hr;
    if (root == NULL)
    {
        return 0;
    }
    else if (root->L == NULL && root->R == NULL)
    {
        return 1;
    }
    cout << endl;
    hr = 1 + height(root->R);
    hl = 1 + height(root->L);
    if (hr > hl)
    {
        return (hr);
    }
    else
    {
        return (hl);
    }
}

void bst::mirror(node *root)
{
    temp = root;
    if (root != NULL)
    {
        mirror(root->L);
        mirror(root->R);
        temp = root->L;
        root->L = root->R;
        root->R = temp;
    }
}

void bst::min(node *root)
{
    temp = root;
    cout << endl;
    while (temp->L != NULL)
    {
        temp = temp->L;
    }
    cout << temp->data;
}

int main()
{
    bst t;
    char ans;
    int choice;
    do
    {

        cout << "\n1) Insert new node";
        cout << "\n2) Inorder traversal";
        cout << "\n3) Preorder traversal";
        cout << "\n4) Postorder traversal";
        cout << "\n5) Search for key";
        cout << "\n6) Height of tree";
        cout << "\n7) Mirror tree";
        cout << "\n8) Minimum value";
        cout << "\n9) Exit";
        cout << "\nEnter your choice: ";
        cin >> choice;

        switch (choice)
        {
        case 1:
            t.insert();
            break;

        case 2:
            cout << "Inorder traversal: ";
            t.disin();
            break;

        case 3:
            cout << "Preorder traversal: ";
            t.dispre();
            break;

        case 4:
            cout << "Postorder traversal: ";
            t.dispost();
            break;

        case 5:
            t.search(root, key);
            break;

        case 6:
            cout << "Height of tree: " << t.height(root) << endl;
            break;

        case 7:
            cout << "Mirroring tree...\n";
            t.mirror(root);
            t.disin();
            cout << "Tree mirrored.\n";
            break;

        case 8:
            cout << "Minimum value: ";
            t.min(root);
            break;

        case 9:
            cout << "Exiting program.\n";
            return 0;

        default:
            cout << "Invalid choice. Please try again.\n";
            break;
        }

        cout << "Do you want to continue (y/n)? ";
        cin >> ans;
    } while (ans == 'y' || ans == 'Y');

    return 0;
}



