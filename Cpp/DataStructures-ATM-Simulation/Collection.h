// Collection.h - NOUA varianta cu lista inlantuita
#ifndef COLLECTION_H
#define COLLECTION_H

template<typename T>
struct Nod {
    T elem;
    int frecv;
    Nod* next; //acum fiecare Nod are si un pointer la nodul urmator
};

template<typename T>
class Collection {
private:
    Nod<T>* head;

public:
    Collection();
    Collection(const Collection<T>& other);// constructor de copiere
    Collection<T>& operator=(const Collection<T>& other); //op de atribuire
    ~Collection();

    void add(T elem);
    bool remove(T elem);
    bool search(T elem) const;
    int size() const; //returneaza nr de noduri distincte
    T getAt(int position) const;
    int nrOccurrences(T elem) const;
    void afisare() const;
    int totalElements() const;// returneaza suma frecventelor(totalul logic de elemente)

private:
    void clear();
    void copyFrom(const Collection<T>& other);
};

#include "Collection.tpp"

#endif // COLLECTION_H
