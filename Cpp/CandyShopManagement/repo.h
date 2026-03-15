#ifndef REPO_H
#define REPO_H
#include<vector>
#include"produs.h"
class Repo {
protected:
    vector<Produs> produse;
public:
    Repo();
    ~Repo();

    void addItem(const Produs&p);
    vector<Produs>getAll()const;
    int size()const;

    void deleteItem(int cod);
    void updateItem(int cod,const Produs&p);
};
#endif //REPO_H
