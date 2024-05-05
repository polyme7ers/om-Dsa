#include<iostream>
using namespace std;

class tree
{
    int a[20][20], l, u, w, i, j, v, e, visited[20];
public:
    void input();
    void display();
    void minimum();
};

void tree::input()
{
    cout << "Enter the no. of branches(vertices): ";
    cin >> v;

    for (i = 0; i < v; i++)
    {
        visited[i] = 0;
        for (j = 0; j < v; j++)
        {
            a[i][j] = 999;
        }
    }

    cout << "\nEnter the no. of connections(edges): ";
    cin >> e;

    for (i = 0; i < e; i++)
    {
        cout << "Enter the vertices of connections:  " << endl;
        cin >> l >> u;
        cout << "Enter the phone company charges for this connection:  ";
        cin >> w;
        a[l - 1][u - 1] = a[u - 1][l - 1] = w;
    }
}

void tree::display()
{
    cout << "\nAdjacency matrix:";
    for (int i = 0; i < v; i++) // Loop through each vertex
    {
        cout << endl;
        for (int j = 0; j < v; j++) // Loop through each vertex again to print connections
        {
            if (a[i][j] != 999) // Display only non-zero weights (connections)
                cout << a[i][j] << "   ";
            else
                cout << "-   "; // Display "-" for unconnected vertices
        }
        cout << endl;
    }
}


void tree::minimum()
{
    int total = 0;
    // Mark the first vertex as visited
    visited[0] = 1;
    
    // Repeat until all vertices are visited
    for (int count = 0; count < (v - 1); count++)
    {
        int min = 999, p, q;
        
        // Find the minimum cost edge that connects a visited vertex to an unvisited one
        for (int i = 0; i < v; i++)
        {
            if (visited[i] == 1)
            {
                for (int j = 0; j < v; j++)
                {
                    if (visited[j] != 1 && a[i][j] < min)
                    {
                        min = a[i][j];
                        p = i; 
                        q = j;
                    }
                }
            }
        }
        
        // Mark the selected vertex as visited
        visited[q] = 1;
        
        // Display the minimum cost connection
        cout << "Minimum cost connection is " << (p + 1) << " -> " << (q + 1) << "  with charge: " << min << endl;
        
        // Update the total cost
        total += min;
    }
    
    // Display the total cost of the minimum spanning tree
    cout << "The minimum total cost of connections of all branches is: " << total << endl;
}

int main()
{
    int ch;
    tree t;
    do
    {
        cout << "==========PRIM'S ALGORITHM=================" << endl;
        cout << "\n1.INPUT\n \n2.DISPLAY\n \n3.MINIMUM\n" << endl;
        cout << "Enter your choice :" << endl;
        cin >> ch;

        switch (ch)
        {
        case 1:
            cout << "*******INPUT YOUR VALUES*******" << endl;
            t.input();
            break;

        case 2:
            cout << "*******DISPLAY THE CONTENTS********" << endl;
            t.display();
            break;

        case 3:
            cout << "*********MINIMUM************" << endl;
            t.minimum();
            break;
        }

    } while (ch != 4);
    return 0;
}
