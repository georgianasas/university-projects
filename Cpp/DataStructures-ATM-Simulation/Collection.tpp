#include <iostream>
using namespace std;

// Collection.tpp - NOUA varianta cu lista inlantuita

template<typename T>
Collection<T>::Collection() {
    head = nullptr;
}

template<typename T>
Collection<T>::~Collection() {
    clear();
}

template<typename T>
void Collection<T>::clear() {
    while (head) {
        Nod<T>* temp = head;   //punem pointerul head intr un temp , apoi la head i l dam pe urmatorul si apoi stergem temp care d efapt e primul head
        head = head->next;
        delete temp;           //sterge si pointer ul, dar si memoria de pe heap aloca pt acel nod
    }
} //le stergem asa in lant

template<typename T>
void Collection<T>::copyFrom(const Collection<T>& other) {
    if (!other.head) {
        head = nullptr;
        return;   /////daca colectia din care copiem e goala , avem doar sa ii dam la head ul nostr nullptr
    }
    head = new Nod<T>{other.head->elem, other.head->frecv, nullptr};
    Nod<T>* curr = head;                   // curr pointează la primul nod (deja creat)
    Nod<T>* otherCurr = other.head->next;  // otherCurr începe de la al doilea nod

    while (otherCurr) {
        curr->next = new Nod<T>{otherCurr->elem, otherCurr->frecv, nullptr}; // leagă un nou nod
        curr = curr->next;  // mută curr la ultimul nod adăugat
        otherCurr = otherCurr->next;  // mută și otherCurr la următorul nod
    }

}

template<typename T>
Collection<T>::Collection(const Collection<T>& other) {
    copyFrom(other);
}

template<typename T>
Collection<T>& Collection<T>::operator=(const Collection<T>& other) {
    if (this != &other) {
        clear();
        copyFrom(other);
    }
    return *this;
}

template<typename T>
void Collection<T>::add(T elem) {
    Nod<T>* curr = head;  //curr e o copie a pointer ului head
    while (curr) {
        if (curr->elem == elem) { //cum curr e un pointer, nu putem scrie curr.elem, scriem curr->elem
            curr->frecv++;
            return;
        }
        curr = curr->next;
    }
    Nod<T>* nou = new Nod<T>{elem, 1, head};  //cream un nou nod , care de fapt e un nou pointer ce pointeaza la Nod<T>,acest nod il legam de ceva ce exista deja , adica de primul
    head = nou;   //acum head pointeaza catre nou
}

template<typename T>
bool Collection<T>::remove(T elem) {
    Nod<T>* curr = head;
    Nod<T>* prev = nullptr;
    while (curr) {
        if (curr->elem == elem) {
            if (curr->frecv > 1) {
                curr->frecv--;
            } else {
                if (prev) {
                    prev->next = curr->next;
                } else {
                    head = curr->next;
                }
                delete curr;
            }
            return true;
        }
        prev = curr;
        curr = curr->next;
    }
    return false;
}

template<typename T>
bool Collection<T>::search(T elem) const {
    Nod<T>* curr = head;
    while (curr) {
        if (curr->elem == elem) return true;
        curr = curr->next;
    }
    return false;
}

template<typename T>
int Collection<T>::size() const {
    int count = 0;
    Nod<T>* curr = head;
    while (curr) {
        count++;
        curr = curr->next;
    }
    return count;
}

template<typename T>
int Collection<T>::nrOccurrences(T elem) const {
    Nod<T>* curr = head;
    while (curr) {
        if (curr->elem == elem) return curr->frecv;
        curr = curr->next;
    }
    return 0;
}

template<typename T>
T Collection<T>::getAt(int position) const {
    int idx = 0;
    Nod<T>* curr = head;
    while (curr) {
        if (idx == position) return curr->elem;
        idx++;
        curr = curr->next;
    }
    return T(); // default value if out of bounds
}

template<typename T>
void Collection<T>::afisare() const {
    cout << "Bancnote: ";
    Nod<T>* curr = head;
    while (curr) {
        cout << "(" << curr->elem << ", " << curr->frecv << ") ";
        curr = curr->next;
    }
    cout << endl;
}

template<typename T>
int Collection<T>::totalElements() const {
    int total = 0;
    Nod<T>* curr = head;
    while (curr) {
        total += curr->frecv;
        curr = curr->next;
    }
    return total;
}
