#include <iostream>
#include <vector>
#include <bits/stdc++.h>

#define MAX_N 100000

using namespace std;

int n;
vector<int> edges[MAX_N + 1];
bool visited[MAX_N + 1];
int parents[MAX_N + 1]; // 부모 노드를 저장

void Traversal(int x) {
    for(int i = 0; i < edges[x].size(); i++) {
        int y = edges[x][i];

        // 아직 방문해 본 적 없는 노드라면
        if(!visited[y]) {
            visited[y] = true;
            // 부모를 둠
            parents[y] = x;

            // 추가적으로 탐색 진행
            Traversal(y);
        }
    }
}



int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    // 입력 받는 코드
    cin >> n;

    int parent, child;
    for(int i = 0; i < n; i++) {
        cin >> parent >> child;

        // 간선 정보를 인접리스트에 넣어줌
        // 양방향 리스트
        edges[parent].push_back(child);
        edges[child].push_back(parent);

    }

    // 1번은 항상 루트노드
    visited[1] = true;
    Traversal(1);


    // 부모를 찾는 코드
    for(int i = 2; i <= n; i++) {
        cout << parents[i] << endl;
    }
    return 0;
}